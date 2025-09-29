# 1단계: 빌드 환경 (Java 17과 Gradle이 설치된 이미지 사용)
FROM gradle:8.4-jdk17-focal AS build

# 소스 코드를 컨테이너 안으로 복사
WORKDIR /home/gradle/src
COPY . .

# Gradle 빌드 실행 (테스트는 생략하여 빌드 속도 향상)
RUN gradle build -x test --no-daemon

# 2단계: 실행 환경 (Java 17만 설치된 가벼운 이미지 사용)
FROM openjdk:17-jdk-slim

# 빌드 단계에서 생성된 .jar 파일을 실행 환경으로 복사
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app.jar"]
