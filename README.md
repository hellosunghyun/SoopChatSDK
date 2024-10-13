# SoopChatSDK

SoopChatSDK는 아프리카TV의 채팅 기능을 쉽게 통합할 수 있는 Kotlin 기반의 SDK입니다.

## 시작하기

이 지침을 따라 로컬 머신에서 SoopChatSDK를 설정하고 사용할 수 있습니다.

### 전제 조건

프로젝트 실행에 필요한 소프트웨어:

- **Java 11** 이상
- **Gradle 6.0** 이상
- **Kotlin 1.5** 이상

### 설치

1. 리포지토리를 클론합니다:
   ```
   git clone https://github.com/your-username/SoopChatSDK.git
   ```

2. 프로젝트 디렉토리로 이동합니다:
   ```
   cd SoopChatSDK
   ```

3. Gradle을 사용하여 프로젝트를 빌드합니다:
   ```
   ./gradlew build
   ```

## 사용 방법

SoopChatSDK를 사용하여 아프리카TV 채팅 기능을 애플리케이션에 통합하는 기본적인 단계:

1. SDK 초기화:
   ```kotlin
   val sdk = SoopChatSDK(clientId, clientSecret)
   ```

2. 인증:
   ```kotlin
   sdk.openAuth(redirectUri)
   ```

3. 액세스 토큰 설정:
   ```kotlin
   sdk.setAuthToken(accessToken)
   ```

4. 채팅 연결:
   ```kotlin
   sdk.connect()
   ```

5. ��시지 전송:
   ```kotlin
   sdk.sendMessage("안녕하세요!")
   ```

6. 연결 종료:
   ```kotlin
   sdk.disconnect()
   ```

자세한 사용 방법은 [문서](링크)를 참조하세요.

## 기능

- OAuth 2.0 인증
- 실시간 채팅 연결
- 메시지 송수신
- 사용자 정보 관리
- 이벤트 핸들링

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 기여하기

프로젝트에 기여하고 싶으시다면 [CONTRIBUTING.md](CONTRIBUTING.md)를 참조해 주세요.

## 연락처

질문이나 피드백이 있으시면 [이메일 주소]로 연락 주세요.

## 사용 방법

이 라이브러리를 사용하려면 다음 의존성을 build.gradle 파일에 추가하세요:
