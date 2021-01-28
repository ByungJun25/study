# Maven local repository 추가 및 jar 추가 하는 방법

## `pom.xml`에 local repository 추가
- `file://` 은 로컬 파일 시스템을 의미함.
- `${project.basedir}`은 현재 `pom.xml`이 있는 위치를 의미함.

```xml
<repositories>
    <repository>
        <id>local-repository</id>
        <name>local repository</name>
        <url>file://${project.basedir}/lib</url> <!-- 현재 위치의 lib 폴더 -->
    </repository>
</repositories>
```

## local repository를 통한 jar 추가 방법

`pom.xml`을 통해 다음의 jar를 추가한다고 한다면,

```xml
<dependency>
    <groupId>edu.princeton.cs</groupId>
    <artifactId>algs4</artifactId>
    <version>1.0.4</version>
</dependency>
```

앞서 설정한 local repository 경로에 `/edu/princeton/cs/algs4/1.0.4` 폴더를 생성하고, algs4-1.0.4.jar 파일을 넣어둔다.

그 후 `mvn clean compile package -U`을 통해 업데이트 해준다.