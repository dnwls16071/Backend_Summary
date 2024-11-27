### ✅ An error occurred (AccessDenied) when calling the CreateMultipartUpload operation: User: arn:aws:iam::686255967901:user/photobooth is not authorized to perform: s3:PutObject on resource: {~~~}  because no identity-based policy allows the s3:PutObject action

* AWS CodeDeploy와 AWS S3, Amazon ECR을 활용한 무중단 배포 인프라 구조를 구축하면서 발생했던 오류다.
* 해당 오류는 Amazon S3의 액세스 거부(HTTP 403 Forbidden)로 AWS가 권한 부여 요청을 명시적 또는 묵시적으로 거부할 때 나타난다.
* AWS CodeDeploy가 EC2한테 S3로부터 빌드 파일을 다운 받은 뒤 배포를 진행하도록 명령하는 과정에서 EC2는 AWS S3에 접근할 권한이 필요한데, 이 부분에서 발생한 오류인 것이다.
* 이 오류를 해결하려면 IAM에서 역할을 만들 때 `AmazonS3FullAccess` 정책을 추가해서 만들면 해결이 가능하다.