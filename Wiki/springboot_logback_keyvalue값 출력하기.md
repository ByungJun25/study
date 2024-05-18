# 구성 환경

Spring boot 3.2.5

# yaml 설정

기본 설정(defaults.xml)에 `%kvp`가 정의되어 있지 않아, logback에서 slf4j의 fluent API를 활용한 KeyValue 값을 출력할 수 없는 문제가 있음. 따라서 아래 코드와 같이 `%kvp` 키워드를 추가한 패턴을 정의해주면 해당 문제를 해결가능함. 

```yaml
logging:
  pattern:
    ## Pattern is copied from the default values of the Spring Boot: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/logback/defaults.xml
    ## Add %replace([%kvp]){'\\[\\]', ''} to the pattern to print the key-value pairs in the log message. The key-value pairs are printed in the format of [key1=value1, key2=value2, ...]
    ## If kvp is empty, the pattern will be replaced with an empty string.
    console: "${CONSOLE_LOG_PATTERN:%clr(%d{${LOG_DATEFORMAT_PATTERN:yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} %clr(${LOG_LEVEL_PATTERN:%5p}) %clr(${PID:}){magenta} %clr(---){faint} %clr(%applicationName[%15.15t]){faint} %clr(${LOG_CORRELATION_PATTERN:}){faint}%clr(%-40.40logger{39}){cyan} %clr(:){faint} %m %replace([%kvp]){'\\[\\]', ''}%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}}"
```

위 코드에는 `%replace([%kvp]){'\\[\\]', ''}`를 추가하였는데 이는 `kvp`가 없는 값일 경우 `[]`도 출력 안하기 위해 빈 값으로 치환하는 코드가 같이 있는것임.


#### multiline version

```yaml
logging:
  pattern:
    console: "${CONSOLE_LOG_PATTERN:%clr(%d{${LOG_DATEFORMAT_PATTERN:yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} \
    %clr(${LOG_LEVEL_PATTERN:%5p}) \
    %clr(${PID:}){magenta} \
    %clr(---){faint} %clr(%applicationName[%15.15t]){faint} \
    %clr(${LOG_CORRELATION_PATTERN:}){faint}%clr(%-40.40logger{39}){cyan} \
    %clr(:){faint} %m %replace([%kvp]){'\\[\\]', ''}%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}}"
```