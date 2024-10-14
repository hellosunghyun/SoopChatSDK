package me.hellosunghyun.soopchatsdk.chat

import me.hellosunghyun.soopchatsdk.utils.Constants.UserFlag

/**
 * 채팅 사용자 정보를 나타내는 클래스입니다.
 *
 * @property userId 사용자 ID
 * @property userNickname 사용자 닉네임
 * @property flag1 사용자 상태 플래그 1
 * @property flag2 사용자 상태 플래그 2
 * @property subscriptionMonth 구독 개월 수
 */
class ChatUser(
    var userId: String? = null,
    var userNickname: String? = null,
    private var flag1: Int = 0,
    private var flag2: Int = 0,
    var subscriptionMonth: Int = -1
) {
    /**
     * 특정 플래그가 설정되어 있는지 확인합니다.
     *
     * @param flag 확인할 플래그
     * @param target 대상 플래그 값
     * @return 플래그가 설정되어 있으면 true, 아니면 false
     */
    private fun hasFlag(flag: Int, target: Int): Boolean {
        return flag and target == target
    }

    /**
     * 사용자의 현재 상태를 반환합니다.
     *
     * @return 사용자 상태를 나타내는 Map
     */
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

    /**
     * 사용자 ID를 반환합니다.
     *
     * @return 사용자 ID
     */
    fun getId(): String? {
        return userId
    }

    /**
     * 사용자 닉네임을 반환합니다.
     *
     * @return 사용자 닉네임
     */
    fun getNickname(): String? {
        return userNickname
    }

    /**
     * 혼합된 플래그 문자열을 반환합니다.
     *
     * @param flag1 플래그 1
     * @param flag2 플래그 2
     * @return 혼합된 플래그 문자열
     */
    fun getMixedFlag(flag1: Int?, flag2: Int?): String {
        return "${flag1 ?: 0}|${flag2 ?: 0}"
    }

    /**
     * 플래그를 설정합니다.
     *
     * @param flag 설정할 플래그 문자열
     */
    fun setFlag(flag: String) {
        val flags = flag.split("|")
        flag1 = flags.getOrNull(0)?.toIntOrNull() ?: 0
        flag2 = flags.getOrNull(1)?.toIntOrNull() ?: 0
    }

    /**
     * 사용자 정보를 설정합니다.
     *
     * @param userInfo 사용자 정보를 담은 Map
     */
    fun setUser(userInfo: Map<String, Any>) {
        userId = userInfo["userId"] as? String
        userNickname = userInfo["userNickname"] as? String
        val flag = userInfo["flag"] as? String ?: "0|0"
        setFlag(flag)
        subscriptionMonth = userInfo["subscriptionMonth"] as? Int ?: -1
    }

    /**
     * 주어진 사용자 ID와 현재 사용자가 같은지 확인합니다.
     *
     * @param otherUserId 비교할 사용자 ID
     * @return 같은 사용자이면 true, 아니면 false
     */
    fun isMe(otherUserId: String): Boolean {
        return userId == otherUserId
    }

    /**
     * 사용자 정보를 Map 형태로 반환합니다.
     *
     * @return 사용자 정보를 담은 Map
     */
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

    /**
     * 사용자 정보와 상태를 함께 Map 형태로 반환합니다.
     *
     * @return 사용자 정보와 상태를 담은 Map
     */
    fun getUserWithStatus(): Map<String, Any?> {
        return getUser() + mapOf("userStatus" to getStatus())
    }

    /**
     * 플래그를 추가합니다.
     *
     * @param flags 추가할 플래그들
     */
    fun addFlag(vararg flags: Int) {
        flags.forEach {
            flag1 = flag1 or it
        }
    }

    /**
     * 플래그를 제거합니다.
     *
     * @param flags 제거할 플래그들
     */
    fun removeFlag(vararg flags: Int) {
        flags.forEach {
            flag1 = flag1 and it.inv()
        }
    }
}
