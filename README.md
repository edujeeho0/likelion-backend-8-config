# Spring 설정 이모저모



## Logging

`System.out.println`은 Spring Boot 프로젝트를 실행한 터미널에 출력을 작성해주는 메서드이다.
실제로 서비스를 오픈한 상태에서는 서버를 24시간 바라보기 힘들다. 이때는 서버에 무슨일이 일어났었는지
로그를 남기는 것이 바람직하다.

Spring Boot에서는 Logback이라는 프레임워크를 로그를 남기는데 사용한다. XML을 이용해서 사용 가능하다.

### `Logger` 만들기

Slf4j는 Java Application의 로그 방법을 통일하기 위한 프레임워크이다. 이를 이용하면 
쉽게 로그를 남기기 위한 `Logger` 객체를 받을 수 있다. 또한 `@Slf4j` 어노테이션은
Logger 객체의 생성을 편하게 해주는 Lombok 어노테이션이다.

```java
//@Slf4j
@RestController
public class TestController {
    // @Slf4j 어노테이션을 추가하면 아래의 코드가 자동으로 추가된다.
    private static final Logger log
            = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/log")
    public void logTest() {
        // Logger가 가진 메서드들
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("A INFO Message");
        log.warn("A WARN Message");
        log.error("A ERROR Message");
    }
}
```

### Log Level

로그를 남길 때는 중요도를 나타내는 레벨을 적용할 수 있다.

- TRACE: 가장 낮은 단계의 로그로 아주 작은 변화의 로그를 남기는 레벨
- DEBUG: TRACE 보다는 조금 덜 구체적인, 개발자의 디버깅을 도와주는 로그를 남기는 레벨
- INFO: 어플리케이션이 실행중일 때 정보 제공의 목적으로 로그를 남기는 레벨
- WARN: 어플리케이션이 실행중일 때 안좋은 영향을 미칠 수도 있는 로그를 남기는 레벨
- ERROR: 어플리케이션이 정상적으로 동작하지 못한 상황에 대한 로그를 남기는 레벨

이 레벨은 어플리케이션의 실행 시 어느정도의 로그 레벨을 남길지를 정해줄 수 있으며,
설정한 레벨보다 더 중요한 레벨의 로그만 실제로 작성된다.
즉, 설정된 레벨이 `INFO`라면 `INFO`, `WARN`, `ERROR` 로그만 실제로 작성된다.

실행시의 로그 레벨만 간단하게 조정하고 싶다면 `application.yaml`로 설정 가능.

```yaml
logging:
  level:
    root: trace
```

### Logback 설정

`resources` 경로에 [설정파일](src/main/resources/logback-spring.xml)을 넣어두면 자동으로 적용된다.

- `<appender>`: 어떤 방식으로 로그를 남길건지를 제공하는 인터페이스 정의를 위한 요소
- `<layout>` & `<encoder>`: 출력되는 로그의 형식을 정의하기 위한 요소
- `<file>`: 파일의 형태로 로그를 남기는 `Appender`가 파일을 남길 곳을 정의하기 위한 요소
- `<rollingPolicy>`: `RollingFileAppender`가 생성된지 일정 시간이 지난 로그를 관리하는 방법을 정의하기 위한 요소
- `<root>` & `<logger>`: 어떤 `Logger` 객체에 대해서 어떤 `Appender`가 적용될지를 정의하는 요소.
  - `<root>`는 모든 `Logger`에 기본으로 적용된다.
  - `<logger>`는 `name` 속성에 정의된 패키지 및 하위 패키지에서 사용하는 `Logger`에 적용된다.

`application.yaml` 파일에서 사용할 설정 XML을 지정할수도 있다.

```yaml
logging:
  config: file:logback-spring.xml
```

## Profiles

어플리케이션이 실행되는 환경에 따라 다른 설정을 적용하고 싶을 때 사용할 수 있는 기능이다.

1. 사용하고 싶은 profile 이름을 정하고, (`dev`, `prod`, `test` 등)
2. 그 이름이 포함된 `application-{profile}.yaml` 파일을 만든다.

이후 실행할 때 어떤 profile로 실행할지 결정하면, 실제 실행 시 사용되는 `application.yaml`을 결정할 수 있다.

### `spring.profiles.default`

실행하는 단계에서 어떤 profile을 실행할지 정하지 않으면 사용할 profile을 나타내기 위한 설정.
아래 내용을 `application.yaml`에 정의하고,

```yaml
spring:
  profiles:
    default: dev
```

아래 내용을 `application-dev.yaml`에 정의한다.

```yaml
spring:
  datasource:
    url: jdbc:sqlite:db.sqlite
    driver-class-name: org.sqlite.JDBC
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
logging:
  config: file:logback-spring.xml
  level:
    root: ${LOG_LEVEL:info}  # 환경변수 활용 가능
```

이렇게 설정하고 실행하면 `application-dev.yaml`의 설정을 기본으로, 설정하지 않은 것 중 `application.yaml`에
있는것을 가져다 사용한다.

### `spring.profiles.active`

Spring Boot 프로젝트는 일반적으로 JAR 파일의 형태로 만들어지며, JAR 파일은 `java` 명령어로 실행할 수 있다.
이때 `spring.profiles.active` 설정을 전달하면 실행할 profile을 결정할 수 있다.

```
java -Dspring.profiles.active=test -jar build/libs/contents-0.0.1-SNAPSHOT.jar
```

### Profile에 따른 Bean 생성

`@Profile` 어노테이션을 사용하면 특정 profile에 대해서만 Bean 객체로 만들어지게 조정할 수 있다.
```java
@Controller
// 개발 환경(dev)에서만 사용하고 싶은 Controller
@Profile("dev")
public class MonitorController {
    // ...
}
```
