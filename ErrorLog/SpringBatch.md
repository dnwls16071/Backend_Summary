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