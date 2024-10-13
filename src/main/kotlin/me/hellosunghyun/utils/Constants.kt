package utils

// 상수 및 열거형 정의를 담고 있는 Constants 객체
object Constants {
    // 패킷 구분 문자: 패킷의 각 필드를 구분하기 위해 사용
    const val PACKET_DELIMITER = '\u000C' // Unicode Character 'FORM FEED' (U+000C)

    // 서비스 코드 정의: 각 서비스에 대한 코드 번호를 열거형으로 정의
    enum class ServiceCode(val code: Int) {
        SVC_KEEPALIVE(0),                // 서버와의 연결 유지 (Keep-Alive)
        SVC_LOGIN(1),                    // 로그인 요청
        SVC_JOINCH(2),                   // 채팅방 입장
        SVC_QUITCH(3),                   // 채팅방 퇴장
        SVC_CHUSER(4),                   // 채팅방 사용자 목록 요청
        SVC_CHATMESG(5),                 // 채팅 메시지 전송
        SVC_SETCHNAME(6),                // 채팅방 이름 설정
        SVC_SETBJSTAT(7),                // 방송 상태 설정
        SVC_SETDUMB(8),                  // 특정 사용자 채팅 금지
        SVC_DIRECTCHAT(9),               // 귓속말 전송
        SVC_NOTICE(10),                  // 공지 메시지 전송
        SVC_KICK(11),                    // 사용자 강제 퇴장
        SVC_SETUSERFLAG(12),             // 사용자 플래그 설정
        SVC_SETSUBBJ(13),                // 매니저 임명 또는 해임
        SVC_SETNICKNAME(14),             // 닉네임 변경
        SVC_SVRSTAT(15),                 // 서버 상태 요청
        SVC_SDK_LOGIN(16),               // SDK 로그인
        SVC_CLUBCOLOR(17),               // 팬클럽 색상 설정
        SVC_SENDBALLOON(18),             // 풍선 선물
        SVC_ICEMODE(19),                 // 채팅 얼림 모드 설정
        SVC_SENDFANLETTER(20),           // 팬레터 선물
        SVC_ICEMODE_EX(21),              // 확장된 채팅 얼림 모드 설정
        SVC_GET_ICEMODE_RELAY(22),       // 채팅 얼림 모드 릴레이 정보 요청
        SVC_SLOWMODE(23),                // 슬로우 모드 설정
        SVC_RELOADBURNLEVEL(24),         // 불타는 레벨 재로드
        SVC_BLINDKICK(25),               // 블라인드 강제 퇴장
        SVC_MANAGERCHAT(26),             // 매니저 채팅
        SVC_APPENDDATA(27),              // 데이터 추가
        SVC_BASEBALLEVENT(28),           // 야구 이벤트
        SVC_PAIDITEM(29),                // 유료 아이템 사용
        SVC_TOPFAN(30),                  // 탑팬 정보
        SVC_SNSMESSAGE(31),              // SNS 메시지 전송
        SVC_SNSMODE(32),                 // SNS 모드 설정
        SVC_SENDBALLOONSUB(33),          // 풍선 선물 (서브)
        SVC_SENDFANLETTERSUB(34),        // 팬레터 선물 (서브)
        SVC_TOPFANSUB(35),               // 탑팬 (서브)
        SVC_BJSTICKERITEM(36),           // BJ 스티커 아이템
        SVC_CHOCOLATE(37),               // 초콜릿 선물
        SVC_CHOCOLATESUB(38),            // 초콜릿 선물 (서브)
        SVC_TOPCLAN(39),                 // 탑클랜 정보
        SVC_TOPCLANSUB(40),              // 탑클랜 (서브)
        SVC_SUPERCHAT(41),               // 슈퍼챗 전송
        SVC_UPDATETICKET(42),            // 티켓 업데이트
        SVC_NOTIGAMERANKER(43),          // 게임 랭커 알림
        SVC_STARCOIN(44),                // 스타코인 전송
        SVC_SENDQUICKVIEW(45),           // 퀵뷰 선물
        SVC_ITEMSTATUS(46),              // 아이템 상태 조회
        SVC_ITEMUSING(47),               // 아이템 사용 중 표시
        SVC_USEQUICKVIEW(48),            // 퀵뷰 사용
        // 번호 49는 사용되지 않음
        SVC_NOTIFY_POLL(50),             // 투표 알림
        SVC_CHATBLOCKMODE(51),           // 채팅 차단 모드 설정
        SVC_BDM_ADDBLACKINFO(52),        // 블랙리스트 정보 추가
        SVC_SETBROADINFO(53),            // 방송 정보 설정
        SVC_BAN_WORD(54),                // 금지어 설정
        // 번호 55~57은 사용되지 않음
        SVC_SENDADMINNOTICE(58),         // 관리자 공지 메시지 전송
        // 번호 59~64는 사용되지 않음
        SVC_FREECAT_OWNER_JOIN(65),      // 프리캣 방장 입장
        // 번호 66~69는 사용되지 않음
        SVC_BUYGOODS(70),                // 상품 구매
        SVC_BUYGOODSSUB(71),             // 상품 구매 (서브)
        SVC_SENDPROMOTION(72),           // 프로모션 전송
        // 번호 73은 사용되지 않음
        SVC_NOTIFY_WITHVR(74),           // VR 알림
        SVC_NOTIFY_MOBBROAD_PAUSE(75),   // 모바일 방송 일시정지 알림
        SVC_KICK_AND_CANCEL(76),         // 강제 퇴장 및 취소
        SVC_KICK_USERLIST(77),           // 강제 퇴장 사용자 목록
        SVC_ADMIN_CHUSER(78),            // 관리자 채팅 사용자 목록 요청
        SVC_CLIDOBAEINFO(79),            // 클린도배 정보
        // 번호 80~85는 사용되지 않음
        SVC_VODBALLOON(86),              // VOD 풍선
        SVC_ADCON_EFFECT(87),            // 광고 효과
        // 번호 88~89는 사용되지 않음
        SVC_KICKMSG_STATE(90),           // 강제 퇴장 메시지 상태
        SVC_FOLLOW_ITEM(91),             // 팔로우 아이템
        SVC_ITEM_SELL_EFFECT(92),        // 아이템 판매 효과
        SVC_FOLLOW_ITEM_EFFECT(93),      // 팔로우 아이템 효과
        SVC_TRANSLATION_STATE(94),       // 번역 상태
        SVC_TRANSLATION(95),             // 번역
        // 번호 96~101은 사용되지 않음
        SVC_GIFT_TICKET(102),            // 티켓 선물
        SVC_VODADCON(103),               // VOD 광고
        SVC_BJ_NOTICE(104),              // BJ 공지
        SVC_VIDEO_BALLOON(105),          // 비디오 풍선
        // 번호 106은 사용되지 않음
        SVC_STATION_ADCON(107),          // 스테이션 광고
        SVC_SENDSUBSCRIPTION(108),       // 구독 선물
        SVC_OGQ_EMOTICON(109),           // OGQ 이모티콘
        SVC_CHUSER_STATE(110),           // 채팅 사용자 상태
        SVC_ITEM_DROPS(111),             // 아이템 드롭
        // 번호 112~116은 사용되지 않음
        SVC_VIDEOBALLOON_LINK(117),      // 비디오 풍선 링크
        SVC_OGQ_EMOTICON_GIFT(118),      // OGQ 이모티콘 선물
        SVC_AD_IN_BROAD_JSON(119),       // 방송 중 광고 JSON 데이터
        SVC_GEM_ITEMSEND(120),           // 잼 아이템 전송
        SVC_MISSION(121),                // 미션 관련
        SVC_SUBTITLE(122),               // 자막 서비스
        // 번호 123~124는 사용되지 않음
        SVC_MISSION_SETTLE(125),         // 미션 정산
        SVC_GET_ADMIN_FLAG(126),         // 관리자 플래그 조회
        SVC_CHUSER_EXTEND(127),          // 채팅 사용자 목록 확장
        SVC_ADMIN_CHUSER_EXTEND(128)     // 관리자 채팅 사용자 목록 확장
        // 필요한 모든 서비스 코드를 번호 순서대로 추가하였습니다.
    }

