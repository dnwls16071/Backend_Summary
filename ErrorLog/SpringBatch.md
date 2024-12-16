### ✅ io.netty.handler.ssl.NotSslRecordException: not an SSL/TLS record: ...

* HTTP 요청을 보낼 때, HTTP인지 HTTPS인지 잘 보면 해결할 수 있는 문제였다.
* 관련 레퍼런스 : [io.netty.handler.ssl.NotSslRecordException: not an SSL/TLS record:](https://github.com/spring-cloud/spring-cloud-gateway/issues/378)

### ✅ How to solve deprecation warning of JobBuilderFactory and StepBuilderFactory

* Spring Batch의 버전 업그레이드에 따라 기존에 작성했던 방법인 JobBuilderFactory와 StepBuilderFactory가 deprecated 되었다.

```text
The type JobBuilderFactory has been deprecated since version 5.0.0 and marked for removal
```

* JobBuilderFactory와 StepBuilderFactory 대신에 JobBuilder와 StepBuilder를 사용하면 된다.
* [Spring Batch V5 가이드라인](https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide#transaction-manager-bean-exposureconfiguration)

### ✅ Caused by: java.lang.NoClassDefFoundError: org/apache/hc/client5/http/ssl/TlsSocketStrategy

* 해당 오류의 원인은 httpcore 관련 라이브러리 문제로 밝혀졌다.
* [MVN Repository](https://mvnrepository.com/artifact/org.apache.httpcomponents.client5/httpclient5/5.4.1)
* 위의 링크에서 Apache HttpClient 최신 버전으로 의존성을 추가해주었더니 해결이 가능했다.