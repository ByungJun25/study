# Spring boot cloud `bootstrap.yml`의 작동이 변경됨.

> 링크: [변경 사항](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2020.0-Release-Notes#breaking-changes)

- Bootstrap, provided by spring-cloud-commons, is no longer enabled by default. If your project requires it, it can be re-enabled by properties or by a new starter.
  - To re-enable by properties set `spring.cloud.bootstrap.enabled=true` or `spring.config.use-legacy-processing=true`. These need to be set as an environment variable, java system property or a command line argument.
  - The other option is to include the new `spring-cloud-starter-bootstrap`.

업데이트가 되면서 default로 비활성화가 되었다. 따라서 활성화를 해주던가 starter 의존성을 추가하자

- 의존성 추가 방법(maven)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```
