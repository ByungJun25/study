# Spring MVC에서의 model 검증 및 restful에서의 responsebody 검증

## Spring MVC에서의 model 검증 (form 기반)

1. 모델 정의  
    Spring의 자동 맵핑을 이용하기 위해선, 모델 객체에 Setter가 존재해야함.
    
    ```java
    @Setter
    @Getter
    public class ExModelDTO() {
        // Fields...
    }
    ```

1. spring-validation 의존성을 추가하고, 앞서 선언한 모델의 검증할 필드에 어노테이션 선언.
1. Controller에서 `@Validated`를 통해 자동 검증이 이루어지게 하고, `BindingResult`를 통해 후처리가 가능하다.

```java
@Setter
@Getter
public class ExModelDTO() {
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String value;

}

@Controller
public class Controller {

    @PostMapping("/test")
    public String method(@Validated ExModelDTO dto, BindingResult result) {
        if(result.hasErrors()) {
            // 에러 존재시 후처리.
        }
    } 
}
```

만약 annotation기반이 아닌 BindingResult를 통해 검증을 하기 위해선, `Validator` 구현한 클래스를 선언해야한다.
예시 - [StudyServer - Spring 데이터 검증 @Valid, BindingResult](https://parkwonhui.github.io/spring/2019/04/22/spring-valid-bindingresult.html)

## RestfulAPI에서의 Responsebody 검증 (Ajax)
1. ResponseBody를 위한 모델에는 Setter가 필요없다.
1. 기본적으로 검증 과정은 앞서 설명한 어노테이션 기반 검증과 동일하다.
1. 검증에 따른 후처리는 `@ExceptionHandler`를 통해 할 수 있다. Global하게 처리를 하기위해선 `@RestControllerAdvice`를 이용한다.

```java
@Setter
@Getter
public class ExModelDTO() {
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String value;

}

@RestController
public class Controller {

    @ExceptionHandler(Exception.class) // 예외는 각 예외에 맞게 선언할 것.
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail");
    }

    @PostMapping("/test")
    public ResponseEntity<String> method(@ResponseBody @Validated ExModelDTO dto) {
    } 
}
```