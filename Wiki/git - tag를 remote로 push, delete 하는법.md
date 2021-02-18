# git - remote tag 생성, 푸쉬, 삭제 하는법

- 작성 시점 IDE 환경: vscode

## remote로 push 하는법
1. vscode의 git을 이용하여 tag 생성
2. `git push {remote_name} {tag_name}` 명령어 사용.
    1. 만약 branch가 upstream branch가 아니면 `git push --set-upstream {remote_name} {branch_name}` 명령어로 upstream으로 전환
3. 모든 tag 한번에 push -> `git push --follow-tags`

## remote tag를 삭제하는 방법
1. `git push --delete {remote_name} {tag_name}` 명령어 사용.