package me.hellosunghyun.soopchatsdk.utils

/**
 * SoopChatSDK에서 사용되는 모든 상수들을 정의합니다.
 * 이 객체는 SDK 전반에 걸쳐 사용되는 다양한 상수, 열거형, 플래그 등을 포함합니다.
 */
object Constants {
    /**
     * 패킷 구분 문자: 패킷의 각 필드를 구분하기 위해 사용됩니다.
     * Unicode Character 'FORM FEED' (U+000C)를 사용합니다.
     */
    const val PACKET_DELIMITER = '\u000C'

    /**
     * 서비스 코드를 정의합니다.
     * 
     * 채팅 서버와의 통신에 사용되는 서비스 코드를 나타냅니다.
     */
    enum class ServiceCode(val code: Int) {
        /** 서버와의 연결 유지 (Keep-Alive) */
        SVC_KEEPALIVE(0),
        /** 로그인 요청 */
        SVC_LOGIN(1),
        /** 채팅방 입장 */
        SVC_JOINCH(2),
        /** 채팅방 퇴장 */
        SVC_QUITCH(3),
        /** 채팅방 사용자 목록 요청 */
        SVC_CHUSER(4),
        /** 채팅 메시지 전송 */
        SVC_CHATMESG(5),
        /** 채팅방 이름 설정 */
        SVC_SETCHNAME(6),
        /** 방송 상태 설정 */
        SVC_SETBJSTAT(7),
        /** 특정 사용자 채팅 금지 */
        SVC_SETDUMB(8),
        /** 귓속말 전송 */
        SVC_DIRECTCHAT(9),
        /** 공지 메시지 전송 */
        SVC_NOTICE(10),
        /** 사용자 강제 퇴장 */
        SVC_KICK(11),
        /** 사용자 플래그 설정 */
        SVC_SETUSERFLAG(12),
        /** 매니저 임명 또는 해임 */
        SVC_SETSUBBJ(13),
        /** 닉네임 변경 */
        SVC_SETNICKNAME(14),
        /** 서버 상태 요청 */
        SVC_SVRSTAT(15),
        /** SDK 로그인 */
        SVC_SDK_LOGIN(16),
        /** 팬클럽 색상 설정 */
        SVC_CLUBCOLOR(17),
        /** 풍선 선물 */
        SVC_SENDBALLOON(18),
        /** 채팅 얼림 모드 설정 */
        SVC_ICEMODE(19),
        /** 팬레터 선물 */
        SVC_SENDFANLETTER(20),
        /** 확장된 채팅 얼림 모드 설정 */
        SVC_ICEMODE_EX(21),
        /** 채팅 얼림 모드 릴레이 정보 요청 */
        SVC_GET_ICEMODE_RELAY(22),
        /** 슬로우 모드 설정 */
        SVC_SLOWMODE(23),
        /** 불타는 레벨 재로드 */
        SVC_RELOADBURNLEVEL(24),
        /** 블라인드 강제 퇴장 */
        SVC_BLINDKICK(25),
        /** 매니저 채팅 */
        SVC_MANAGERCHAT(26),
        /** 데이터 추가 */
        SVC_APPENDDATA(27),
        /** 야구 이벤트 */
        SVC_BASEBALLEVENT(28),
        /** 유료 아이템 사용 */
        SVC_PAIDITEM(29),
        /** 탑팬 정보 */
        SVC_TOPFAN(30),
        /** SNS 메시지 전송 */
        SVC_SNSMESSAGE(31),
        /** SNS 모드 설정 */
        SVC_SNSMODE(32),
        /** 풍선 선물 (서브) */
        SVC_SENDBALLOONSUB(33),
        /** 팬레터 선물 (서브) */
        SVC_SENDFANLETTERSUB(34),
        /** 탑팬 (서브) */
        SVC_TOPFANSUB(35),
        /** BJ 스티커 아이템 */
        SVC_BJSTICKERITEM(36),
        /** 초콜릿 선물 */
        SVC_CHOCOLATE(37),
        /** 초콜릿 선물 (서브) */
        SVC_CHOCOLATESUB(38),
        /** 탑클랜 정보 */
        SVC_TOPCLAN(39),
        /** 탑클랜 (서브) */
        SVC_TOPCLANSUB(40),
        /** 슈퍼챗 전송 */
        SVC_SUPERCHAT(41),
        /** 티켓 업데이트 */
        SVC_UPDATETICKET(42),
        /** 게임 랭커 알림 */
        SVC_NOTIGAMERANKER(43),
        /** 스타코인 전송 */
        SVC_STARCOIN(44),
        /** 퀵뷰 선물 */
        SVC_SENDQUICKVIEW(45),
        /** 아이템 상태 조회 */
        SVC_ITEMSTATUS(46),
        /** 아이템 사용 중 표시 */
        SVC_ITEMUSING(47),
        /** 퀵뷰 사용 */
        SVC_USEQUICKVIEW(48),
        /** 투표 알림 */
        SVC_NOTIFY_POLL(50),
        /** 채팅 차단 모드 설정 */
        SVC_CHATBLOCKMODE(51),
        /** 블랙리스트 정보 추가 */
        SVC_BDM_ADDBLACKINFO(52),
        /** 방송 정보 설정 */
        SVC_SETBROADINFO(53),
        /** 금지어 설정 */
        SVC_BAN_WORD(54),
        /** 관리자 공지 메시지 전송 */
        SVC_SENDADMINNOTICE(58),
        /** 프리캣 방장 입장 */
        SVC_FREECAT_OWNER_JOIN(65),
        /** 상품 구매 */
        SVC_BUYGOODS(70),
        /** 상품 구매 (서브) */
        SVC_BUYGOODSSUB(71),
        /** 프로모션 전송 */
        SVC_SENDPROMOTION(72),
        /** VR 알림 */
        SVC_NOTIFY_WITHVR(74),
        /** 모바일 방송 일시정지 알림 */
        SVC_NOTIFY_MOBBROAD_PAUSE(75),
        /** 강제 퇴장 및 취소 */
        SVC_KICK_AND_CANCEL(76),
        /** 강제 퇴장 사용자 목록 */
        SVC_KICK_USERLIST(77),
        /** 관리자 채팅 사용자 목록 요청 */
        SVC_ADMIN_CHUSER(78),
        /** 클린도배 정보 */
        SVC_CLIDOBAEINFO(79),
        /** VOD 풍선 */
        SVC_VODBALLOON(86),
        /** 광고 효과 */
        SVC_ADCON_EFFECT(87),
    }

