package me.hellosunghyun.soopchatsdk

import me.hellosunghyun.soopchatsdk.api.OAuth
import me.hellosunghyun.soopchatsdk.api.ApiClient
import me.hellosunghyun.soopchatsdk.chat.ChatManager
import me.hellosunghyun.soopchatsdk.utils.Constants
import kotlinx.coroutines.runBlocking

class SoopChatSDK(private val clientId: String, private val clientSecret: String) {
    private val apiClient = ApiClient(Constants.API_SERVER_HOST)
    private var chatManager: ChatManager? = null
    private var accessToken: String? = null
    private var isConnected = false

    fun openAuth(redirectUri: String) {
        val oAuth = OAuth(clientId, clientSecret)
        oAuth.openAuth(redirectUri)
    }

    fun setAuthToken(token: String) {
        accessToken = token
    }

    fun connect() = runBlocking {
        if (accessToken == null) {
            throw Exception("Access token is required.")
        }
        val chatInfo = requestChatRoomList()
        if (chatManager == null) {
            chatManager = ChatManager(
                chatInfo["chatAddress"] as String,
                chatInfo["bjId"] as String,
                accessToken!!,
                chatInfo["ticket"] as String,
                chatInfo["chatNo"] as Int
            )
            isConnected = true
        } else {
            throw Exception("Already connected.")
        }
    }

    private suspend fun requestChatRoomList(): Map<String, Any> {
        // 채팅방 정보 요청 구현
        val response = apiClient.post("/broad/access/chatinfo", mapOf("access_token" to accessToken!!))
        // 응답을 파싱하여 필요한 정보 추출
        return parseChatInfoResponse(response)
    }

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

    data class ChatInfo(
        val chatIp: String,
        val chatPort: Int,
        val id: String,
        val ticket: String,
        val chatNo: Int
    )

    fun sendMessage(message: String) {
        if (!isConnected) {
            throw Exception("Not connected to chat server.")
        }
        chatManager?.sendMessage(message, "normal")
    }

    fun disconnect() {
        chatManager?.disconnect()
        chatManager = null
        isConnected = false
    }

    // 기타 필요한 메서드를 모두 추가했습니다.
}
