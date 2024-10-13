package utils

import java.nio.ByteBuffer
import java.nio.charset.Charset

// 유틸리티 함수 모음

object Utils {
    fun encodeUTF8(input: String): ByteArray {
        return input.toByteArray(Charsets.UTF_8)
    }

    fun decodeUTF8(input: ByteArray): String {
        return String(input, Charsets.UTF_8)
    }

    fun mergePacket(header: ByteArray, body: ByteArray): ByteArray {
        return header + body
    }

    // 숫자를 지정된 길이의 문자열로 패딩하는 함수
    fun padLeft(value: Int, totalWidth: Int, paddingChar: Char = '0'): String {
        return value.toString().padStart(totalWidth, paddingChar)
    }

    // 패킷 분할 함수 (데이터를 구분자로 분할)
    fun splitPacket(data: String, delimiter: Char): List<String> {
        return data.split(delimiter)
    }

    // 유효한 JSON 문자열인지 확인하는 함수
    fun isValidJson(jsonString: String): Boolean {
        return try {
            val gson = com.google.gson.Gson()
            gson.fromJson(jsonString, Any::class.java)
            true
        } catch (e: Exception) {
            false
        }
    }
}
