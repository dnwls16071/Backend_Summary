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

#### CodeDeploy 역할 생성하기

* CodeDeploy가 다른 AWS 자원에 접근하기 위해선 권한이 필요하다. 그 권한을 부여해주는 기능이 바로 IAM의 역할이다.


* 체크할 부분

  * 서비스 또는 사용 사례 : CodeDeploy

#### CodeDeploy 애플리케이션 및 배포 그룹 생성하기

* 체크할 부분

  * 애플리케이션 이름 : actions-server
  * 컴퓨팅 플랫폼 : EC2/온프레미스
  * 배포 그룹 이름 : production
  * 서비스 역할 입력 : CodeDeploy 역할
  * 배포 유형 : 현재 위치
  * 환경 구성 : Amazon EC2 인스턴스
  * 배포 설정 : CodeDeployDefault.AllAtOnce & 로드 밸런서 비활성화

#### EC2 역할 생성하기

* 현재 아키텍처를 보면 EC2가 빌드된 파일이 담겨 있는 S3에 접근해야 한다. 그러려면 역시 권한이 필요하다.

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
                "s3:Get*",
                "s3:List*"
            ],
            "Effect": "Allow",
            "Resource": "*"
        }
    ]
}
```
* EC2에 생성한 IAM 역할을 연결한다.

#### Code Deploy Agent

* Code Deploy Agent가 다른 AWS 자원에 접근하려면 권한이 필요하다.
```shell
$ sudo apt update && \
sudo apt install -y ruby-full wget && \
cd /home/ubuntu && \
wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install && \
chmod +x ./install && \
sudo ./install auto
```

* Code Deploy Agent가 정상적으로 설치됐는지 확인하기

```shell
$ systemctl status codedeploy-agent
```

#### Github Actions가 CodeDeploy, S3에 접근할 수 있게 사용자 IAM을 발급

* IAM 사용자가 CodeDeploy, S3에 접근하려면 권한이 필요하다. 이 때 필요한 권한은 다음과 같이 2개가 있다.
* CodeDeploy에 접근하는 권한인 `AWSCodeDeployFullAccess`, 그리고 S3에 접근하는 권한인 `AmazonS3FullAccess`가 있다.
* 보안 자격 증명에서 액세스 키와 비밀 액세스 키를 발급받아 잘 저장한다.
* Github Actions에서 사용할 수 있도록 액세스 키와 비밀 액세스 키를 Secret Variable로 등록한다.

#### 배포 전에 빌드된 파일이 저장될 S3 버킷 생성하기

* Github Actions에서 프로젝트 소스 코드를 빌드하고 생성된 산출물인 jar 파일을 S3 버킷에서 관리해야 한다.

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

      - name: 압축하기
        run: tar -czvf $GITHUB_SHA.tar.gz project.jar appspec.yml scripts

      - name: AWS Resource에 접근할 수 있게 AWS credentials 설정
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-region: ap-northeast-2
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}

      - name: S3에 프로젝트 폴더 업로드하기
        run: aws s3 cp --region ap-northeast-2 ./$GITHUB_SHA.tar.gz s3://actionss3/$GITHUB_SHA.tar.gz

      - name: Code Deploy를 활용해 EC2에 프로젝트 코드 배포
        run: aws deploy create-deployment
          --application-name actions-server                                             # Code Deploy 애플리케이션 이름 
          --deployment-config-name CodeDeployDefault.AllAtOnce            
          --deployment-group-name production                                            # Code Deploy 배포 그룹 이름
          --s3-location bucket=actionss3,bundleType=tgz,key=$GITHUB_SHA.tar.gz
```

#### appspec.yaml 

```yaml
version: 0.0
os: linux

files:
  # S3에 저장한 파일들 중 destination(AWS EC2)으로 이동시킬 대상을 지정한다.
  # / 이라고 지정하면 S3에 저장한 전체 파일을 뜻한다.
  - source: /
    # EC2의 어떤 경로에 저장할 지 지정한다.
    destination: /home/ubuntu/actions

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

#### scripts/start-server.sh

```shell
echo "--------------- 서버 배포 시작 -----------------"
cd /home/ubuntu/actions
sudo fuser -k -n tcp 8080 || true
nohup java -jar project.jar > ./output.log 2>&1 &
echo "--------------- 서버 배포 끝 -----------------"
```

-----------------------
</details>