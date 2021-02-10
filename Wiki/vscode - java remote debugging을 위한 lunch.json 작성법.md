# vscode - java remote debugging을 위한 lunch.json 작성법

아래와 같이 작성한다.

```json
"configurations": [
    {
        "type": "java",
        "name": "Debug (Attach)", // 디버깅 설정 이름
        "projectName": "{프로젝트 이름}",
        "request": "attach", // attach로 해야 remote로 연결
        "hostName": "localhost", // 호스트 이름
        "port": 8080 // 포트 번호
    }
]
```
