# spring이 제공하는 플러그인을 통해 docker image 만들기

1. Spring 프로젝트에 다음과 같이 dockerfile 생성

```dockerfile
# base image 선택 - openjdk 11 jre slim 버전
FROM openjdk:openjdk:11-jre-slim
# 호스트로 노출할 port
EXPOSE 9365
# dockerfile내에 사용될 변수 선언
ARG JAR_FILE=target/example.jar
# jar 파일 복사
ADD ${JAR_FILE} app.jar
# 실행 명령어 설정
ENTRYPOINT ["java","-jar","/app.jar"]
```

1. `mvn spring-boot:build-image` 명령어를 통해 이미지 생성 (docker 데몬이 실행중이어야 함)

## 도커 이미지를 파일로 저장하는 방법

1. `docker save -o example.tar example:tag` 명령어 사용