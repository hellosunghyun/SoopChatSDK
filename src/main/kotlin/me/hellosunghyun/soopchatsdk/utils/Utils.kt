package me.hellosunghyun.soopchatsdk.utils

import java.nio.ByteBuffer
import java.nio.charset.Charset

/**
 * SoopChatSDK에서 사용되는 유틸리티 함수들을 모아놓은 객체입니다.
 */
object Utils {
    /**
     * 주어진 문자열을 UTF-8로 인코딩합니다.
     *
     * @param input 인코딩할 문자열
     * @return UTF-8로 인코딩된 바이트 배열
     */
    fun encodeUTF8(input: String): ByteArray {
        return input.toByteArray(Charsets.UTF_8)
    }

    /**
     * UTF-8로 인코딩된 바이트 배열을 문자열로 디코딩합니다.
     *
     * @param input 디코딩할 UTF-8 바이트 배열
     * @return 디코딩된 문자열
     */
    fun decodeUTF8(input: ByteArray): String {
        return String(input, Charsets.UTF_8)
    }

    /**
     * 헤더와 바디 바이트 배열을 하나의 패킷으로 병합합니다.
     *
     * @param header 패킷의 헤더 바이트 배열
     * @param body 패킷의 바디 바이트 배열
     * @return 병합된 패킷 바이트 배열
     */
    fun mergePacket(header: ByteArray, body: ByteArray): ByteArray {
        return header + body
    }

    /**
     * 숫자를 지정된 길이의 문자열로 패딩합니다.
     *
     * @param value 패딩할 숫자
     * @param totalWidth 결과 문자열의 총 길이
     * @param paddingChar 패딩에 사용할 문자 (기본값은 '0')
     * @return 패딩된 문자열
     */
    fun padLeft(value: Int, totalWidth: Int, paddingChar: Char = '0'): String {
        return value.toString().padStart(totalWidth, paddingChar)
    }

    /**
     * 데이터를 주어진 구분자로 분할합니다.
     *
     * @param data 분할할 데이터 문자열
     * @param delimiter 구분자
     * @return 분할된 문자열 리스트
     */
    fun splitPacket(data: String, delimiter: Char): List<String> {
        return data.split(delimiter)
    }

    /**
     * 주어진 문자열이 유효한 JSON인지 확인합니다.
     *
     * @param jsonString 검사할 JSON 문자열
     * @return 유효한 JSON이면 true, 아니면 false
     */
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
