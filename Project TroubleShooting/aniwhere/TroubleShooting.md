### ✅ (Back)JWT(Json Web Token)를 클라이언트에 응답으로 보내주는 과정에서 겪은 트러블 슈팅

### ✅ (DevOps)Amazon ECR - Docker Hub를 통한 컨테이너 기반의 CI/CD 구축 트러블 슈팅

### ✅ (Back)Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.example.aniwhere.infrastructure.persistence.RefreshTokenRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository

* Spring Data Jpa Repository가 Spring data Redis Repository로 인식되어 Reids가 이게 내 리포지토리가 맞는지 확인하는 과정에서 발생하는 로그 내용이다.

### ✅ (DevOps)SpringBoot(Backend) & Next.js(Frontend) Nginx Reverse Proxy 트러블 슈팅

* 리버스 프록시(Reverse Proxy) : 리버스 프록시란, 클라이언트 요청을 대신 받아 내부 서버로 전달해주는 것을 말한다.
* 프록시(Proxy)란, 대리라는 의미로 서버 요청에 대한 대리자 역할을 수행하는 것을 프록시 서버라고 한다.
* 프록시 서버는 클라이언트와 서버 사이에서 동작하는 중간 서버를 말한다.
* 클라이언트가 서버에 직접 요청을 보내는 대신 프록시 서버는 클라이언트 요청을 받아 서버로 전달하고, 서버에서 받은 응답을 클라이언트에게 다시 전달하는 중계자 역할을 한다.

##### 포워드 프록시(Forward Proxy)

<img src="https://velog.velcdn.com/images/pgmjun/post/c82e44a6-da24-49f3-955d-f17315b1a046/image.png">

* 클라이언트와 인터넷 사이에 있는 프록시 서버를 뜻한다.
* 포워드 프록시는 클라이언트의 익명성을 보장해준다.
  * 클라이언트의 요청을 가로채 통신을 대리 수행한다.
  * 때문에 WAS는 클라이언트에 대해 알 수 없다. IP 역추적을 한다고 하더라도 프록시 서버의 IP만 보인다.
* 클라이언트가 요청한 내용을 캐싱한다.
  * 연결된 클라이언트가 요청한 데이터를 반환할 때 저장했다가 다른 클라이언트가 동일한 데이터를 요청하면 인터넷을 거치지 않고 바로 반환해준다.
  * 전송 시간이 절약되고 외부 요청이 감소되어 네트워크 병목 현상을 방지한다.

##### 리버스 프록시(Reverse Proxy)

<img src="https://velog.velcdn.com/images/pgmjun/post/d339dea9-f57e-4525-8ee0-6f14b7fc01bc/image.png">

* 리버스 프록시를 적용하면 로드 밸런싱이 가능하며 서버의 정보를 숨겨 보안성을 얻을 수 있다.
* 클라이언트는 리버스 프록시 서버가 진짜 서버인 것처럼 요청을 보내기 때문에 클라이언트 측에서 서버의 정체를 알 수 없어 보안이 좋다.

##### Nginx Reverse Proxy 설정 방법

* AWS EC2에 접속한 후 Nginx를 설치한다.

```shell
# apt-get 업데이트
sudo apt-get update

# Nginx 설치
sudo apt-get install nginx
sudo service start nginx

# nginx Config 파일 열기
sudo vi /etc/nginx/nginx.conf
```

* /etc/nginx/nginx.conf 내용 수정

```shell
http {

        server {
                listen 80;
                
                # 프론트엔드 측 리버스 프록시 설정
                location / {
                        proxy_pass http://localhost:3000;
                        proxy_set_header Host $host;
                        proxy_set_header X-Real-IP $remote_addr;
                        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                        proxy_set_header X-Forwarded-Proto $scheme;
                }
                
                # 백엔드 측 리버스 프록시 설정
                location /api {
                        rewrite ^/api(.*)$ $1 break;
                        proxy_pass http://localhost:8080;
                        proxy_set_header Host $host;
                        proxy_set_header X-Real-IP $remote_addr;
                        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                        proxy_set_header X-Forwarded-Proto $scheme;
                }
        }

        ##
        # Basic Settings
        ##

        sendfile on;
        tcp_nopush on;
        types_hash_max_size 2048;
        # server_tokens off;

        # server_names_hash_bucket_size 64;
        # server_name_in_redirect off;

        include /etc/nginx/mime.types;
        default_type application/octet-stream;

        ##
        # SSL Settings
        ##

        ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE
        ssl_prefer_server_ciphers on;

        ##
        # Logging Settings
        ##

        access_log /var/log/nginx/access.log;

        ##
        # Gzip Settings
        ##

        gzip on;

        # gzip_vary on;
        # gzip_proxied any;
        # gzip_comp_level 6;
        # gzip_buffers 16 8k;
        # gzip_http_version 1.1;
        # gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

        ##
        # Virtual Host Configs
        ##

        ##include /etc/nginx/conf.d/*.conf;
        ##include /etc/nginx/sites-enabled/*;
}
```

