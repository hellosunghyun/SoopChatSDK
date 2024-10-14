package me.hellosunghyun.soopchatsdk.api

import me.hellosunghyun.soopchatsdk.utils.Constants
import kotlinx.coroutines.runBlocking
import com.google.gson.Gson

/**
 * OAuth 인증을 처리하는 클래스입니다.
 *
 * @property clientId 클라이언트 ID
 * @property clientSecret 클라이언트 시크릿
 */
class OAuth(private val clientId: String, private val clientSecret: String) {
    private val apiClient = ApiClient(Constants.API_SERVER_HOST)

    /**
     * OAuth 인증 페이지를 엽니다.
     *
     * @param redirectUri 인증 후 리디렉션될 URI
     */
    fun openAuth(redirectUri: String): String {
        val url = "${Constants.API_SERVER_HOST}/auth/code?client_id=$clientId&response_type=code&redirect_uri=$redirectUri"
        return url
    }

    /**
     * 인증 코드를 사용하여 액세스 토큰을 요청합니다.
     *
     * @param code 인증 코드
     * @return 토큰 정보를 담은 Map
     */
    suspend fun getAuth(code: String): Map<String, String> {
        val response = apiClient.post("/auth/token", mapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code
        ))
        // 응답을 파싱하여 토큰 반환
        // 예를 들어, JSON 파싱을 통해 토큰 값을 추출합니다.
        return parseTokenResponse(response)
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 요청합니다.
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 토큰 정보를 담은 Map
     */
    suspend fun refreshAuth(refreshToken: String): Map<String, String> {
        val response = apiClient.post("/auth/token", mapOf(
            "grant_type" to "refresh_token",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "refresh_token" to refreshToken
        ))
        return parseTokenResponse(response)
    }

    /**
     * 토큰 응답을 파싱합니다.
     *
     * @param response API 응답 문자열
     * @return 파싱된 토큰 정보를 담은 Map
     */
    private fun parseTokenResponse(response: String): Map<String, String> {
        // 응답 JSON을 파싱하여 access_token 및 refresh_token을 추출합니다.
        // 간단한 예시로 Gson 라이브러리를 사용합니다.
        val gson = Gson()
        val tokenResponse = gson.fromJson(response, TokenResponse::class.java)
        return mapOf(
            "access_token" to tokenResponse.access_token,
            "refresh_token" to tokenResponse.refresh_token
        )
    }

    /**
     * 토큰 응답 데이터 클래스입니다.
     */
    data class TokenResponse(
        val access_token: String,
        val refresh_token: String,
        val expires_in: Int,
        val token_type: String
    )
}
