# Docker를 이용하여 Nexus 2를 설치하는 방법
> 설치는 [Sonatype - Nexus Docker](https://hub.docker.com/r/sonatype/nexus)를 참고하였습니다.

## 설치
1. Volumn을 마운트하기 위한 datas 라는 디렉토리를 생성한다.
2. 다음의 명령어를 작성한 `docker-compose.yml` 파일을 만들고 이를 `docker-compose -d up` 으로 실행한다.
    ```yml
    version: '3.7'
    services:
    nexus: # 서비스 이름
        container_name: nexus # 컨테이너 이름
        image: sonatype/nexus:oss # 이미지 이름 - tag: oss
        volumes: 
        - ./datas:/sonatype-work # datas 폴더에 마운트
        ports:
        - 8081:8081 # 8081 포트로 연결 
        networks:
        - nexus-network # nexus-network에 연결

    networks:
    nexus-network: # nexus-network 생성
    ```

3. 정상적으로 실행되면 `http://localhost:8081/nexus`로 접속하여 확인한다. 
4. default User(ID: admin, password: admin123)로 접속한다.
5. 이후 원하는 레포지토리를 추가하거나 변경하고, `Public Repositories`의 `Configuration` 항목에서 사용할 레포지토리를 추가한다.

## Maven 연동방법
> 연동 방법은 [sonatype - Nexus 2 Document](https://help.sonatype.com/repomanager2/maven-and-other-build-tools/apache-maven)를 참고했습니다.

앞서 설치한 Nexus를 `{사용자폴더}/.m2/settings.xml`에서 설정하여 Maven에 연동할 수 있다.

1. `settings.xml`에 다음의 코드 추가한다.
    ```xml
    <settings>
        <mirrors>
            <mirror>
                <!--This sends everything else to /public -->
                <id>nexus</id>
                <mirrorOf>*</mirrorOf>
                <url>http://localhost:8081/nexus/content/groups/public</url>
            </mirror>
        </mirrors>
        <profiles>
            <profile>
                <id>nexus</id>
                <!--Enable snapshots for the built in central repo to direct -->
                <!--all requests to nexus via the mirror -->
                <repositories>
                    <repository>
                        <id>central</id>
                        <url>http://central</url>
                        <releases><enabled>true</enabled></releases>
                        <snapshots><enabled>true</enabled></snapshots>
                    </repository>
                </repositories>
                <pluginRepositories>
                    <pluginRepository>
                        <id>central</id>
                        <url>http://central</url>
                        <releases><enabled>true</enabled></releases>
                        <snapshots><enabled>true</enabled></snapshots>
                    </pluginRepository>
                </pluginRepositories>
            </profile>
        </profiles>
        <activeProfiles>
            <!--make the profile active all the time -->
            <activeProfile>nexus</activeProfile>
        </activeProfiles>
    </settings>
    ```