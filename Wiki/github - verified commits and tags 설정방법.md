# github - verified commits and tags 설정방법

## Commits
1. GitHub에 GPG key 등록 - 참고: [Managing commit signature verification](https://docs.github.com/en/github/authenticating-to-github/generating-a-new-gpg-key)

2. git에 GPG key 등록 - 참고: [Telling Git about your GPG key](https://docs.github.com/en/github/authenticating-to-github/telling-git-about-your-signing-key#telling-git-about-your-gpg-key-1)

3. local repository signed commit default로 설정
    - `git config commit.gpgsign true`
    - 만약 설정을 안할 경우, `git commit -S -m your commit message`와 같이 `S`옵션을 줘야함
    - 전역 설정: `git config --global commit.gpgsign true`

## Tags
참고: [Signing tags](https://docs.github.com/en/github/authenticating-to-github/signing-tags)

1. 설정은 commit과 동일
2. tag생성시, `git tag -s mytag` 명령어 사용