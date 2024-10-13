package api

import utils.Constants
import kotlinx.coroutines.runBlocking
import com.google.gson.Gson

class OAuth(private val clientId: String, private val clientSecret: String) {
    private val apiClient = ApiClient(Constants.API_SERVER_HOST)

    fun openAuth(redirectUri: String) {
        val url = "${Constants.API_SERVER_HOST}/auth/code?client_id=$clientId&response_type=code&redirect_uri=$redirectUri"
        // 웹 브라우저를 열어 사용자에게 인증을 요청합니다.
        // 구현은 플랫폼에 따라 달라집니다.
    }

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

    suspend fun refreshAuth(refreshToken: String): Map<String, String> {
        val response = apiClient.post("/auth/token", mapOf(
            "grant_type" to "refresh_token",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "refresh_token" to refreshToken
        ))
        // 응답을 파싱하여 토큰 반환
        return parseTokenResponse(response)
    }

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

    data class TokenResponse(
        val access_token: String,
        val refresh_token: String,
        val expires_in: Int,
        val token_type: String
    )
}
