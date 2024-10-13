package me.hellosunghyun.soopchatsdk.chat

import me.hellosunghyun.soopchatsdk.utils.Constants

object ChatRoomState {
    var chatNumber: Int = 0
    var bjId: String = ""
    var bannedWords: List<String> = listOf()
    var isFreezing: Boolean = false
    var freezeFlag: Int = 0
    var freezeThresholdBySubscriptionMonths: Int = 1
    var freezeThresholdByBalloons: Int = 0
    var managerCount: Int = 0
    var maxManagerCount: Int = 10
    var currentPenaltyCount: Int = 0
    var lastWhisperUserId: String = ""
    var _chatNotice: ChatNotice = ChatNotice()

    data class ChatNotice(
        var isShow: Boolean = false,
        var message: String = ""
    )

    fun init(options: Map<String, Any>) {
        chatNumber = options["chatNumber"] as? Int ?: 0
        bjId = options["bjId"] as? String ?: ""
        maxManagerCount = options["maxManagerCount"] as? Int ?: 10
    }

    fun updateManagerCount(count: Int) {
        managerCount = count
    }

    fun incrementPenaltyCount() {
        currentPenaltyCount++
    }

    fun resetPenaltyCount() {
        currentPenaltyCount = 0
    }

    fun setChatNotice(show: Boolean, message: String) {
        _chatNotice.isShow = show
        _chatNotice.message = message
    }

    fun getChatNotice(): ChatNotice {
        return _chatNotice
    }
}
