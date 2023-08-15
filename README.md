# wanted-pre-onboarding-backend
## 지원자의 성명: 김관욱
## 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
  1. git clone
  2. database mysql 설치
  3. 데이터 베이스 명 wanted, 유저 root, 비밀번호 1234
  4. 어플리케이션 빌드 후 실행
  5. 엔드포인트 호출 방법
     - http://15.165.27.4:8080/member
     - http://15.165.27.4:8080/member/login
     - http://15.165.27.4:8080/twit
     - http://15.165.27.4:8080/twit?page=0&size=10
     - http://15.165.27.4:8080/twit/1
## 데이터베이스 테이블 구조
  ![](./readme_asset/wanted_erd.png)
## 구현한 API의 동작을 촬영한 데모 영상 링크
## 구현 방법 및 이유에 대한 간략한 설명
  - Spring Data Jpa 활용한 Entity 중심의 개발
  - ControllerAdvice 활용한 예외처
  - ArgumentResolver 사용해서 JWT 회원 인증 구축
  - 낙관적 락을 사용한 게시글 업데이트 동시성 문제 해결
  - AOP를 활용하여 업데이트 재시도 로직의 관심사 분리
  - 헥사고날 아키텍처 적용
## API 명세(request/response 포함)
- http://15.165.27.4:8080/docs/apidocs.html 
  ![](./readme_asset/_Users_gwanuk_Directory_wanted-pre-onboarding-backend_src_docs_asciidoc_apidocs.html.png)
