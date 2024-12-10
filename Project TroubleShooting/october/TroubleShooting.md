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

### ✅ AWS Multipart Upload 트러블 슈팅

<img src="https://techblog.woowahan.com/wp-content/uploads/2023/08/Spring-Boot%E1%84%8B%E1%85%A6%E1%84%89%E1%85%A5-S3%E1%84%8B%E1%85%A6-%E1%84%91%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%8B%E1%85%B3%E1%86%AF-%E1%84%8B%E1%85%A5%E1%86%B8%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB-%E1%84%89%E1%85%A6-%E1%84%80%E1%85%A1%E1%84%8C%E1%85%B5-%E1%84%87%E1%85%A1%E1%86%BC%E1%84%87%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5-9.jpg">

* Multipart 업로드는 AWS S3에서 제공하는 파일 업로드 방식이다. 업로드할 파일을 작은 Part로 나누어 각 부분을 개별적으로 업로드한다.
* 파일의 바이너리 데이터가 Spring Boot 서버를 거치지 않고 AWS S3에 다이렉트로 업로드되기 때문에 서버의 부하를 고려하지 않아도 된다.
* 각 파트별 ETag 값을 AWS S3로부터 응답으로 받아 하나의 파일에 대한 모든 ETag 값이 다 존재하게 되면 AWS에서 하나의 객체로 조립하여 저장한다.
* 또한 몇 개의 파트가 업로드되었는지 확인하여 사용자에게 업로드 진행사항을 제공할 수 있다.

<img src="https://techblog.woowahan.com/wp-content/uploads/2023/08/img-8-1.png">

* 업로드 프로세스
  * Multipart 업로드 시작
  * PreSignedURL 발급
  * PreSignedURL part 업로드
  * Multipart 업로드 완료

예외) Multipart 업로드 취소

<img src="https://techblog.woowahan.com/wp-content/uploads/2023/08/Spring-Boot%E1%84%8B%E1%85%A6%E1%84%89%E1%85%A5-S3%E1%84%8B%E1%85%A6-%E1%84%91%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%8B%E1%85%B3%E1%86%AF-%E1%84%8B%E1%85%A5%E1%86%B8%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB-%E1%84%89%E1%85%A6-%E1%84%80%E1%85%A1%E1%84%8C%E1%85%B5-%E1%84%87%E1%85%A1%E1%86%BC%E1%84%87%E1%85%A5%E1%86%B8-%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5-6-1.jpg">

* 클라이언트가 서버 측(스프링 백엔드)에 UploadID 발급을 요청한다.
* 서버 측에서 만들어진 UploadID를 클라이언트로 응답을 제공한다.
* 업로드할 Part의 URL(PreSigned URL) 발급을 서버 측에 요청한다.
* 서버 측에서 UploadID를 기반으로 PreSigned URL을 생성해 클라이언트에 응답으로 제공한다.
* 클라이언트에서 요청하는 파트 개수 만큼 반복하면서 AWS S3에 바이너리 데이터를 업로드한다.
* 업로드가 완료된 후 클라이언트에서 서버 측에 Multipart 업로드 완료 요청을 보낸다.
* 서버 측에서 업로드가 완료되었다는 성공 응답을 클라이언트에 보낸다.(이 때, 업로드가 완료된 후 S3 정적 저장소에 저장되는 URL을 리포지토리에 저장)

```json
{
    "timestamp": "2024-12-04T16:41:18.886+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "com.amazonaws.services.s3.model.AmazonS3Exception: The specified upload does not exist. The upload ID may be invalid, or the upload may have been aborted or completed. (Service: Amazon S3; Status Code: 404; Error Code: NoSuchUpload; Request ID: JN47XH27KFHFA8HH; S3 Extended Request ID: RrZGMCIhmb5cgPTs15CaqRN681vIOdD0aTfk1YU/VTcdxf01U9SKF9G8lo7NEjpA581e6exwEKo=; Proxy: null), S3 Extended Request ID: RrZGMCIhmb5cgPTs15CaqRN681vIOdD0aTfk1YU/VTcdxf01U9SKF9G8lo7NEjpA581e6exwEKo=\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.handleErrorResponse(AmazonHttpClient.java:1811)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.handleServiceErrorResponse(AmazonHttpClient.java:1395)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1371)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1145)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:802)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:770)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:744)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:704)\n\tat com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:686)\n\tat com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:550)\n\tat com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:530)\n\tat com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5062)\n\tat com.amazonaws.services.s3.AmazonS3Client.invoke(AmazonS3Client.java:5008)\n\tat com.amazonaws.services.s3.AmazonS3Client.completeMultipartUpload(AmazonS3Client.java:3490)\n\tat com.october.back.media.service.MediaService.uploadComplete(MediaService.java:87)\n\tat com.october.back.media.controller.MediaController.completeMultipartUpload(MediaController.java:47)\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\n\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:569)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188)\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:926)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:831)\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089)\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979)\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483)\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:384)\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905)\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741)\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190)\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)\n\tat java.base/java.lang.Thread.run(Thread.java:840)\n",
    "message": "The specified upload does not exist. The upload ID may be invalid, or the upload may have been aborted or completed. (Service: Amazon S3; Status Code: 404; Error Code: NoSuchUpload; Request ID: JN47XH27KFHFA8HH; S3 Extended Request ID: RrZGMCIhmb5cgPTs15CaqRN681vIOdD0aTfk1YU/VTcdxf01U9SKF9G8lo7NEjpA581e6exwEKo=; Proxy: null)",
    "path": "/api/media/IMAGE/complete-upload"
}
```

* 클라이언트가 S3에 미디어 파일을 업로드할 때, 파트 넘버를 나누어 지정해야 된다.
* 위의 에러는 REST API 테스트 툴인 포스트맨으로 테스트할 때, 한 번에 `PUT` 메서드로 요청을 보냈기 때문에 발생하는 오류라고 한다.

### ✅ java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allow

* 리뷰 서비스 코드를 테스트하는 과정에서 위와 같은 오류가 발생했다.
* 전체 클래스 코드를 보면 아래와 같다.

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;

	public ReviewResponse createReview(ReviewServiceDto request) {

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		Review entity = request.toEntity(user);
		Review savedReview = reviewRepository.save(entity);
		return ReviewResponse.from(savedReview);
	}
}
```

* 클래스 레벨에서의 기본 설정이 `readOnly = true` 옵션으로 지정되어 있다.
* 리뷰를 작성하는 작업은 데이터를 쓰는 작업이기 때문에 이 메서드에 대해서는 읽기 전용을 해제해야 한다.
* 따라서 전체 클래스 레벨에서는 `readOnly = true`를 사용하되 메서드 레벨에서 데이터 변화가 필요한 경우 `@Transactional` 어노테이션을 추가하도록 하자.