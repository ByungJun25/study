# github action - GitHub package 및 Maven repository 배포하기

## 참고 링크
- [Package client guides for GitHub Packages](https://docs.github.com/en/packages/guides/package-client-guides-for-github-packages)
- [Publishing using Apache Maven](https://github.com/actions/setup-java#publishing-using-apache-maven)
- [Publishing packages to the Maven Central Repository](https://docs.github.com/en/actions/guides/publishing-java-packages-with-maven)
- [OSSRH Guide](https://central.sonatype.org/pages/ossrh-guide.html#deployment)
- [Deploy - Apache Maven](https://central.sonatype.org/pages/apache-maven.html)


## 방법

1. GitHub package에 배포하기
    1. Maven을 통해 배포하기

        1. Personal access token 발급
            - settings -> developer settings -> personal access token 
            - Generate new token 클릭
            - write:packages 권한 선택 후(자동으로 repository 권한 및 packages 읽기 권한 선택됨), 토큰 발급
        
        1. `settings.xml` 설정
            ```xml
            <repository>
                <id>github</id>
                <name>GitHub OWNER Apache Maven Packages</name>
                <url>https://maven.pkg.github.com/OWNER/REPOSITORY</url> <!-- OWNER: GitHub 이름(소문자로만) / REPOSITORY: 레포지토리 이름(소문자로만) -->
            </repository>

            <server>
                <id>github</id>
                <username>USERNAME</username> <!-- GitHub 이름 -->
                <password>TOKEN</password> <!-- 앞서 발급한 personal access token -->
            </server>
            ```
        1. `pom.xml` 설정
            ```xml
            <distributionManagement>
                <repository>
                    <id>github</id>
                    <name>GitHub OWNER Apache Maven Packages</name>

                    <url>https://maven.pkg.github.com/OWNER/REPOSITORY</url> <!-- OWNER: GitHub 이름(소문자로만) / REPOSITORY: 레포지토리 이름(소문자로만) -->
                </repository>
            </distributionManagement>
            ```

        1. `mvn deploy` 명령어로 배포

    1. GitHub Actions을 통해 배포하기
        1. `workflows yaml` 파일 설정
            ```yml
            jobs:
              deploy:
                name: deploy GitHub Maven Package
                runs-on: ubuntu-latest
            
                steps:
                  - uses: actions/checkout@v2
                  - name: Set up JDK 1.8
                    uses: actions/setup-java@v1
                    with:
                      java-version: 1.8
            
                  - name: Publish to GitHub Packages Apache Maven
                    run: mvn --batch-mode deploy -PRC
                    env:
                      GITHUB_TOKEN: ${{ github.token }} ## 이렇게 작성시 자동으로 token을 생성하여 사용함
            ```

1. Github Actions를 통해 Maven central repository에 배포하기
    1. Jira 가입 및 티켓 생성
        1. [https://issues.sonatype.org/](https://issues.sonatype.org/)로 가서 jira 계정 생성.
        1. 새 프로젝트 티켓을 생성한다.
        1. 이후 안내를 따라, 생성된 티켓의 상태가 `resolved`가 되도록 할 것.
    
    2. `pom.xml`에 내용 작성 - `{}`의 내용은 각자가 원하는대로 하면 됨.
        1. `groupId`, `artifactId`, `version` 명시
            
            ```xml
            <groupId>com.github.{name}</groupId> <!-- 개인인 경우 도메인이 없기때문에, github를 이용해야한다. -->
            <artifactId>{project name}</artifactId>
            <version>{version}</version>
            ```

        1. `name`, `description`, `url`, `inceptionYear` 명시
            
            ```xml
            <name>{name}</name>
            <description>{description}</description>
            <url>{github repository address}</url> <!-- 개인인 경우, github를 이용. -->
            <inceptionYear>2021</inceptionYear>
            ```

        1. `licenses` 명시
            : 원하는 라이센스로 아래와 같이 명시하면 된다.                

            ```xml
            <licenses>
                <license>
                    <name>Apache License, Version 2.0</name>
                    <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
                    <distribution>repo</distribution>
                    <comments>A business-friendly OSS license</comments>
                </license>
            </licenses>
            ```

        1. `developers` 명시
            
            ```xml
            <developers>
                <developer>
                    <id>{id}</id>
                    <name>{name}</name>
                    <url>{github address}</url>
                    <timezone>{timezone}</timezone>
                    <roles>
                        <role>{role}</role>
                    </roles>
                </developer>
            </developers>
            ```

        1. `scm` 명시
            
            ```xml
            <scm>
                <url>{github repository address}</url>
            </scm>
            ```

        1. (optional) `issueManagement`, `ciManagement` 명시
    
    3. `plugins` 설정
        1. `nexus-staging-maven-plugin`  
            : maven에 배포후 자동으로 close 및 release를 할 수 있도록 설정할 수 있는 plugin
            
            ```xml
            <plugin>
                <groupId>org.sonatype.plugins</groupId>
                <artifactId>nexus-staging-maven-plugin</artifactId>
                <version>1.6.7</version>
                <extensions>true</extensions>
                <configuration>
                    <serverId>ossrh</serverId>
                    <nexusUrl>https://oss.sonatype.org/</nexusUrl>
                    <autoReleaseAfterClose>false</autoReleaseAfterClose>
                </configuration>
            </plugin>
            ```

        2. `maven-source-plugin`  
            : maven에 배포시, source 정보가 담긴 jar 파일을 함께 제공해야하는데, 이를 만들어주는 plugin
            
            ```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ```

        1. `maven-javadoc-plugin`  
            : maven에 배포시, javadoc 정보가 담긴 jar 파일을 함께 제공해야하는데, 이를 만들어주는 plugin
            
            ```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ```

        1. `maven-gpg-plugin`  
            : maven에 배포시, 배포할 jar 파일에 서명해야 하는데, 이를 도와주는 plugin
            
            ```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.6</version>
                <executions>
                    <execution>
                        <id>sign-artifacts</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <!-- Prevent gpg from using pinentry programs -->
                    <gpgArguments>
                        <arg>--pinentry-mode</arg>
                        <arg>loopback</arg>
                    </gpgArguments>
                </configuration>
            </plugin>
            ```

    4. `distributionManagement` 설정
        ```xml
        <distributionManagement>
            <snapshotRepository>
                <id>ossrh</id>
                <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            </snapshotRepository>
            <repository>
                <id>ossrh</id>
                <name>Central Repository OSSRH</name>
                <url>https://oss.sonatype.org/service/local/staging/deploy/maven2/</url>
            </repository>
        </distributionManagement>
        ```
            
    5. GitHub Actions 설정
        ```yml
        build-deploy:
          name: deploy GitHub Maven Package
          runs-on: ubuntu-latest

          steps:
              - name: code check out
                uses: actions/checkout@v2

              - name: Set up Java for publishing to Maven Central Repository
                uses: actions/setup-java@v1
                with:
                    java-version: 1.8
                    server-id: ossrh
                    server-username: MAVEN_USERNAME
                    server-password: MAVEN_PASSWORD
                    gpg-private-key: ${{ secrets.MAVEN_GPG_PRIVATE_KEY }} # 생성한 private key의 pgp값을 secret에 저장하고 이를 사용
                    gpg-passphrase: MAVEN_GPG_PASSPHRASE 
      
              - name: Publish to the Maven Central Repository
                run: mvn --batch-mode deploy
                env:
                    MAVEN_USERNAME: ${{ secrets.OSSRH_USERNAME }} # jira 계정 or access token -> maven의 nexus사이트에서 발급 가능.
                    MAVEN_PASSWORD: ${{ secrets.OSSRH_TOKEN }} # jira 비밀번호 or access token
                    MAVEN_GPG_PASSPHRASE: ${{ secrets.MAVEN_GPG_PASSPHRASE }} # gpg 키 생성시 입력한 비밀번호
        ```

    6. GitHub Action 실행
    
    1. maven의 nexus에 접속하여 staging repository에 올라와 있는지 확인 후, 발급한 티켓으로 가서 comment로 sync 요청.

    7. maven의 nexus에 접속하여 staging repository에 있는 데이터를 close & release 할 것 (앞서 nexus-staging-maven-plugin으로 자동화 가능)  
        : close 및 release 시 maven 측에서 자동으로 필요 요구사항을 확인함. 불충분할시 배포 불가능. `drop`기능을 이용하여 잘못된 업로드 삭제 가능.

