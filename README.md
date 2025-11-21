# 경산시 관광 웹 서비스 프로젝트

## 프로젝트 개요
이 프로젝트는 경산시 관광 정보를 제공하고, 사용자가 자신만의 여행 코스를 계획하고 관리할 수 있도록 돕는 웹 서비스입니다. SW대회 참가용으로 진행 중이며, 사용자에게 맞춤형 여행 경험을 제공하는 것을 목표로 합니다.

## 주요 기능
*   **사용자 관리:** 다단계 회원가입 프로세스를 통해 사용자를 등록하고, 로그인 기능을 제공합니다.
*   **여행 코스 생성:** 출발지, 여행 기간, 선호 장소 등을 단계별로 입력하여 맞춤형 여행 코스를 생성할 수 있습니다.
*   **장소 추천:** 사용자의 취향과 선택에 기반하여 새로운 장소를 추천합니다.
*   **코스 관리:** 생성된 여행 코스를 조회, 상세 확인, 삭제할 수 있습니다.
*   **장소 '좋아요' 기능:** 관심 있는 장소에 '좋아요'를 표시하고, 이를 코스 생성 시 활용할 수 있습니다.
*   **월간 매거진:** 큐레이션된 여행 콘텐츠를 제공합니다.
*   **신고 기능:** 부적절한 콘텐츠에 대한 신고 기능을 제공합니다.
*   **관광지 검색 및 필터링**: 카테고리별 관광지 조회
*   **관광지 상세 정보 확인**: 사진, 설명, 위치 등
*   **관리자 기능**: 관광지 등록/수정, 사용자 관리, 통계 확인 (예정)

## 기술 스택
*   **백엔드:** Java 17, Spring Boot (3.5.5), Spring Data JPA, Spring Security, QueryDSL
*   **프론트엔드:** HTML, CSS, JavaScript, Thymeleaf
*   **데이터베이스:** MySQL
*   **빌드 도구:** Gradle
*   **버전 관리:** Git & GitHub

## 아키텍처
프로젝트는 표준 Spring Boot 계층형 아키텍처를 따릅니다.
*   `controller`: 웹 요청 처리 및 UI 상호작용
*   `service`: 핵심 비즈니스 로직
*   `repository`: 데이터베이스 연동 (Spring Data JPA)
*   `domain`: 핵심 데이터 모델 (JPA 엔티티)
*   `dto`: 계층 간 데이터 전송 객체

## 팀 구성
| 역할 | 이름 | 담당 |
|------|------|------|
| 개발 | 조웅 | 웹 프론트엔드 구현, Git repository 관리 |
| 개발 | 서상범 | 백엔드 기능 개발 |
| 개발 | 이택민 | DB 연동 |

> 디자인 및 기획 관련 기능은 시각디자인과, 경영학과 팀원이 담당합니다.

## 실행 방법

### 1. 필수 설치 항목
*   Java 17
*   Gradle
*   MySQL 8.0 이상

### 2. 데이터베이스 설정
`src/main/resources/application.properties` 파일을 열어 MySQL 데이터베이스 연결 정보를 설정합니다.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update # 애플리케이션 실행 시 자동으로 스키마 업데이트
```
`your_username`과 `your_password`를 실제 MySQL 사용자 이름과 비밀번호로 변경하고, `testdb` 데이터베이스가 생성되어 있는지 확인합니다.

### 3. 프로젝트 빌드
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 프로젝트를 빌드합니다.
```bash
./gradlew build
```

### 4. 애플리케이션 실행
빌드가 완료되면 다음 명령어로 애플리케이션을 실행할 수 있습니다.
```bash
java -jar build/libs/swproject-0.0.1-SNAPSHOT.jar
```
또는 Gradle을 사용하여 직접 실행할 수도 있습니다.
```bash
./gradlew bootRun
```

### 5. 접속
애플리케이션이 성공적으로 실행되면 웹 브라우저에서 `http://localhost:8080`으로 접속할 수 있습니다.
