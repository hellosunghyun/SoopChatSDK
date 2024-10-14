package me.hellosunghyun.soopchatsdk.chat

import me.hellosunghyun.soopchatsdk.utils.Constants
import me.hellosunghyun.soopchatsdk.utils.Utils
import kotlinx.coroutines.*
import java.net.URI
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler

/**
 * 채팅 관리를 위한 클래스입니다.
 * 채팅 서버와의 연결, 메시지 송수신, 이벤트 처리 등을 담당합니다.
 *
 * @property chatAddress 채팅 서버 주소
 * @property bjId BJ ID
 * @property accessToken 액세스 토큰
 * @property ticket 채팅 티켓
 * @property chatNo 채팅방 번호
 */
class ChatManager(
    private val chatAddress: String,
    private val bjId: String,
    private val accessToken: String,
    private val ticket: String,
    private val chatNo: Int
) {
    private var webSocket: AsynchronousSocketChannel? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private val eventHandlers = mutableMapOf<String, MutableList<(Any) -> Unit>>()

    init {
        connectWebSocket()
    }

    /**
     * 웹소켓 연결을 시작합니다.
     * 연결이 성공하면 로그인 패킷을 전송하고 메시지 수신을 시작합니다.
     */
    private fun connectWebSocket() {
        scope.launch {
            try {
                val uri = URI(chatAddress)
                webSocket = AsynchronousSocketChannel.open()
                // 웹소켓 연결 로직 구현
                sendLoginPacket()
                listenForMessages()
            } catch (e: Exception) {
                // 에러 처리
                emitEvent("error", e)
            }
        }
    }

    /**
     * 로그인 패킷을 전송합니다.
     * 서버에 사용자 인증 정보를 보내 로그인을 수행합니다.
     */
    private suspend fun sendLoginPacket() {
        val packet = makePacket(Constants.ServiceCode.SVC_SDK_LOGIN.code, mapOf("ticket" to ticket))
        webSocket?.write(ByteBuffer.wrap(packet))
    }

    /**
     * 패킷을 생성합니다.
     *
     * @param serviceCode 서비스 코드
     * @param data 패킷에 포함될 데이터
     * @return 생성된 패킷 바이트 배열
     */
    private fun makePacket(serviceCode: Int, data: Map<String, Any>): ByteArray {
        val packet = mutableListOf<String>()
        packet.add(Constants.PACKET_DELIMITER.toString())
        // 데이터에 따라 패킷 생성 로직 추가
        val header = encodePacketHeader(serviceCode, packet.size)
        val body = encodePacketBody(packet)
        return Utils.mergePacket(header, body)
    }

    /**
     * 패킷 헤더를 인코딩합니다.
     *
     * @param serviceCode 서비스 코드
     * @param bodyLength 패킷 본문의 길이
     * @return 인코딩된 헤더 바이트 배열
     */
    private fun encodePacketHeader(serviceCode: Int, bodyLength: Int): ByteArray {
        val header = StringBuilder()
        header.append('\u001B')
        header.append('\t')
        header.append(Utils.padLeft(serviceCode, 4))
        header.append(Utils.padLeft(bodyLength, 6))
        header.append("00") // 리턴 코드 자리
        return Utils.encodeUTF8(header.toString())
    }

    /**
     * 패킷 본문을 인코딩합니다.
     *
     * @param packet 패킷 데이터 리스트
     * @return 인코딩된 본문 바이트 배열
     */
    private fun encodePacketBody(packet: List<String>): ByteArray {
        val bodyString = packet.joinToString(Constants.PACKET_DELIMITER.toString())
        return Utils.encodeUTF8(bodyString)
    }

    /**
     * 서버로부터 메시지를 수신하기 위한 리스너를 설정합니다.
     */
    private fun listenForMessages() {
        val buffer = ByteBuffer.allocate(1024)
        webSocket?.read(buffer, null, object : CompletionHandler<Int, Nothing?> {
            override fun completed(result: Int, attachment: Nothing?) {
                if (result > 0) {
                    buffer.flip()
                    val data = ByteArray(buffer.limit())
                    buffer.get(data)
                    handleMessage(data)
                    buffer.clear()
                    webSocket?.read(buffer, null, this)
                } else {
                    // 연결 종료
                    emitEvent("closed", null)
                }
            }

            override fun failed(exc: Throwable, attachment: Nothing?) {
                // 에러 처리
                emitEvent("error", exc)
            }
        })
    }

    /**
     * 수신한 메시지를 처리합니다.
     *
     * @param data 수신한 메시지 데이터
     */
    private fun handleMessage(data: ByteArray) {
        // 수신한 메시지를 처리하는 로직 구현
        // 예를 들어 패킷을 파싱하고 이벤트를 발생시킵니다.
        val message = Utils.decodeUTF8(data)
        emitEvent("message", message)
    }

    /**
     * 채팅 메시지를 전송합니다.
     *
     * @param message 전송할 메시지 내용
     * @param type 메시지 타입
     */
    fun sendMessage(message: String, type: String) {
        // 채팅 메시지 전송 로직 구현
        val data = mapOf("message" to message, "type" to type)
        val packet = makePacket(Constants.ServiceCode.SVC_CHATMESG.code, data)
        webSocket?.write(ByteBuffer.wrap(packet))
    }

    /**
     * 채팅 서버와의 연결을 종료합니다.
     */
    fun disconnect() {
        scope.cancel()
        webSocket?.close()
        emitEvent("closed", null)
    }

    /**
     * 이벤트 핸들러를 등록합니다.
     *
     * @param event 이벤트 이름
     * @param handler 이벤트 핸들러 함수
     */
    fun on(event: String, handler: (Any) -> Unit) {
        val handlers = eventHandlers.getOrPut(event) { mutableListOf() }
        handlers.add(handler)
    }

    /**
     * 이벤트를 발생시킵니다.
     *
     * @param event 발생시킬 이벤트 이름
     * @param data 이벤트와 함께 전달할 데이터
     */
    private fun emitEvent(event: String, data: Any?) {
        eventHandlers[event]?.forEach { handler ->
            handler(data ?: Unit)
        }
    }
}
