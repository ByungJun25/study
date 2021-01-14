 # repo.spring.io을 통해 받는 3rd-party 앱이 더이상 익명 사용자에게는 유효하지 않음

> 링크: [Notice of Permissions Changes to repo.spring.io, Fall and Winter, 2020](https://spring.io/blog/2020/10/29/notice-of-permissions-changes-to-repo-spring-io-fall-and-winter-2020)

찾게된 원인: `nexus`를 통해 `dependency`를 로드하도록 했는데, 계속 `org.restlet.jee:org.restlet` 의 jar 파일이 로드되지 않는 문제가 발생함.

2020년 11월 12일부로 익명사용자는 `repo.spring.io`를 통해 `3rd party artifact`를 로드할 수 없게됨.

해결방법: 상단 링크의 `How to use repo.spring.io` 섹션 참고할 것.
> 임시 해결책으로 `jar` 파일을 수동으로 받은뒤, 업로드 해놓음.