### ✅ 액츄에이터

##### 액츄에이터 시작

* 액츄에이터 실행 여부를 확인하고 싶은 경우라면 아래 URL을 입력하면 된다.

> http://localhost:8080/actuator

* 액츄에이터 기능을 웹에 추가시키고 싶다면 `application.yml`에 다음과 같은 설정을 추가해주면 된다.

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

* 액츄에이터가 제공하는 기능 하나하나를 **엔드포인트**라고 한다. `health`는 헬스 정보, `beans`는 스프링 컨테이너에 등록된 빈을 보여준다.
* 각각의 엔드포인트는 `/actuator/{엔드포인트명}`과 같은 형식으로 접근할 수 있다.

##### 엔드포인트 설정

* 엔드포인트를 사용하려면 다음과 같은 두 가지 설정을 해야 한다.
  * 엔드포인트 활성화
  * 엔드포인트 노출
* `application.yml`에 모든 엔드포인트를 웹에 노출시키면서 활성화하려면 아래와 같이 설정하면 된다.

```yaml
management:
  endpoint:
    shutdown:
      enabled: true
  endpoints:
    web:
      exposure:
        include: "*"
```

##### 다양한 엔드포인트

* 각각의 엔드포인트를 통해 개발자는 애플리케이션 내부의 수많은 기능을 관리하고 모니터링 할 수 있다.
* 스프링 부트가 기본으로 제공하는 다양한 엔드포인트에 대해 알아보자.
* 엔드포인틈 목록
  * `beans` : 스프링 컨테이너에 등록된 스프링 빈을 보여준다.
  * `conditions` : `condition`을 통해 빈을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유를 표시한다.
  * `configprops` : `@ConfigurationProperties`를 보여준다.
  * `env` : `Environment` 정보를 보여준다.
  * `health` : 애플리케이션 헬스 정보를 보여준다.
  * `httpexchanges` : HTTP 호출 응답 정보를 보여준다.
  * `info` : 애플리케이션 정보를 보여준다.
  * `loggers` : 애플리케이션 로거 설정을 보여주고 변경도 할 수 있다.
  * `metrics` : 애플리케이션 메트릭 정보를 보여준다.
  * `mappings` : `@RequestMapping` 정보를 보여준다.
  * `threaddump` : 쓰레드 덤프를 실행해서 보여준다.
  * `shutdown` : 애플리케이션을 종료한다. 이 기능은 기본적으로 비활성화 되어 있다.

[전체 엔드포인트 정보](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html)

##### 헬스 정보

* 헬스 정보를 사용하면 애플리케이션에 문제가 발생했을 때 문제를 빠르게 인지할 수 있다.
* 헬스 정보에는 단순히 애플리케이션이 요청에 응답을 할 수 있는지 판단하는 것을 넘어서 애플리케이션이 사용하는 데이터베이스가 응답하는지, 디스크 사용량에는 문제가 없는지와 같은 다양한 정보들을 포함해서 만들어진다.
* 헬스 정보를 더 자세히 보려면 `application.yml` 설정에 다음과 같은 옵션을 추가하면 된다.
* 헬스 컴포넌트 중에 하나라도 문제가 있으면 전체 상태는 `Down` 상태가 된다.

```yaml
management:
  endpoint:
    health:
      # show-details: always
      show-components: always
    shutdown:
      enabled: true
  endpoints:
    web:
      exposure:
        include: "*"
```

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 994662584320,
        "free": 899194114048,
        "threshold": 10485760,
        "path": "/Users/jwj/Desktop/Backend_Study_TIL/actuator/.",
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    },
    "ssl": {
      "status": "UP",
      "details": {
        "validChains": [],
        "invalidChains": []
      }
    }
  }
}
```

##### 애플리케이션 정보

* `info` 엔드포인트는 애플리케이션의 기본 정보를 노출한다.
  * `java` : 자바 런타임 정보
  * `os` : OS 정보
  * `env` : `Environment`에서 `info.`으로 시작하는 정보
  * `build` : 빌드 정보
  * `git` : git 정보, git.properties 파일이 필요하다.

```yaml
management:
  
  # ...
  
  info:
    java:
      enabled: true
    os:
      enabled: true