    /**
     * 사용자 플래그를 정의하는 객체입니다.
     * 각 사용자의 상태나 권한을 나타내는 비트 플래그들을 포함합니다.
     * 플래그는 두 그룹으로 나뉘어 있으며, 각 그룹은 32비트를 사용합니다.
     */
    object UserFlag {
        // where 1 플래그 (첫 번째 플래그 그룹)
        /** 관리자 권한을 가진 사용자 */
        const val ADMIN = 1 shl 0
        /** 숨겨진 사용자 (채팅방에서 보이지 않음) */
        const val HIDDEN = 1 shl 1
        /** BJ (방송 진행자) */
        const val BJ = 1 shl 2
        /** 채팅 금지된 사용자 */
        const val DUMB = 1 shl 3
        /** 게스트 사용자 (임시 계정) */
        const val GUEST = 1 shl 4
        /** 팬클럽 회원 */
        const val FANCLUB = 1 shl 5
        /** 자동으로 매니저 권한이 부여된 사용자 */
        const val AUTOMANAGER = 1 shl 6
        /** 매니저 목록에 포함된 사용자 */
        const val MANAGERLIST = 1 shl 7
        /** 매니저 권한을 가진 사용자 */
        const val MANAGER = 1 shl 8
        /** 여성 사용자 */
        const val FEMALE = 1 shl 9
        /** 자동으로 채팅 금지된 사용자 */
        const val AUTODUMB = 1 shl 10
        /** 블라인드 처리된 채팅 금지 사용자 */
        const val DUMB_BLIND = 1 shl 11
        /** 도배 방지 플래그가 설정된 사용자 */
        const val DOBAE_BLIND = 1 shl 12
        /** 채팅방에서 퇴장한 사용자 */
        const val EXITUSER = 1 shl 13
        /** 모바일 기기로 접속한 사용자 */
        const val MOBILE = 1 shl 14
        /** 탑팬 (Top Fan) 사용자 */
        const val TOPFAN = 1 shl 15
        /** 실명 인증된 사용자 */
        const val REALNAME = 1 shl 16
        /** 귓속말 수신을 거부한 사용자 */
        const val NODIRECT = 1 shl 17
        /** 글로벌 앱을 사용하는 사용자 */
        const val GLOBAL_APP = 1 shl 18
        /** 퀵뷰 기능을 사용하는 사용자 */
        const val QUICKVIEW = 1 shl 19
        /** 후원 스티커를 사용한 사용자 */
        const val SPTR_STICKER = 1 shl 20
        /** 크롬캐스트를 사용하는 사용자 */
        const val CHROMECAST = 1 shl 21
        // 비트 22는 현재 사용되지 않음
        /** 모바일 웹으로 접속한 사용자 */
        const val MOBILE_WEB = 1 shl 23
        /** 도배 방지 플래그 2가 설정된 사용자 */
        const val DOBAE_BLIND2 = 1 shl 24
        // 비트 25-27은 현재 사용되지 않음
        /** 팔로워 */
        const val FOLLOWER = 1 shl 28
        // 비트 29는 현재 사용되지 않음
        /** VOD 풍선 알림을 설정한 사용자 */
        const val NOTIVODBALLOON = 1 shl 30
        /** 탑팬 알림을 설정한 사용자 */
        const val NOTITOPFAN = 1 shl 31

