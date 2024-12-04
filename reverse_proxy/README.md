### ✅ Spring & Nginx Reverse Proxy

##### 프록시 서버(Proxy Server)

* 프록시 서버는 최종 사용자를 찾는 웹 사이트에서 분리하는 중개 서버를 말한다.

##### 포워드 프록시(Forward Proxy) vs 리버스 프록시(Reverse Proxy)

<img src="https://blog.containerize.com/ko/how-to-setup-and-configure-nginx-as-reverse-proxy/images/forward-proxy-server-1024x482.png#center">

* 포워드 프록시는 클라이언트 측에서 사용하며, 클라이언트를 대신하여 요청을 보내는 중간 서버라고 생각하면 된다.
* 클라이언트가 프록시 서버를 통해 외부 리소스에 접근한다.

<img src="https://blog.containerize.com/ko/how-to-setup-and-configure-nginx-as-reverse-proxy/images/reverse-proxy-server-1024x482.png#center">

* 서버 측에서 사용되며, 서버 앞단에서 클라이언트의 요청을 받아 적절한 내부 서버로 전달한다.
* 클라이언트는 실제 서버의 존재를 알 수 없다는 점에서 보안을 향상시킬 수 있고 리버스 프록시를 실제 서버로 인식하게 한다.

##### 이번에 적용할 리버스 프록시(Reverse Proxy)의 장점

* 보안 : 리버스 프록시는 서버를 직접 노출시키지 않고 클라이언트와의 통신을 중계하므로 서버의 실제 위치를 숨길 수 있고 보안을 한 층 더 강화합니다.
* 프록시 서버를 도입함으로써 본래 서버의 IP 주소를 노출시키지 않을 수 있어 해커들의 DDos 공격과 같은 공격을 막는데 용이하다.
* 로드 밸런싱 : 리버스 프록시는 여러 서버로 요청을 분산할 수 있으며 부하 분산을 구현할 수 있다. 이는 트래픽이 여러 서버에 고르게 분산되어 서버의 성능을 향상시키고 가용성을 높이는 데 도움이 된다.
* 캐싱 : 리버스 프록시는 정적 리소스를 캐싱하여 동일한 요청에 대한 응답을 빠르게 제공할 수 있어 웹 서버 부하를 줄이고 대역폭을 절약할 수 있다.
* SSL 종단 지원 : 리버스 프록시는 클라이언트와의 SSL/TLS 연결을 종단하고 백엔드 서버와의 일반 HTTP 연결을 사용하게 할 수 있다.

### ✅ SpringBoot & Nginx Reverse Proxy 설정 방법

```shell
# apt 업데이트
$ sudo apt update
```

```shell
# nginx 설치
$ sudo apt install nginx
```

```shell
# nginx 실행
$ sudo systemctl start nginx

ubuntu@ip-172-31-36-66:~$ sudo systemctl status nginx
● nginx.service - A high performance web server and a reverse proxy server
     Loaded: loaded (/usr/lib/systemd/system/nginx.service; enabled; preset: enabled)
     Active: active (running) since Wed 2024-12-04 05:11:05 UTC; 2min 33s ago
       Docs: man:nginx(8)
    Process: 2103 ExecStartPre=/usr/sbin/nginx -t -q -g daemon on; master_process on; (code=exited, status=0/SUCCESS)
    Process: 2105 ExecStart=/usr/sbin/nginx -g daemon on; master_process on; (code=exited, status=0/SUCCESS)
   Main PID: 2106 (nginx)
      Tasks: 2 (limit: 1130)
     Memory: 1.7M (peak: 1.9M)
        CPU: 7ms
     CGroup: /system.slice/nginx.service
             ├─2106 "nginx: master process /usr/sbin/nginx -g daemon on; master_process on;"
             └─2107 "nginx: worker process"

Dec 04 05:11:05 ip-172-31-36-66 systemd[1]: Starting nginx.service - A high performance web server and a reverse proxy server...
Dec 04 05:11:05 ip-172-31-36-66 systemd[1]: Started nginx.service - A high performance web server and a reverse proxy server.
```

