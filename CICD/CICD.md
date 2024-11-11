# CI/CD

### ✅CI/CD 기본 개념 및 Github Actions 정리

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* CI/CD란, Continuous Integration, Continuous Deployment라는 의미를 가진다.
* CI/CD는 테스트(Test), 통합(Merge), 배포(Deploy)의 과정을 자동화하는 걸 의미한다.
* 반복적인 과정을 자동화시키기 위해 CI/CD를 배우는 것이다.

[img.png](image/img.png)

#### Github Actions 개념

[img.png](image/img_2.png)

1. 코드 작성 후 commit
2. 깃허브에 push
3. push를 감지해서 Github Actions에 작성한 로직이 실행

    a. 빌드(Build)
    b. 테스트(Test)
    c. 배포(Deploy)

4. 서버에서 배포된 최신 코드를 기반으로 서버 재실행

#### Github Actions 기본 문법

[Github Actions 설명서](https://docs.github.com/ko/actions)

* Github Actions를 실행시키기 위해서는 반드시 `.github/workflows`라는 디렉터리에 `.yml` 또는 `.yaml` 확장자로 파일을 작성해야 한다.
* 그리고 `.github/workflows`는 프로젝트 폴더의 최상단에 만들어야 한다.

#### Github Actions 전체 구조

[img.png](image/img_3.png)

-----------------------
</details>

### ✅개인 프로젝트에서 CI/CD 구축하기

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

[img.png](image/img_4.png)

* `git pull`을 활용해서 변경된 부분의 프로젝트 코드에 대해서만 업데이트 하기 때문에 CI/CD 속도가 빠르다.
* 인프라 구조가 복잡하지 않고 간단하다.
* 하지만 빌드 작업은 EC2 인스턴스 내부에서 이루어지기 때문에 운영 서버 성능에 영향을 미칠 수 있다.
* Github 계정 정보가 EC2에 저장되기 때문에 신뢰성이 요구되는 사람과의 작업 혹은 개인 프로젝트에만 적용할 수 있다.

#### EC2에 들어가서 기본 환경 구성하기

* JDK 설치 

```shell
$ sudo apt update && /
sudo apt install openjdk-17-jdk -y

$ java -version # 잘 설치됐는 지 확인
```

* git clone을 활용해서 프로젝트 소스코드 다운받기

```shell
$ git clone {git repository clone 주소}
```

* EC2에서 clone 받은 서버가 잘 작동하는지 확인하기

```shell
$ cd {프로젝트 경로}
$ ./gradlew clean build
$ cd build/libs
$ nohup java -jar ________.jar &

$ sudo lsof -i:8080 # 8080번 포트에 Spring Boot가 실행되고 있는 지 확인
```

* 매번 Github 계정과 비밀번호를 입력하는 과정 없애기

```shell
$ git config --global credential.helper store
```

#### 스프링 백엔드의 경우 보안상 노출되면 위험한 정보들을 보관하는 application.yml 파일을 CI/CD에 포함시키기

* 결론부터 말하자면 Github의 Secret Variable을 통해 해결할 수 있다.

```yaml
name: Deploy To EC2

on:
  push:
    branches:
      - master

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: SSH로 EC2에 접속하기
        uses: appleboy/ssh-action@v1.0.3
        env:
          APPLICATION_PROPERTIES: ${{ secrets.APPLICATION_PROPERTIES }}
        with:
          host: ${{ secrets.EC2_HOST }}         # EC2의 주소(탄력적 IP를 연결하면 EC2를 재시작해도 주소의 변경이 일어나지 않는다.)
          username: ${{ secrets.EC2_USERNAME }} # EC2 접속 username(ubuntu)
          key: ${{ secrets.EC2_PRIVATE_KEY }}   # EC2의 Key 파일의 내부 텍스트(SSH 키 페어를 그대로 복붙하되 마지막에 %는 붙여넣지않는다.)
          envs: APPLICATION_PROPERTIES
          script_stop: true                     # 아래 script 중 실패하는 명령이 하나라도 있으면 실패로 처리
          script: | 
            cd /home/ubuntu/actions
            git pull origin master
            echo "$APPLICATION_PROPERTIES" > src/main/resources/application.yml     # application.yml 파일을 작성
            chmod +x ./gradlew                                                      # 실행 권한 부여
            ./gradlew clean build   
            sudo fuser -k -n tcp 8080 || true                                       # 8080번 포트로 실행되는 프로세스를 종료하되 없더라도 실패로 처리되지 않도록 true를 추가
            nohup java -jar build/libs/*SNAPSHOT.jar > ./output.log 2>&1 &          # 백그라운드로 실행하면서 실행 로그에 대한 결과를 output.log에 기록
```

-----------------------
</details>

### ✅일반 프로젝트에서 CI/CD 구축하기

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

[img.png](image/img_5.png)

* 빌드 작업을 Github Actions에서 처리하기 때문에 운영 서버 성능에 영향을 거의 주지 않는다.
* CI/CD 툴로 Github Actions만 사용하기 때문에 인프라 구조는 이전과 동일하다.
* 무중단 배포 혹은 여러 인스턴스에 배포를 해야 하는 상황이라면 직접 스크립트를 작성해야 한다.

```yaml
name: Deploy To EC2

on:
  push:
    branches:
      - master

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Github Repository 파일 불러오기
        uses: actions/checkout@v4

      - name: JDK 17 version 설치
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      - name: application.yml 파일 작성
        run: echo "$APPLICATION_PROPERTIES" > src/main/resources/application.yml

      - name: 실행 권한 부여
        run: chmod +x ./gradlew

      - name: 테스트 및 빌드하기
        run: ./gradlew clean build

      - name: 빌드된 파일 이름 변경하기
        run: mv ./build/libs/*SNAPSHOT.jar ./project.jar

      - name: SCP로 EC2에 빌드된 파일 전송하기
        uses: appleboy/scp-action@v0.1.7
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USERNAME }}
          key: ${{ secrets.EC2_PRIVATE_KEY }}
          source: project.jar
          target: /home/ubuntu/actions/tobe   # tobe 디렉터리 : 빌드된 파일을 전달받는 곳

      - name: SSH로 EC2에 접속하기
        uses: appleboy/ssh-action@v1.0.3
        env:
          APPLICATION_PROPERTIES: ${{ secrets.APPLICATION_PROPERTIES }}
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USERNAME }}
          key: ${{ secrets.EC2_PRIVATE_KEY }}
          envs: APPLICATION_PROPERTIES
          script_stop: true
          script: |
            rm -rf /home/ubuntu/actions/current                                                 # current 디렉터리 : 현재 실행되는 프로젝트 파일이 저장되는 곳
            mkdir /home/ubuntu/actions/current                                                  # 지운 다음 새로 만들기
            mv /home/ubuntu/actions/tobe/project.jar /home/ubuntu/actions/current/project.jar   # tobe 디렉터리에 있는 빌드된 파일을 current 디렉터리로 옮기기
            cd /home/ubuntu/actions/current
            sudo fuser -k -n tcp 8080 || true 
            nohup java -jar project.jar > ./output.log 2>&1 & 
            rm -rf /home/ubuntu/actions/tobe                                                    # 다음 푸시가 될 때 기존 파일에 의한 영향이 없도록 삭제 처리          
```

-----------------------
</details>

### ✅확장성을 요구하는 프로젝트에서 CI/CD 구축하기(feat. S3 & CodeDeploy)

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

[img.png](image/img_6.png)

#### CodeDeploy에 대한 역할을 생성하기

[img.png](image/img_8.png)

#### CodeDeploy 애플리케이션과 배포 그룹 생성하기

[img.png](image/img_9.png)

#### EC2에 대한 역할 생성하기

* EC2에 왜 역할이 필요한가?
* EC2는 AWS S3에 저장된 빌드 파일을 가져와야 하기 때문에 S3에 접근할 수 있는 권한이 필요하다.
* 따라서 EC2에 대한 역할을 생성해야 한다.

[img.png](image/img_10.png)

[img.png](image/img_11.png)

#### Code Deploy Agent 설치하기

```shell
$ sudo apt update && \
sudo apt install -y ruby-full wget && \
cd /home/ubuntu && \
wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install && \
chmod +x ./install && \
sudo ./install auto
```

#### Code Deploy Agent가 정상적으로 작동하고 있는지 확인하기

```shell
$ systemctl status codedeploy-agent
```

#### Github Actions가 AWS S3, Code Deploy에 접근할 수 있도록 권한을 지정하기

[img.png](image/img_12.png)

#### 생성된 사용자의 보안 자격 증명에서 AWS Access Key, AWS Secret Key를 발급받아 Github Repository Secret 변수에 저장하기

* 액세스 키 : AKIAQE43JXPDJBORVMV5
* 비밀 액세스 키 : dLoYLGrpCMlvxIUyLNW6u8Bax33opBU8ovKDwXed
* 이 때, 해당 화면을 벗어나게 되면 다시는 액세스 키와 비밀 액세스 키를 조회할 수 없다. 따라서 분실하지않도록 잘 보관해야 한다.

#### Code Deploy가 실행될 때 필요한 appspec.yml 파일 작성하기

* appspec.yml 파일은 Code Deploy가 해당 파일을 기반으로 실행되기 때문에 작성을 해줘야 한다.

```yaml
version: 0.0
os: linux

files:
  # S3에 저장한 파일들 중 destination(AWS EC2)으로 이동시킬 대상을 지정한다.
  # / 이라고 지정하면 S3에 저장한 전체 파일을 뜻한다.
  - source: /
    # EC2의 어떤 경로에 저장할 지 지정한다.
    destination: /home/ubuntu/Instagram-Server

permissions:
  - object: /
    owner: ubuntu
    group: ubuntu

hooks:
  ApplicationStart:
    - location: scripts/start-server.sh
      timeout: 60
      runas: ubuntu
```

#### 쉘 스크립트 작성하기

```shell
#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
cd /home/ubuntu/Instagram-Server
sudo fuser -k -n tcp 8080 || true
nohup java -jar project.jar > ./output.log 2>&1 &
echo "--------------- 서버 배포 끝 -----------------"
```

#### Code Deploy 로그 확인하기

* 겉으로 보았을 때 성공했더라도 Code Deploy의 로그를 확실하게 확인하는 것이 좋다.

```shell
$ /opt/codedeploy-agent/deployment-root/{deployment-group-ID}/{deployment-ID}/logs/scripts.log

$ /opt/codedeploy-agent/deployment-root/{배포 그룹 ID}/{배포 ID}/logs/scripts.log
```

-----------------------
</details>

### ✅Docker 컨테이너 기반의 스프링 백엔드 CI/CD 구축하기

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* 앞서 정리했던 **[✅확장성을 요구하는 프로젝트에서 CI/CD 구축하기(feat. S3 & CodeDeploy)]** 에서 더 나아가 Docker 컨테이너 기반의 인프라 구조로 확장해보려고 한다.

#### Ubuntu에서 JDK 설치하기

```shell
$ sudo apt update && /
sudo apt install openjdk-17-jdk -y
```

#### Ubuntu에서 Docker, Docker Compose 설치하기

```shell
$ sudo apt-get update && \
	sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common && \
	curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - && \
	sudo apt-key fingerprint 0EBFCD88 && \
	sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
	sudo apt-get update && \
	sudo apt-get install -y docker-ce && \
	sudo usermod -aG docker ubuntu && \
	sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
	sudo chmod +x /usr/local/bin/docker-compose && \
	sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

#### 위에서 CLI로 처리한 명령어들이 잘 처리됐는지 확인하기

```shell
$ java -version # Java 설치 확인

$ docker -v # Docker 버전 확인
$ docker compose version # Docker Compose 버전 확인
```

#### Github Actions의 IAM에 권한을 추가하기

* 우리는 Amazon ECR을 사용할 예정이기 때문에 Amazon ECR에 대한 권한을 추가해준다.

[img.png](image/img_13.png)

#### Elastic Container Registry(ECR) 만들기

* 아무나 접근할 수 없도록 프라이빗으로 설정하여 만든다.

#### Dockerfile 기반으로 프로젝트를 만들기

* Dockerfile을 작성하여 어떤 이미지를 기반으로 컨테이너를 만들 것인지를 알려줘야 한다.

```dockerfile
FROM openjdk:17-jdk

COPY build/libs/*SNAPSHOT.jar project.jar

ENTRYPOINT ["java", "-jar", "/project.jar"]
```

#### EC2가 private ECR에 접근할 수 있도록 세팅하기

```shell
$ sudo apt update
$ sudo apt install amazon-ecr-credential-helper
```

레퍼런스 : [Amazon ECR 깃허브 리포지토리](https://github.com/awslabs/amazon-ecr-credential-helper?tab=readme-ov-file)

#### Configuration 설정하기

* Amazon ECR 레퍼런스를 보게 되면 Configuration 설정 파일을 만들라고 나온다.
* `~` 경로에서 `.docker`라는 폴더를 만들고, 그 하위에 `config.json` 파일을 만들어서 아래와 같이 작성한다.

```json
{
	"credsStore": "ecr-login"
}
```

#### IAM Role을 활용해서 EC2가 ECR에 접근할 수 있도록 권한을 부여하기

* 이전에 EC2에 연결된 IAM 역할을 수정한 적이 있다.
* 그 역할에 ECR에 대해 접근할 수 있는 권한인 `AmazonEC2ContainerRegistryFullAccess` 정책을 추가한다.

[img.png](image/img_14.png)

#### Docker 기반 CI/CD 작성하기

* Docker 컨테이너 기반의 Github Actions 파일을 작성해야 한다.

```yaml
name: Deploy To EC2

on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Github Repository 파일 불러오기
        uses: actions/checkout@v4

      - name: JDK 17버전 설치
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      - name: resource 디렉터리 만들기
        run: mkdir ./src/main/resources

      - name: application.yml 파일 만들기
        run: echo "${{ secrets.APPLICATION_PROPERTIES }}" > ./src/main/resources/application.yml

      - name: 실행 권한 부여하기
        run: chmod +x ./gradlew

      - name: 테스트 및 빌드하기
        run: ./gradlew clean build -x test

      - name: AWS Resource에 접근할 수 있게 AWS credentials 설정
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-region: ap-northeast-2
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}

      - name: ECR에 로그인하기
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      - name: Docker 이미지 생성
        run: docker build -t aniwhere .

      - name: Docker 이미지에 Tag 붙이기
        run: docker tag aniwhere ${{ steps.login-ecr.outputs.registry }}/instagram-server:latest

      - name: ECR에 Docker 이미지 Push하기
        run: docker push ${{ steps.login-ecr.outputs.registry }}/instagram-server:latest

      - name: docker-compose.yml 전송하기
        uses: appleboy/scp-action@master
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USERNAME }}
          key: ${{ secrets.EC2_PRIVATE_KEY }}
          source: "docker-compose.yml, Dockerfile, build/libs/*.jar"
          target: "~/app"

      - name: SSH로 EC2에 접속하기
        uses: appleboy/ssh-action@v1.0.3
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USERNAME }}
          key: ${{ secrets.EC2_PRIVATE_KEY }}
          script_stop: true
          script: |
            cd ~/app
            docker compose down || true
            docker compose pull
            docker compose up -d --build
```

#### docker-compos 활용하여 컨테이너에 Redis, MySQL도 추가하기

```yaml
services:
  instagram-server:
    build: .
    ports:
      - 8080:8080
    depends_on:
      mysql:
        condition: service_healthy
      redis:
        condition: service_healthy

  mysql:
    image: mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: aniwhere
      MYSQL_DATABASE: aniwhere
    volumes:
      - mysql_data:/var/lib/mysql
    ports:
      - 3306:3306
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping" ]
      interval: 5s
      retries: 10

  redis:
    image: redis:latest
    ports:
      - 6379:6379
    healthcheck:
      test: [ "CMD", "redis-cli", "ping" ]
      interval: 5s
      retries: 10

volumes:
  mysql_data:
```

* 이 때, `application.yml`도 수정을 해야 한다.
* Spring 애플리케이션과 소통을 위해 docker-compose에 설정된 서비스명을 기재해야 한다.
* 위에서는 `mysql` 서비스명을 mysql, `redis` 서비스명을 redis로 설정했다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://mysql:3306/aniwhere
    username: root
    password: aniwhere
  data:
    redis:
      host: redis
      port: 6379
  jpa:
    hibernate:
      ddl-auto: create
    defer-datasource-initialization: true
```

-----------------------
</details>