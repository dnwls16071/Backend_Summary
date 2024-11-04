### ✅ERROR: failed to solve: process "/bin/sh -c sudo apt update && sudo apt install -y git" did not complete successfully: exit code: 127

<details>
   <summary> 에러 내용 해결 방법 (👈 Click)</summary>
<br />

* git과 ubuntu가 같이 있는 이미지가 없어 커스텀 이미지를 만들던 도중 발생했던 오류

```dockerfile
 => ERROR [2/2] RUN sudo apt update && sudo apt install -y git                                                                                                                                                                  0.1s
------
 > [2/2] RUN sudo apt update && sudo apt install -y git:
0.102 /bin/sh: sudo: command not found
------
Dockerfile:3
--------------------
   1 |     FROM ubuntu
   2 |     
   3 | >>> RUN sudo apt update && sudo apt install -y git
   4 |     
   5 |     ENTRYPOINT ["/bin/bash", "-c", "sleep 500"]
--------------------
ERROR: failed to solve: process "/bin/sh -c sudo apt update && sudo apt install -y git" did not complete successfully: exit code: 127
```

* 기본 shell이나 sudo 명령어가 없는 경우 `sudo` 명령어를 인식하지 못한다는 것이다.
* 아래와 같이 수정하여 작성했다.

```dockerfile
FROM ubuntu

RUN apt update && apt install -y git

ENTRYPOINT ["/bin/bash", "-c", "sleep 500"]
```

-----------------------
</details>

### ✅OCI runtime exec failed: exec failed: unable to start container process: exec: "bash": executable file not found in $PATH: unknown

<details>
   <summary> 에러 내용 해결 방법 (👈 Click)</summary>
<br />

* 백엔드와 프론트엔드 프로젝트를 Docker로 배포하는 과정이 어떤지 실습해보는 과정 중에서 프론트엔드 프로젝트를 Docker 컨테이너로 실행시키기 위해 아래와 같이 입력했는데 제목과 같은 오류가 발생했다.

```dockerfile
docker exec -it [컨테이너 ID] bash
```

* Next.js 도커 컨테이너에서 bash가 실행되지 않는 이유는 기본 이미지가 alpine 리눅스가 slim 버전을 사용하기 때문이다.
* 따라서 커스텀으로 직접 bash를 설치할 수 있도록 `RUN` 명령어를 추가하여 거기서 추가 작업을 처리할 수 있도록 하거나 아니면 `sh`로 접속하면 된다.

```dockerfile
docker exec -it [컨테이너 ID] sh
```

-----------------------
</details>

### ✅Docker(SpringBoot & MySQL) 컨테이너 연동 오류

<details>
   <summary> 에러 내용 해결 방법 (👈 Click)</summary>
<br />

[img.png](docker_img/img.png)

* 각각의 컨테이너는 자신만의 네트워크망과 IP 주소를 가진다.
* 호스트 컴퓨터의 입장에서 localhost는 호스트 컴퓨터 자체를 가리키고 Spring 컨테이너의 입장에서 localhost는 Spring 자체를 가리킨다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: pwd1234
    driver-class-name: com.mysql.cj.jdbc.Driver
```

* Spring 애플리케이션에서 필요한 `application.yml` 파일이다.
* `url` 속성을 보면 `localhost`가 보이는데 이는 Docker 컨테이너 입장으로 접근해보면 Spring 컨테이너 자체를 가리키는 것이고 Spring 컨테이너 내부에 MySQL이 존재하는 것으로 생각할 수 있다.
* 따라서, 여기서 `url`을 수정해 `localhost` 대신에 docker-compose에 작성한 이름을 입력해주면 된다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://[Docker Compose에 작성한 서비스명]:3306/mydb
    username: root
    password: pwd1234
    driver-class-name: com.mysql.cj.jdbc.Driver
```

-----------------------
</details>