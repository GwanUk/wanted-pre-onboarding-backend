# wanted-pre-onboarding-backend
## 지원자의 성명: 김관욱
## docker compose를 이용하여 애플리케이션 환경을 구성한 경우 (README.md 파일에 docker-compose 실행 방법 반드시 기입)
1. git clone https://github.com/GwanUk/wanted-pre-onboarding-backend.git
2. cd wanted-pre-onboarding-backend/
3. ./gradlew clean build
4. docker-compose up
```
Dockerfile

FROM openjdk:17-jdk-slim
ADD ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```
```
docker-compose.yml

version: "3"

services:
  database:
    image: mysql
    container_name: database-mysql
    environment:
      - MYSQL_DATABASE=wanted
      - MYSQL_ROOT_HOST=%
      - MYSQL_ROOT_PASSWORD=1234
    command: ['--character-set-server=utf8mb4', '--collation-server=utf8mb4_unicode_ci']

  application:
    build: .
    restart: always
    ports:
      - 8080:8080
    depends_on:
      - database
    image: web-server-img
    container_name: web-server
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://database:3306/wanted?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 1234
```

## 클라우드 환경(AWS, GCP)에 배포 환경을 설계하고 애플리케이션을 배포한 경우 (README.md 파일에 배포된 API 주소와 설계한 AWS 환경 그림으로 첨부)
- http://15.165.27.4:8080/docs/apidocs.html
  ![](./readme_asset/architecture.png)
## 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
  1. git clone https://github.com/GwanUk/wanted-pre-onboarding-backend.git
  2. database mysql 설치
  3. 어플리케이션 빌드 후 실행
- 엔드포인트 호출 방법 
  - http://15.165.27.4:8080/member
  - http://15.165.27.4:8080/member/login
  - http://15.165.27.4:8080/twit
  - http://15.165.27.4:8080/twit?page=0&size=10
  - http://15.165.27.4:8080/twit/1
## 데이터베이스 테이블 구조
  ![](./readme_asset/wanted_erd.png)
## 구현한 API의 동작을 촬영한 데모 영상 링크
  - https://drive.google.com/file/d/1E1stIqQu0xzh4EDyIDRIOAR53B5hXVUW/view?usp=sharing
## 구현 방법 및 이유에 대한 간략한 설명
  - Entity 중심의 개발을 위한 Spring Data Jpa 사용 
  - 예외처리 관심사 분리를 위한 ControllerAdvice 사용 
  - 회원 인증을 위하여 JWT 및 ArgumentResolver 사용
    - jwt expiration 10분
  - 게시글 업데이트 동시성 문제 해결을 위해 낙관적 락 사용
  - 게시글 업데이트 재시도 로직의 관심사 분리를 위해 AOP 사용
  - 헥사고날 아키텍처 적용
## API 명세(request/response 포함)
- http://15.165.27.4:8080/docs/apidocs.html 
  ![](./readme_asset/_Users_gwanuk_Directory_wanted-pre-onboarding-backend_src_docs_asciidoc_apidocs.html.png)
