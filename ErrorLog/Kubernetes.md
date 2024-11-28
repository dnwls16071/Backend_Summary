### ✅ The connection to the server localhost:8080 was refused - did you specify the right host or port?

* Kubernetes 설치 과정에서 서버 측에서 연결을 거부했다는 내용의 오류였다.
* 우선 Docker-Desktop을 설치하고 Apple Chip 버전을 다운로드 후 설치한다.(나는 이미 Docker Desktop이 있어 이 부분은 패스)

[Download Docker Desktop | Docker](https://www.docker.com/products/docker-desktop/)

* 설치된 Docker Desktop을 실행하면 메인 화면에서 kubernetes가 있는지 확인한다.

<img src="/kubernetes/img1.png">

* [Enable Kubernetes]를 클릭하여 쿠버네티스를 활성화시킨 후 재시작하면 정상적으로 작동된다.

<img src="/kubernetes/img2.png">

* 실행이 정상적으로 완료되었는지 터미널에서 아래 명령어를 실행해보자.

> kubectl version
>
> Client Version: v1.31.3
> Kustomize Version: v5.4.2
> Server Version: v1.30.2