    // 사용자 플래그 정의: 각 사용자 상태를 나타내는 비트 플래그를 정의
    object UserFlag {
        // where 1 플래그 (첫 번째 플래그 그룹)
        const val ADMIN = 1 shl 0            // 관리자 권한을 가진 사용자
        const val HIDDEN = 1 shl 1           // 숨겨진 사용자
        const val BJ = 1 shl 2               // BJ (방송 진행자)
        const val DUMB = 1 shl 3             // 채팅 금지된 사용자
        const val GUEST = 1 shl 4            // 게스트 사용자
        const val FANCLUB = 1 shl 5          // 팬클럽 회원
        const val AUTOMANAGER = 1 shl 6      // 자동 매니저 권한이 있는 사용자
        const val MANAGERLIST = 1 shl 7      // 매니저 목록에 포함된 사용자
        const val MANAGER = 1 shl 8          // 매니저 권한을 가진 사용자
        const val FEMALE = 1 shl 9           // 여성 사용자
        const val AUTODUMB = 1 shl 10        // 자동 채팅 금지된 사용자
        const val DUMB_BLIND = 1 shl 11      // 블라인드 채팅 금지된 사용자
        const val DOBAE_BLIND = 1 shl 12     // 도배 방지 플래그
        const val EXITUSER = 1 shl 13        // 퇴장한 사용자
        const val MOBILE = 1 shl 14          // 모바일 접속 사용자
        const val TOPFAN = 1 shl 15          // 탑팬
        const val REALNAME = 1 shl 16        // 실명 인증된 사용자
        const val NODIRECT = 1 shl 17        // 귓속말 수신 거부 설정된 사용자
        const val GLOBAL_APP = 1 shl 18      // 글로벌 앱 사용자
        const val QUICKVIEW = 1 shl 19       // 퀵뷰 사용자
        const val SPTR_STICKER = 1 shl 20    // 후원 스티커 사용자
        const val CHROMECAST = 1 shl 21      // 크롬캐스트 사용
        // 번호 22는 사용되지 않음
        const val MOBILE_WEB = 1 shl 23      // 모바일 웹 접속 사용자
        const val DOBAE_BLIND2 = 1 shl 24    // 도배 방지 플래그 2
        // 번호 25~27은 사용되지 않음
        const val FOLLOWER = 1 shl 28        // 팔로워
        // 번호 29은 사용되지 않음
        const val NOTIVODBALLOON = 1 shl 30  // VOD 풍선 알림 설정 사용자
        const val NOTITOPFAN = 1 shl 31      // 탑팬 알림 설정 사용자

