### ✅ An error occurred (AccessDenied) when calling the CreateMultipartUpload operation: User: arn:aws:iam::686255967901:user/photobooth is not authorized to perform: s3:PutObject on resource: {~~~}  because no identity-based policy allows the s3:PutObject action

* AWS CodeDeploy와 AWS S3, Amazon ECR을 활용한 무중단 배포 인프라 구조를 구축하면서 발생했던 오류다.
* 해당 오류는 Amazon S3의 액세스 거부(HTTP 403 Forbidden)로 AWS가 권한 부여 요청을 명시적 또는 묵시적으로 거부할 때 나타난다.
* AWS CodeDeploy가 EC2한테 S3로부터 빌드 파일을 다운 받은 뒤 배포를 진행하도록 명령하는 과정에서 EC2는 AWS S3에 접근할 권한이 필요한데, 이 부분에서 발생한 오류인 것이다.
* 이 오류를 해결하려면 IAM에서 역할을 만들 때 `AmazonS3FullAccess` 정책을 추가해서 만들면 해결이 가능하다.

### ✅ S3 AccessControlListNotSupported

* AWS Multipart Upload를 요청하여 UploadId 응답을 반환받아 Pre-Signed URL을 요청하는 과정에서 제목과 같은 오류가 계속 발생했다.

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<Error>
<Code>AccessControlListNotSupported</Code> 
<Message>The bucket does not allow ACLs</Message> 
<RequestId>NTM3MCG5R9WR2CTJ</RequestId> 
<HostId>HpNLy9ly7cLDjFSE3fBT47+c1xomKG08q6cDDYUkq+R4WSNf/QVDdIzhRlljMn7+aFJF3ILTLmo=</HostId> 
</Error>
```

* 해당 오류는 Amazon S3 버킷의 ACL(Access Control List) 관련 문제이다.
* S3 버킷이 객체 소유권 설정에서 ACL 비활성화로 되어 있기 때문이다.
* S3 버킷의 객체 소유권 설정을 ACL 활성화로 변경해주었더니 문제를 해결할 수 있었다.