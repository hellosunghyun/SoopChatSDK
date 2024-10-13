package chat

import utils.Constants
import utils.Utils
import kotlinx.coroutines.*
import java.net.URI
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler

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

    private suspend fun sendLoginPacket() {
        val packet = makePacket(Constants.ServiceCode.SVC_SDK_LOGIN.code, mapOf("ticket" to ticket))
        webSocket?.write(ByteBuffer.wrap(packet))
    }

    private fun makePacket(serviceCode: Int, data: Map<String, Any>): ByteArray {
        val packet = mutableListOf<String>()
        packet.add(Constants.PACKET_DELIMITER.toString())
        // 데이터에 따라 패킷 생성 로직 추가
        val header = encodePacketHeader(serviceCode, packet.size)
        val body = encodePacketBody(packet)
        return Utils.mergePacket(header, body)
    }

    private fun encodePacketHeader(serviceCode: Int, bodyLength: Int): ByteArray {
        val header = StringBuilder()
        header.append('\u001B')
        header.append('\t')
        header.append(Utils.padLeft(serviceCode, 4))
        header.append(Utils.padLeft(bodyLength, 6))
        header.append("00") // 리턴 코드 자리
        return Utils.encodeUTF8(header.toString())
    }

    private fun encodePacketBody(packet: List<String>): ByteArray {
        val bodyString = packet.joinToString(Constants.PACKET_DELIMITER.toString())
        return Utils.encodeUTF8(bodyString)
    }

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

    private fun handleMessage(data: ByteArray) {
        // 수신한 메시지를 처리하는 로직 구현
        // 예를 들어 패킷을 파싱하고 이벤트를 발생시킵니다.
        val message = Utils.decodeUTF8(data)
        emitEvent("message", message)
    }

    fun sendMessage(message: String, type: String) {
        // 채팅 메시지 전송 로직 구현
        val data = mapOf("message" to message, "type" to type)
        val packet = makePacket(Constants.ServiceCode.SVC_CHATMESG.code, data)
        webSocket?.write(ByteBuffer.wrap(packet))
    }

    fun disconnect() {
        scope.cancel()
        webSocket?.close()
        emitEvent("closed", null)
    }

    // 이벤트 핸들러 등록
    fun on(event: String, handler: (Any) -> Unit) {
        val handlers = eventHandlers.getOrPut(event) { mutableListOf() }
        handlers.add(handler)
    }

    // 이벤트 발생
    private fun emitEvent(event: String, data: Any?) {
        eventHandlers[event]?.forEach { handler ->
            handler(data ?: Unit)
        }
    }
}