        // where 2 플래그 (두 번째 플래그 그룹)
        const val GLOBAL_PC = 1 shl 0        // 글로벌 PC 접속 사용자
        const val CLAN = 1 shl 1             // 클랜 회원
        const val TOPCLAN = 1 shl 2          // 탑클랜 회원
        const val TOP20 = 1 shl 3            // 탑 20 랭킹 사용자
        const val GAMEGOD = 1 shl 4          // 게임 갓 등급 사용자
        const val ATAG_ALLOW = 1 shl 5       // A태그 허용 사용자
        const val NOSUPERCHAT = 1 shl 6      // 슈퍼챗 수신 거부 설정 사용자
        const val NORECVCHAT = 1 shl 7       // 채팅 수신 거부 설정 사용자
        const val FLASH = 1 shl 8            // 플래시 사용
        const val LGGAME = 1 shl 9           // LG 게임 사용자
        const val EMPLOYEE = 1 shl 10        // 직원 계정
        const val CLEANATI = 1 shl 11        // 클린아티 활동 사용자
        const val POLICE = 1 shl 12          // 경찰 계정
        const val ADMINCHAT = 1 shl 13       // 관리자 채팅 권한 사용자
        const val PC = 1 shl 14              // PC 접속 사용자
        const val SPECIFY = 1 shl 15         // 특정 사용자
    }

    // 이벤트 타입 정의: 채팅 SDK에서 발생하는 이벤트 타입을 정의
    enum class EventType {
        DEBUGGING_MESSAGE,  // 디버깅 메시지 이벤트
        MESSAGE,            // 일반 메시지 이벤트
        READY,              // 준비 완료 이벤트
        CLOSED,             // 연결 종료 이벤트
        ERROR               // 에러 발생 이벤트
    }

    // 기타 상수 정의
    const val CHAT_SERVER_HOST = "wss://chat-%s.afreecatv.com:%d/Websocket/%s" // 채팅 서버 주소 포맷
    const val API_SERVER_HOST = "https://openapi.afreecatv.com"                // API 서버 주소
    const val LIVE_SERVER_HOST = "https://live.afreecatv.com"                  // 라이브 서버 주소

    // 채팅 메시지 타입 정의: 채팅 메시지의 종류를 나타냄
    enum class ChatMessageType(val type: Int) {
        NORMAL(0),          // 일반 메시지
        NOTICE(1),          // 공지 메시지
        WARNING(2),         // 경고 메시지
        TRANSLATION(3)      // 번역 메시지
    }

    // 플랫폼 타입 정의: 접속한 플랫폼 종류를 나타냄
    enum class PlatformType(val value: String) {
        DESKTOP("DESKTOP"), // 데스크톱 플랫폼
        MOBILE("MOBILE")    // 모바일 플랫폼
    }

    // 채팅 얼림 모드 플래그 정의: 채팅 얼림 모드에서 허용할 사용자 유형을 나타냄
    object FreezeFlag {
        const val NONE = 0                   // 누구에게도 허용하지 않음
        const val BJ = 1 shl 4               // BJ에게만 허용
        const val FAN = 1 shl 5              // 팬클럽 회원에게 허용
        const val SUPPORTER = 1 shl 6        // 후원자에게 허용
        const val TOP_FAN = 1 shl 7          // 탑팬에게 허용
        const val FOLLOWER = 1 shl 8         // 팔로워에게 허용
        const val MANAGER = 1 shl 9          // 매니저에게 허용
    }
}