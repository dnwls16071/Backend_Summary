# Docker

### ✅Docker CLI

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* 이미지 다운로드
  * `docker pull [이미지명]`
* 이미지 조회
  * `docker image ls`
* 이미지 삭제
  * `docker image rm [-f] [이미지명]`
* 컨테이너 생성 및 실행 : 
  * `docker create [이미지명]`
  * `docker start [컨테이너 ID]`
* 컨테이너 중단 및 삭제
  * `docker stop [컨테이너 ID]`
  * `docker rm [컨테이너 ID]`
* 컨테이너 이미지 다운로드 및 생성/실행
  * `docker run [이미지명]` : 포그라운드 실행
  * `docker run -d [이미지명]` : 백그라운드 실행
* 호스트 포트와 컨테이너 포트 바인딩하기
  * `docker run -d -p [호스트 포트 번호]:[컨테이너 포트 번호] [이미지명]`
* 실행 중인 컨테이너 조회
  * `docker ps`
* 중단된 컨테이너를 포함한 모든 컨테이너 조회
  * `docker ps -a`
* 도커 컨테이너 종료
  * `docker stop [컨테이너 ID]`
* 도커 컨테이너 로그 조회
  * `docker logs [컨테이너 ID]`
* 실행 중인 도커 컨테이너 내부에 접속
  * `docker exec -it [컨테이너 ID] bash`

![img.png](image/img.png)

* Docker Container가 어떻게 동작하는지에 대한 전체 프로세스를 이해
  * 클라이언트는 사용 중인 호스트 컴퓨터의 포트 번호 4000번으로 접속을 하게 되면 호스트 컴퓨터 내부의 컨테이너인 nginx 컨테이너의 포트 번호인 80번과 바인딩이 되면서 연결이 정상적으로 이루어진다.
  * 단, 클라이언트가 호스트 컴퓨터 내부의 nginx 컨테이너의 포트 번호 80번으로 직접 접속을 시도하게 되면 연결이 정상적으로 이루어지지 않는다.

![img_1.png](image/img_1.png)

* Docker Container가 어떻게 동작하는지에 대한 전체 프로세스를 이해
  * 호스트 컴퓨터의 6379번 포트와 컨테이너 내부의 6379번 포트를 바인딩하여 연결이 정상적으로 이루어진다.
  * 클라이언트가 호스트 컴퓨터의 6379번 포트로 접속을 하게 되면 컨테이너 내부의 레디스에 접근할 수 있게 된다.

-----------------------
</details>

### ✅Docker Volume

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* 도커 컨테이너가 가지는 문제점
  * Docker를 활용하면 특정 프로그램을 컨테이너로 간편하게 띄울 수 있어 별도의 불필요한 설치를 안 거쳐도 된다.
  * 하지만 프로그램에 새로운 기능이 추가되면 새로운 이미지를 만들어서 컨테이너를 실행시키는데 이 때, Docker는 새로운 컨테이너를 만들어 통째로 갈아끼우는 방식으로 교체를 하기 때문에 데이터의 유실이 발생한다.
  * 컨테이너 내부에 저장된 데이터가 삭제되면 안되는 경우에는 볼륨(Volume)이라는 개념을 활용할 수 있다.
* Docker Volume이란, 도커 컨테이너 내부의 데이터를 영속적으로 저장하기 위한 수단이다.
* Volume은 컨테이너 내부 저장 공간을 사용하지 않고 호스트 저장 공간을 공유해서 사용하는 기법이다.

```dockerfile
docker run -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -v /Users/jwj/Desktop/개발/docker-mysql/mysql-data:/var/lib/mysql -d mysql
```

* `/Users/jwj/Desktop/개발/docker-mysql` 호스트 컴퓨터 저장 경로 하위에 `mysql-data` 디렉터리가 생성되면서 데이터가 저장이 된다. 
* 이 때, 데이터베이스의 초기 설정 비밀번호 역시 같이 저장되기 때문에 도커 컨테이너를 지우고 다시 설치하여 데이터베이스에 접속할 때 비밀번호를 바꾸게 되면 접속이 되지 않는다.
* 미리 디렉터리를 만들면 안된다. 이 점을 주의하자.

-----------------------
</details>

### ✅Docker Compose

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* Dockerfile이란, Docker 이미지를 만들게 해주는 파일이다.

```dockerfile
FROM [이미지명]
FROM [이미지명]:[태그명]
```

[img_2.png](image/docker%20screenshot.png)

* Dockerfile을 기반으로 커스텀 이미지를 만들었으나 실제로 컨테이너를 조회해보면 아무것도 떠있지 않은 상황이 된다.
* 작업이 완료가 되면 컨테이너도 자동으로 종료가 된다.
* 제대로 테스트를 해보기 위해 아래와 같이 Dockerfile을 수정한다.

```dockerfile
FROM openjdk:21-jdk

# 아래 명령어를 추가하는 이유 : 컨테이너가 바로 종료되는 것을 막기 위함
# 500초 동안 시스템을 일시 정지 시키는 명령어
ENTRYPOINT ["/bin/bash", "-c", "sleep 500"]
```

#### COPY : 파일 복사(이동) & ENTRYPOINT : 컨테이너 실행 시 실행할 명령어 지정

* COPY는 호스트 컴퓨터에 있는 파일들을 복사해서 컨테이너로 전달하는 명령어다.

```dockerfile
FROM ubuntu

# COPY [호스트 컴퓨터에 있는 복사할 파일의 경로] [컨테이너에서 파일이 위치할 경로]
COPY My-App.txt /My-App.txt

ENTRYPOINT ["/bin/bash", "-c", "sleep 500"]
```



-----------------------
</details>