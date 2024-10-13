package api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.Constants
import java.net.HttpURLConnection
import java.net.URL

class ApiClient(private val baseUrl: String) {

    suspend fun get(path: String, params: Map<String, String>? = null): String = withContext(Dispatchers.IO) {
        val url = buildUrl(path, params)
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        handleResponse(connection)
    }

    suspend fun post(path: String, data: Map<String, String>): String = withContext(Dispatchers.IO) {
        val url = URL("$baseUrl$path")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true

        val postData = data.map { "${it.key}=${it.value}" }.joinToString("&")
        connection.outputStream.write(postData.toByteArray(Charsets.UTF_8))

        handleResponse(connection)
    }

    private fun buildUrl(path: String, params: Map<String, String>?): String {
        val paramString = params?.map { "${it.key}=${it.value}" }?.joinToString("&") ?: ""
        return if (paramString.isNotEmpty()) "$baseUrl$path?$paramString" else "$baseUrl$path"
    }

    private fun handleResponse(connection: HttpURLConnection): String {
        return if (connection.responseCode in 200..299) {
            connection.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        } else {
            handleErrorResponse(connection)
        }
    }

    // 에러 응답을 처리하는 함수
    private fun handleErrorResponse(connection: HttpURLConnection): String {
        return connection.errorStream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() } ?: "Unknown error"
    }
}
