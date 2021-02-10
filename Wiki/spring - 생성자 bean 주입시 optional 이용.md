# spring - 생성자 bean 주입시 optional 이용

문제: 보통 `private final`로 선언하고, `Lombok`의 `@RequiredArgsConstructor` 이용해서 생성자 주입을 많이 사용하는데, 주입할 빈이 생성되어 있지 않으면 에러가 발생한다. `@Autowired`의 `required = false`와 같이 없어도 실행되도록 하는 것은 어떻게 하는가?

해결: 주입 받을 대상을 `Optional`로 선언하면 된다. 이렇게 선언하면 빈이 생성되어 있지 않을 경우, Empty Optional로 존재하게 된다.