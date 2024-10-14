package me.hellosunghyun.soopchatsdk

import me.hellosunghyun.soopchatsdk.api.OAuth
import me.hellosunghyun.soopchatsdk.api.ApiClient
import me.hellosunghyun.soopchatsdk.chat.ChatManager
import me.hellosunghyun.soopchatsdk.utils.Constants
import kotlinx.coroutines.*

/**
 * SoopChatSDK의 주요 클래스입니다.
 * 아프리카TV 채팅 기능을 위한 인터페이스를 제공합니다.
 *
 * @property clientId 클라이언트 ID
 * @property clientSecret 클라이언트 시크릿
 */
class SoopChatSDK(private val clientId: String, private val clientSecret: String) {
    private val apiClient = ApiClient(Constants.API_SERVER_HOST)
    private var chatManager: ChatManager? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var tokenExpirationTime: Long = 0
    private var isConnected = false
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    /**
     * OAuth 인증 페이지를 엽니다.
     *
     * @param redirectUri 인증 후 리디렉션될 URI
     */
    fun openAuth(redirectUri: String) {
        val oAuth = OAuth(clientId, clientSecret)
        oAuth.openAuth(redirectUri)
    }

    /**
     * 인증 토큰을 설정합니다.
     *
     * @param accessToken 액세스 토큰
     * @param refreshToken 리프레시 토큰
     * @param expiresIn 토큰 만료 시간 (초)
     */
    fun setAuthToken(accessToken: String, refreshToken: String, expiresIn: Int) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.tokenExpirationTime = System.currentTimeMillis() + (expiresIn * 1000)
    }

    /**
     * 토큰이 만료되었는지 확인합니다.
     *
     * @return 토큰이 만료되었으면 true, 아니면 false
     */
    private fun isTokenExpired(): Boolean {
        return System.currentTimeMillis() >= tokenExpirationTime
    }

    /**
     * 토큰을 갱신합니다.
     */
    private suspend fun refreshToken() {
        val oAuth = OAuth(clientId, clientSecret)
        val tokenInfo = oAuth.refreshAuth(refreshToken ?: throw Exception("Refresh token is null"))
        setAuthToken(
            tokenInfo["access_token"] ?: throw Exception("Failed to refresh token"),
            tokenInfo["refresh_token"] ?: throw Exception("Failed to refresh token"),
            tokenInfo["expires_in"]?.toInt() ?: 3600
        )
    }

    /**
     * 유효한 액세스 토큰을 가져옵니다. 필요한 경우 토큰을 갱신합니다.
     *
     * @return 유효한 액세스 토큰
     */
    suspend fun getValidAccessToken(): String {
        if (isTokenExpired()) {
            refreshToken()
        }
        return accessToken ?: throw Exception("Access token is null")
    }

    /**
     * 채팅 서버에 연결합니다.
     *
     * @throws Exception 연결 실패 시 예외 발생
     */
    fun connect() = runBlocking {
        val validAccessToken = getValidAccessToken()
        val chatInfo = requestChatRoomList(validAccessToken)
        if (chatManager == null) {
            chatManager = ChatManager(
                chatInfo["chatAddress"] as String,
                chatInfo["bjId"] as String,
                validAccessToken,
                chatInfo["ticket"] as String,
                chatInfo["chatNo"] as Int,
                { getValidAccessToken() } // 토큰 갱신 콜백 전달
            )
            isConnected = true
        } else {
            throw Exception("Already connected.")
        }
    }

    /**
     * 채팅방 정보를 요청합니다.
     *
     * @param accessToken 유효한 액세스 토큰
     * @return 채팅방 정보를 담은 Map
     */
    private suspend fun requestChatRoomList(accessToken: String): Map<String, Any> {
        val response = apiClient.post("/broad/access/chatinfo", mapOf("access_token" to accessToken))
        return parseChatInfoResponse(response)
    }

    /**
     * 채팅방 정보를 요청합니다.
     *
     * @return 채팅방 정보를 담은 Map
     */
    private suspend fun requestChatRoomList(): Map<String, Any> {
        // 채팅방 정보 요청 구현
        val response = apiClient.post("/broad/access/chatinfo", mapOf("access_token" to accessToken!!))
        // 응답을 파싱하여 필요한 정보 추출
        return parseChatInfoResponse(response)
    }

    /**
     * 채팅방 정보 응답을 파싱합니다.
     *
     * @param response API 응답 문자열
     * @return 파싱된 채팅방 정보
     */
    private fun parseChatInfoResponse(response: String): Map<String, Any> {
        // JSON 파싱을 통해 채팅방 정보를 추출합니다.
        val gson = com.google.gson.Gson()
        val chatInfoList = gson.fromJson(response, Array<ChatInfo>::class.java)
        val chatInfo = chatInfoList.firstOrNull() ?: throw Exception("No chat room available.")
        return mapOf(
            "chatAddress" to "${chatInfo.chatIp}:${chatInfo.chatPort}",
            "bjId" to chatInfo.id,
            "ticket" to chatInfo.ticket,
            "chatNo" to chatInfo.chatNo
        )
    }

    /**
     * 채팅방 정보를 담는 데이터 클래스입니다.
     */
    data class ChatInfo(
        val chatIp: String,
        val chatPort: Int,
        val id: String,
        val ticket: String,
        val chatNo: Int
    )

    /**
     * 채팅 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @throws Exception 연결되지 않은 상태에서 메시지 전송 시 예외 발생
     */
    fun sendMessage(message: String) {
        if (!isConnected) {
            throw Exception("채팅 서버에 연결되지 않았습니다.")
        }
        coroutineScope.launch {
            chatManager?.sendMessage(message, "normal")
        }
    }

    /**
     * 채팅 서버와의 연결을 종료합니다.
     */
    fun disconnect() {
        chatManager?.disconnect()
        chatManager = null
        isConnected = false
    }

    /**
     * 관리자 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @throws Exception 연결되지 않은 상태에서 메시지 전송 시 예외 발생
     */
    fun sendManagerMessage(message: String) {
        if (!isConnected) {
            throw Exception("채팅 서버에 연결되지 않았습니다.")
        }
        coroutineScope.launch {
            chatManager?.sendManagerMessage(message)
        }
    }

    // 기타 필요한 메서드를 모두 추가했습니다.
}