        // where 2 플래그 (두 번째 플래그 그룹)
        /** 글로벌 PC 버전으로 접속한 사용자 */
        const val GLOBAL_PC = 1 shl 0
        /** 클랜 회원 */
        const val CLAN = 1 shl 1
        /** 탑클랜 회원 */
        const val TOPCLAN = 1 shl 2
        /** 상위 20위 안에 든 사용자 */
        const val TOP20 = 1 shl 3
        /** '게임의 신' 등급 사용자 */
        const val GAMEGOD = 1 shl 4
        /** A태그 사용이 허용된 사용자 */
        const val ATAG_ALLOW = 1 shl 5
        /** 슈퍼챗 수신을 거부한 사용자 */
        const val NOSUPERCHAT = 1 shl 6
        /** 채팅 수신을 거부한 ���용자 */
        const val NORECVCHAT = 1 shl 7
        /** 플래시 플레이어를 사용하는 사용자 */
        const val FLASH = 1 shl 8
        /** LG 게임 사용자 */
        const val LGGAME = 1 shl 9
        /** 아프리카TV 직원 계정 */
        const val EMPLOYEE = 1 shl 10
        /** 클린아티 활동 참여 사용자 */
        const val CLEANATI = 1 shl 11
        /** 경찰 계정 (특별 관리 계정) */
        const val POLICE = 1 shl 12
        /** 관리자 채팅 권한을 가진 사용자 */
        const val ADMINCHAT = 1 shl 13
        /** PC로 접속한 사용자 */
        const val PC = 1 shl 14
        /** 특정 조건에 해당하는 사용자 */
        const val SPECIFY = 1 shl 15
    }

    /**
     * 이벤트 타입을 정의하는 열거형 클래스입니다.
     * 채팅 SDK에서 발생할 수 있는 다양한 이벤트 유형을 나타냅니다.
     */
    enum class EventType {
        /** 디버깅 목적의 메시지 이벤트 */
        DEBUGGING_MESSAGE,
        /** 일반적인 채팅 메시지 이벤트 */
        MESSAGE,
        /** SDK가 사용 준비를 마쳤음을 나타내는 이벤트 */
        READY,
        /** 채팅 연결이 종료되었음을 나타내는 이벤트 */
        CLOSED,
        /** 오류가 발생했음을 나타내는 이벤트 */
        ERROR
    }

    /**
     * 채팅 서버 주소 포맷
     * 실제 연결 시 이 포맷에 맞춰 서버 주소를 생성합니다.
     * %s: 서버 식별자, %d: 포트 번호, %s: 추가 경로
     */
    const val CHAT_SERVER_HOST = "wss://chat-%s.afreecatv.com:%d/Websocket/%s"

    /** 
     * API 서버 주소
     * 아프리카TV의 Open API 엔드포인트 주소입니다.
     */
    const val API_SERVER_HOST = "https://openapi.afreecatv.com"

    /**
     * 라이브 서버 주소
     * 아프리카TV의 라이브 스트리밍 서버 주소입니다.
     */
    const val LIVE_SERVER_HOST = "https://live.afreecatv.com"

    /**
     * 채팅 메시지 타입을 정의하는 열거형 클래스입니다.
     * 각 메시지의 성격이나 목적을 구분합니다.
     */
    enum class ChatMessageType(val type: Int) {
        /** 일반적인 채팅 메시지 */
        NORMAL(0),
        /** 공지사항 메시지 */
        NOTICE(1),
        /** 경고 메시지 */
        WARNING(2),
        /** 번역된 메시지 */
        TRANSLATION(3)
    }

    /**
     * 플랫폼 타입을 정의하는 열거형 클래스입니다.
     * 사용자가 접속한 플랫폼의 종류를 나타냅니다.
     */
    enum class PlatformType(val value: String) {
        /** 데스크톱 컴퓨터를 통한 접속 */
        DESKTOP("DESKTOP"),
        /** 모바일 기기를 통한 접속 */
        MOBILE("MOBILE")
    }

    /**
     * 채팅 얼림 모드 플래그를 정의하는 객체입니다.
     * 채팅 얼림 모드에서 특정 사용자 그룹에게 채팅을 허용할 때 사용됩니다.
     */
    object FreezeFlag {
        /** 모든 사용자의 채팅을 금지 */
        const val NONE = 0
        /** BJ에게만 채팅 허용 */
        const val BJ = 1 shl 4
        /** 팬클럽 회원에게 채팅 허용 */
        const val FAN = 1 shl 5
        /** 후원자에게 채팅 허용 */
        const val SUPPORTER = 1 shl 6
        /** 탑팬에게 채팅 허용 */
        const val TOP_FAN = 1 shl 7
        /** 팔로워에게 채팅 허용 */
        const val FOLLOWER = 1 shl 8
        /** 매니저에게 채팅 허용 */
        const val MANAGER = 1 shl 9
    }
}