# java, spring - interface와 inner class를 통한 DTO 클래스 관리

**문제점** - 기존에 DTO 클래스를 요청별, 응답별로 개별로 만들어 관리하였다. 이에 따라, 클래스의 갯수가 많아지고, 중복도 생기고, 쪼오끔 관리하기 귀찮아짐.

**해결 방법**  
아이디어는 다음 글에서 힌트를 얻었다  
[p4rksh - Spring Boot에서 깔끔하게 DTO 관리하기](https://velog.io/@p4rksh/Spring-Boot%EC%97%90%EC%84%9C-%EA%B9%94%EB%81%94%ED%95%98%EA%B2%8C-DTO-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0)

- **Inner Class를 통한 DTO 관리**  
  기존에 여러 파일로 분산되어 있는 DTO들을 하나의 Class 파일 내부에서 관리할 수 있는 장점이 생김.

      기존 방법

      ```java
      // Entity
      @Getter
      public class Data {
          private UUID id;
          private String name;
          private String value;
      }

      // DTO
      public class DataDTO {
          @Getter
          @NoArgsConstructor
          public static class Request {
              private String name;
              private String value;
          }

          @Getter
          @Builder
          public static class Response {
              private UUID id;
              private String name;
              private String value;
          }
      }
      ```

여기까지가 앞서 언급한 글에서 말하는 방식이다. 괜찮은 아이디어였다. 이 방식을 통하면, 이제 여러 파일이 아닌, 하나의 파일 내에서 내가 원하는 DTO 클래스를 마음대로 생성할 수 있게 된다.

기존에 나는 `of` 메서드를 통해 entity를 받아 dto를 생성하고 있었는데, 위 방식에서 어떻게 이를 적용할지 고민하여 다음과 같이 코드를 짜게 되었다.

1. 외부 클래스는 class가 아닌 interface로 선언한다 - 이렇게 하면 단순히 내부 클래스를 들고 있기 위한 외부 클래스의 생성을 막을 수 있다. 또한 다형성을 이용하여 모두 같은 타입으로 리턴이 가능하다.
2. java 8부터는 interface에도 static 메서드가 선언이 가능하다. 즉 static 메서드를 통한 팩토리 메서드를 만들면 될 것으로 생각되었다.
3. 팩토리 메서드에서 각 상황별 DTO를 어떻게 생성할 것인가? -> 이에 대해서는 좀 더 고민해봐야겠지만, 현재는 내부 enum을 사용하도록 하였다.
4. DTO 생성은 빌더 패턴을 이용한다. -> 이렇게하면 내부 속성값이 지워질때 컴파일시점에 확인이 가능하고, 또한 파라미터의 순서와는 상관없어서, 실수를 방지할 수 있다.

작성된 코드 예시는 다음과 같다.

```java
// Entity
@Getter
public class Data {
    private UUID id;
    private String name;
    private String value;
}

// DTO
public interface DataDTO {

    enum Type {
        RESPONSE1, RESPONSE2, RESPONSE3;
    }

    public static DataDTO of(Data data, Type type) {
        DataDTO result = null;

        switch (type) {
            case RESPONSE1:
                result = Response1.builder().id(data.getId()).name(data.getName()).value(data.getValue()).build();
                break;
            case RESPONSE2:
                result = Response2.builder().id(data.getId()).name(data.getName()).build();
                break;
            case RESPONSE3:
                result = Response3.builder().name(data.getName()).value().build();
                break;
            default:
                result = Default.builder().id(data.getId()).name(data.getName()).value(data.getValue()).build();
                break;
        }

        return result;
    }

    @Getter
    @Builder
    class Default implements DataDTO {
        private final UUID id;
        private final String name;
        private final String value;
    }

    @Getter
    @NoArgsConstructor
    class Request implements DataDTO {
        private String name;
        private String value;
    }

    @Getter
    @Builder
    class Response1 implements DataDTO {
        private final UUID id;
        private final String name;
        private final String value;
    }

    @Getter
    @Builder
    class Response2 implements DataDTO {
        private final UUID id;
        private final String name;
    }

    @Getter
    @Builder
    class Response3 implements DataDTO {
        private final String name;
        private final String value;
    }
}

// RestController
@RequiredArgsConstructor
@RequestMapping("/datas")
@RestController
public class Controller {

    private final DataService dataService;

    @GetMapping
    public ResponseEntity<List<DataDTO>> getMethod() {
        List<Data> datas = this.dataService.getAllDatas();
        List<DataDTO> dtos = datas.stream().map(d -> DataDTO.of(d, DataDTO.Type.RESPONSE2)).collect(Collectors.toList());

        return ResponseEntity.ok().body(dtos);
    }
}
```
