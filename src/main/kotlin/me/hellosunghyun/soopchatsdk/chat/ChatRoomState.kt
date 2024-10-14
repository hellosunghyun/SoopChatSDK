package me.hellosunghyun.soopchatsdk.chat

import me.hellosunghyun.soopchatsdk.utils.Constants

/**
 * 채팅방의 현재 상태를 관리하는 객체입니다.
 * 채팅방 번호, BJ ID, 금지어 목록, 얼리기 상태 등 다양한 채팅방 관련 정보를 포함합니다.
 */
object ChatRoomState {
    /** 채팅방 번호 */
    var chatNumber: Int = 0
    
    /** BJ의 ID */
    var bjId: String = ""
    
    /** 채팅방에서 사용이 금지된 단어 목록 */
    var bannedWords: List<String> = listOf()
    
    /** 채팅방 얼리기 상태 */
    var isFreezing: Boolean = false
    
    /** 채팅방 얼리기 플래그 */
    var freezeFlag: Int = 0
    
    /** 구독 개월 수에 따른 얼리기 임계값 */
    var freezeThresholdBySubscriptionMonths: Int = 1
    
    /** 풍선 개수에 따른 얼리기 임계값 */
    var freezeThresholdByBalloons: Int = 0
    
    /** 현재 매니저 수 */
    var managerCount: Int = 0
    
    /** 최대 매니저 수 */
    var maxManagerCount: Int = 10
    
    /** 현재 패널티 카운트 */
    var currentPenaltyCount: Int = 0
    
    /** 마지막으로 귓속말을 보낸 사용자 ID */
    var lastWhisperUserId: String = ""
    
    /** 채팅 공지사항 */
    var _chatNotice: ChatNotice = ChatNotice()

    /**
     * 채팅 공지사항을 나타내는 데이터 클래스입니다.
     *
     * @property isShow 공지사항 표시 여부
     * @property message 공지사항 메시지
     */
    data class ChatNotice(
        var isShow: Boolean = false,
        var message: String = ""
    )

    /**
     * 채팅방 상태를 초기화합니다.
     *
     * @param options 초기화에 사용할 옵션 맵
     */
    fun init(options: Map<String, Any>) {
        chatNumber = options["chatNumber"] as? Int ?: 0
        bjId = options["bjId"] as? String ?: ""
        maxManagerCount = options["maxManagerCount"] as? Int ?: 10
    }

    /**
     * 매니저 수를 업데이트합니다.
     *
     * @param count 새로운 매니저 수
     */
    fun updateManagerCount(count: Int) {
        managerCount = count
    }

    /**
     * 패널티 카운트를 증가시킵니다.
     */
    fun incrementPenaltyCount() {
        currentPenaltyCount++
    }

    /**
     * 패널티 카운트를 초기화합니다.
     */
    fun resetPenaltyCount() {
        currentPenaltyCount = 0
    }

    /**
     * 채팅 공지사항을 설정합니다.
     *
     * @param show 공지사항 표시 여부
     * @param message 공지사항 메시지
     */
    fun setChatNotice(show: Boolean, message: String) {
        _chatNotice.isShow = show
        _chatNotice.message = message
    }

    /**
     * 현재 채팅 공지사항을 반환합니다.
     *
     * @return 현재 설정된 채팅 공지사항
     */
    fun getChatNotice(): ChatNotice {
        return _chatNotice
    }
}
