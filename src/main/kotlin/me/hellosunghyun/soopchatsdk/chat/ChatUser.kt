package me.hellosunghyun.soopchatsdk.chat

import me.hellosunghyun.soopchatsdk.utils.Constants.UserFlag

class ChatUser(
    var userId: String? = null,
    var userNickname: String? = null,
    private var flag1: Int = 0,
    private var flag2: Int = 0,
    var subscriptionMonth: Int = -1
) {
    // 플래그 유틸리티 함수
    private fun hasFlag(flag: Int, target: Int): Boolean {
        return flag and target == target
    }

    // 사용자 상태 반환
    fun getStatus(): Map<String, Boolean> {
        return mapOf(
            "isBJ" to hasFlag(flag1, UserFlag.BJ),
            "isManager" to hasFlag(flag1, UserFlag.MANAGER),
            "isGuest" to hasFlag(flag1, UserFlag.GUEST),
            "isTopFan" to hasFlag(flag1, UserFlag.TOPFAN),
            "isFan" to hasFlag(flag1, UserFlag.FANCLUB),
            "isFollower" to hasFlag(flag1, UserFlag.FOLLOWER),
            "isSupporter" to hasFlag(flag1, UserFlag.SPTR_STICKER),
            "hasQuickView" to hasFlag(flag1, UserFlag.QUICKVIEW),
            "isTranslatable" to (hasFlag(flag1, UserFlag.BJ) || hasFlag(flag1, UserFlag.MANAGER) || hasFlag(flag1, UserFlag.FANCLUB) || hasFlag(flag1, UserFlag.TOPFAN))
        )
    }

    // 사용자 ID 반환
    fun getId(): String? {
        return userId
    }

    // 사용자 닉네임 반환
    fun getNickname(): String? {
        return userNickname
    }

    // 혼합된 플래그 반환
    fun getMixedFlag(flag1: Int?, flag2: Int?): String {
        return "${flag1 ?: 0}|${flag2 ?: 0}"
    }

    // 플래그 설정
    fun setFlag(flag: String) {
        val flags = flag.split("|")
        flag1 = flags.getOrNull(0)?.toIntOrNull() ?: 0
        flag2 = flags.getOrNull(1)?.toIntOrNull() ?: 0
    }

    // 사용자 정보 설정
    fun setUser(userInfo: Map<String, Any>) {
        userId = userInfo["userId"] as? String
        userNickname = userInfo["userNickname"] as? String
        val flag = userInfo["flag"] as? String ?: "0|0"
        setFlag(flag)
        subscriptionMonth = userInfo["subscriptionMonth"] as? Int ?: -1
    }

    // 다른 사용자와 비교하여 같은 사용자인지 확인
    fun isMe(otherUserId: String): Boolean {
        return userId == otherUserId
    }

    // 사용자 정보 반환
    fun getUser(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "userNickname" to userNickname,
            "flag1" to flag1,
            "flag2" to flag2,
            "flag" to getMixedFlag(flag1, flag2),
            "subscriptionMonth" to subscriptionMonth
        )
    }

    // 사용자 정보와 상태 반환
    fun getUserWithStatus(): Map<String, Any?> {
        return getUser() + mapOf("userStatus" to getStatus())
    }

    // 플래그 추가
    fun addFlag(vararg flags: Int) {
        flags.forEach {
            flag1 = flag1 or it
        }
    }

    // 플래그 제거
    fun removeFlag(vararg flags: Int) {
        flags.forEach {
            flag1 = flag1 and it.inv()
        }
    }
}
