### ✅ (Back)JWT(Json Web Token)를 클라이언트에 응답으로 보내주는 과정에서 겪은 트러블 슈팅

### ✅ (DevOps)Amazon ECR - Docker Hub를 통한 컨테이너 기반의 CI/CD 구축 트러블 슈팅

### ✅ (Back)Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.example.aniwhere.infrastructure.persistence.RefreshTokenRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository

* Spring Data Jpa Repository가 Spring data Redis Repository로 인식되어 Reids가 이게 내 리포지토리가 맞는지 확인하는 과정에서 발생하는 로그 내용이다.

### ✅ (DevOps)SpringBoot(Backend) & Next.js(Frontend) Nginx Reverse Proxy 트러블 슈팅

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