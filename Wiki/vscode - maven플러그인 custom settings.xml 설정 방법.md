# vscode에서 maven플러그인 custom settings.xml 설정 방법

> [vscode-maven 레포지토리](https://github.com/microsoft/vscode-maven)의 [#140](https://github.com/microsoft/vscode-maven/issues/140) 이슈를 참고하였습니다.

1. 프로젝트 루트폴더에 vscode 설정 폴더인 `.vscode`폴더를 생성.
2. `settings.json` 생성
3. `"maven.executable.options":"-s {실제경로}"` 입력.