```shell
$ sudo vi /etc/nginx/nginx.conf

user www-data;
worker_processes auto;
pid /run/nginx.pid;
error_log /var/log/nginx/error.log;
include /etc/nginx/modules-enabled/*.conf;

events {
        worker_connections 768;
        # multi_accept on;
}

http {
        server {
                listen 80;

                location / {
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


#mail {
#       # See sample authentication script at:
#       # http://wiki.nginx.org/ImapAuthenticateWithApachePhpScript
#
#       # auth_http localhost/auth.php;
#       # pop3_capabilities "TOP" "USER";
#       # imap_capabilities "IMAP4rev1" "UIDPLUS";
#
#       server {
#               listen     localhost:110;
#               protocol   pop3;
#               proxy      on;
#       }
#
#       server {
#               listen     localhost:143;
#               protocol   imap;
#               proxy      on;
#       }
#}
```

* `proxy_set_header Host $host` : 헤더는 원격 서버로 전달되는 HTTP 요청 헤더 중 하나이다. `$host` 변수는 클라이언트가 요청한 호스트 헤더 값을 가지고 있다.
* `proxy_set_header X-Real-IP $remote_addr` : 헤더는 클라이언트의 실제 IP 주소를 리버스 프록시를 통해 전달한다.
* `proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for` : 헤더는 클라이언트가 어떤 IP 주소를 통해 요청을 보냈는가를 기록하며, 여러 프록시 서버를 거친 경우 쉼표로 구분된 목록으로 기록된다.

##### 실행 결과

<img src="/images/reverse_proxy.png">

* Nginx의 EC2의 IP에 80번 포트로 접속했는데 SpringBoot EC2의 IP에 8080번 포트로 접속한 것과 동일한 화면이 출력되는 것을 볼 수 있다.
* 또한 SpringBoot의 포트인 8080번 포트로 접속하면 위와 동일한 화면이 뜨지 않고 접속이 불가능한 것을 알 수 있다.
* 이는 우리가 AWS의 EC2 보안 그룹의 인바운드 규칙에서 8080번 포트를 외부에서 아무나 접속할 수 없도록 설정해주었기 때문이다.
* 위와 같은 설정들을 통해서 무조건 리버스 프록시를 거쳐서 접속할 수 있게 된 것이다.

##### 고민할 부분

* WAS에 대한 접근과 정보 노출을 방어했지만 아직 Nginx를 통해서 요청하면 WAS에 요청하는 것과 똑같이 요청하여 데이터를 획득할 수 있다.
* 그렇기에 고의적으로 리버스 프록시 서버에 많은 요청을 보내게 된다면 이를 그대로 받아들여 서버에 과부하가 오게 된다.
* 이전까지 해왔던 개발에 비하면 보안적인 측면에서 생각할 부분이 상당히 많아졌다.
* 구글링해보면서 찾아본 결과 이런 과도한 트래픽 제어를 할 수 있는 Java rate limiting 라이브러리가 있다는 것을 알게 되었다.
* 라이브러리를 활용하는 방법도 있겠지만 요청이 컨트롤러에 도착하기 전 먼저 도달하게 되는 필터나 인터셉터 측에서도 이런 트래픽 제어가 가능한지 고민해봐야겠다.

### ✅(추가) Swagger API 문서 경로 설정 변경 방법

* 리버스 프록시를 설정하면서 Swagger API 경로 역시 변경을 해줘야했다.
* 이 부분은 공식 문서에 설명이 잘 나와있어서 수월하게 해결할 수 있었다.
* [OpenAPI3 & SpringBoot 설정 레퍼런스](https://springdoc.org/#Introduction)
* `application.yml` 파일을 아래와 같이 작성해주면 된다.

```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html

server:
  servlet:
    context-path: /api
```

* 기본적으로 제공되는 Swagger API 문서 URL을 무효화시키고 개발자가 임의로 변경한 경로로 바꿀 수 있다.
* 위의 설정대로라면 `http://localhost:8080/api/swagger-ui/index.html` 경로로 접근이 가능해진다.