```
```json
{
  "java": {
    "version": "17.0.13",
    "vendor": {
      "name": "Homebrew",
      "version": "Homebrew"
    },
    "runtime": {
      "name": "OpenJDK Runtime Environment",
      "version": "17.0.13+0"
    },
    "jvm": {
      "name": "OpenJDK 64-Bit Server VM",
      "vendor": "Homebrew",
      "version": "17.0.13+0"
    }
  },
  "os": {
    "name": "Mac OS X",
    "version": "14.4",
    "arch": "aarch64"
  }
}
```

* build 정보에 대해선 수동으로 추가하는 것이 아니라 `build.gradle`에서 추가적으로 설정을 해줘야 한다.

```gradle
springBoot {
	buildInfo()
}
```

* `build/resources/main` 하위에 보게 되면 `META-INF` 디렉터리 하위에 `build-info.properties`가 위치하게 된다.
* 실행 결과를 통해서 애플리케이션의 기본 정보와 버전 그리고 빌드된 시간을 확인할 수 있다.
* git 정보를 추가하기 위해서 해당 프로젝트가 깃에 의해 관리가 되어있어야 가능하다. 정보를 보고싶다면 `build.gradle`에 아래 플러그인을 추가한다.

```gradle
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.4.0'
	id 'io.spring.dependency-management' version '1.1.6'
	id "com.gorylenko.gradle-git-properties" version "2.4.1"	// git info
}
```

##### 로거

* 제공되는 로그 레벨  
  * TRACE
  * DEBUG
  * INFO
  * WARN
  * ERROR

##### HTTP 요청 응답 기록

* HTTP 요청과 응답의 과거 기록을 확인하고 싶다면 `httpexchanges` 엔드포인트를 사용하면 된다.
* `HttpExchangeRepository` 인터페이스 구현체를 빈으로 등록하면 `httpexchanges` 엔드포인트를 사용할 수 있다.
* 스프링 부트는 기본적으로 InMemoryHttpExchangeRepository 구현체를 제공한다.
* 해당 빈을 등록하지 않으면 `httpexchanges` 엔드포인트가 활성화되지 않는다.

```java
@SpringBootApplication
public class ActuatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ActuatorApplication.class, args);
  }


  @Bean
  public InMemoryHttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}
```

##### 액츄에이터와 보안

* 액츄에이터가 제공하는 기능들은 애플리케이션의 내부 정보를 많이 노출한다. 그래서 외부 인터넷 망이 공개된 곳에 액츄에이터의 엔드포인트를 공개하는 것은 보안상 좋은 방법이 아니다.
* 액츄에이터의 엔드포인트들은 외부 인터넷에서 접근이 불가능하게 막고, 내부에서만 접근 가능한 내부망을 사용하는 것이 안전하다.
* 액츄에이터 포트를 지정
  * 외부 인터넷 망을 통해 8080번 포트에만 접근할 수 있고, 다른 포트는 내부망에서만 접근할 수 있다면 액츄에이터에 다른 포트를 설정하면 된다.
  * 액츄에이터 기능을 애플리케이션 서버와는 다른 포트에서 실행하려면 다음과 같이 설정하면 된다.
```yaml
management:
  server:
    port: 9292
```
* 액츄에이터 URL 경로에 인증 설정
  * 포트를 분리하는 것이 어렵고 어쩔 수 없이 외부 인터넷 망을 통해서 접근해야 한다면 `/actuator` 경로에 서블릿 필터, 스프링 인터셉터 또는 스프링 시큐리티를 통해서 인증된 사용자만 접근 가능하도록 추가 개발이 필요하다.