### ✅ (DevOps)error: tar: build/libs/aniwhere-0.0.1-SNAPSHOT.jar: Wrote only 5120 of 10240 bytes, tar: Exiting with failure status due to previous errors, drone-scp error: Process exited with status 2

* 해당 오류는 파일을 서버로 전송하고 압축 해제하는 과정에서 발생한 문제이다. 
* 구체적으로 들여다보면 파일 전송(tar/scp) 과정에서 파일이 완전히 전송되지 않았다.
* JAR 파일의 전체 크기는 10240 바이트인데, 단 5120 바이트만 쓰여진 것이다.
* 구글링을 통해 원인을 찾아본 결과 원인으로 거론된 것들은 다양했다.
  * scp do not create a folder by itself, so be sure your target folder exist(타겟 폴더가 존재하는지 여부)
  * the storage is full(스토리지가 찼는지)
  * user's insufficient permissions in target directory(불충분한 권한)
  * your user does not have a home directory (most likely the action first scps the file into the user's home directory, and then performs a move)(홈 디렉터리가 없거나)


* 일단 첫 번째로 먼저 EC2 서버의 용량을 먼저 확인해보았다.

> df -h

* 확인한 결과 아래와 같이 정리되어 나오는 것을 볼 수 있었다.

> Filesystem       Size  Used Avail Use% Mounted on
> 
> /dev/root        6.8G  6.6G  135M  99% /
> 
> tmpfs            956M     0  956M   0% /dev/shm
>
> tmpfs            383M  1.2M  381M   1% /run
> 
> tmpfs            5.0M     0  5.0M   0% /run/lock
> 
> efivarfs         128K  3.6K  120K   3% /sys/firmware/efi/efivars
> 
> /dev/nvme0n1p16  881M  133M  687M  17% /boot
> 
> /dev/nvme0n1p15  105M  6.1M   99M   6% /boot/efi
> 
> tmpfs            192M   16K  192M   1% /run/user/1000

* 확인한 결과 `/dev/root` 용량이 거의 다 사용된 것을 볼 수 있었다. 이런 경우 SCP 라이브러리를 통한 파일 압축과 전송이 제대로 이루어지지 않는다고도 나와있었다.
* 이를 해결하기 위해 앱 파일과 로그 파일을 먼저 정리 후 Docker 이미지/컨테이너 정리를 해주었다.

> sudo rm -rf ~/app/* 
> 
> sudo rm -rf /var/log/*
> 
> docker system prune -af

* 여유 공간을 확보한 후 다시 CI/CD를 가동하니 정상적으로 작동되는 것을 확인할 수 있었다.

### ✅(Back - Front) 프론트엔드-백엔드 Response 스펙 설계 경험기

* 최근 Aniwhere 프로젝트에서 API Response 스펙을 정의하는 과정에서 마주한 기술적 챌린지와 학습 포인트를 공유하고자 한다.
* FE/BE 개발자 간 Response 상세도에 대한 관점 차이가 발생했다.
* BE 관점에서는 HTTP Status Code, Field Validation 결과, 상세 메타데이터 등 최대한 상세한 정보 제공 선호하였다.
* 반면 FE 관점에서 실제 사용하는 핵심 데이터 위주의 최소한의 정보만을 선호하는 것으로 나타났다.
* 이로 인해 이미 개발된 API들의 리팩토링이 불가피했다.
* 일관되지 않은 Response 구조로 인한 개발 생산성이 저하됐고 API 스펙 변경으로 인한 추가 개발 비용이 발생했다.
* 이런 경험을 통해서 기술적 완성도와 더불어 팀 간 커뮤니케이션의 중요성이 정말 중요하다는 것을 깨달았고 초기 설계 단계에서의 충분한 논의의 필요성을 느꼈다.
* 또한 프로젝트 기술 스펙은 단순 기술적 관점이 아닌 실제 사용자(프론트엔드 개발자)의 니즈에 맞춰 설계되어야 한다는 것을 잘 알게 되었다.
* 이번 프로젝트를 통해 명확한 Response 스펙 기준을 수립했으며, 이를 기반으로 일관성 있는 API 개발을 진행하고 있다.

### ✅ (Back) 인증없이 Swagger API URL을 Spring Security에서 허용하는 방법(Swagger v3 버전)

* Nginx Reverse Proxy 처리에 따라 Swagger API 문서의 경로를 변경해야되는 요구사항이 접수됐다.
* 관련 레퍼런스 1 : [오픈 API 공식문서](https://springdoc.org/#Introduction)
* 관련 레퍼런스 2 : [How to configure Spring Security to allow Swagger URL to be accessed without authentication](https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut)
* `application.yml` 설정 파일에서 Swagger API URL 경로를 다음과 같이 변경하고 이를 JWT Filter, Spring Security Config에 등록해주었다.

```yaml
springdoc:
  swagger-ui:
    path: /api/swagger-ui.html
    config-url: /api/v3/api-docs/swagger-config
    url: /api/v3/api-docs
  api-docs:
    path: /api/v3/api-docs
```