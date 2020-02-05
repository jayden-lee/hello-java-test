# Java Test Study Repo
> 인프런 자바 테스트 강좌를 학습하고 정리한 내용입니다

# JUnit
- 자바 개발자가 가장 많이 사용하는 단위 테스팅 프레임워크
- 2017년 10월 JUni5가 공개 되었고, 기존 프로젝트에서는 JUnit4를 사용했었음
- 최근 Spring Boot 2.2+ 버전에서는 기본 JUnit 버전을 JUnit5로 채택함

## JUnit5
> Junit5는 여러 모듈로 구성되어 있음
1. Platform: 테스트를 실행하는 런쳐 제공
2. Jupiter: TestEngine API 구현체로 JUnit5를 제공
3. Vintage: JUnit3, 4를 지원하는 TestEngine API 구현체

## JUnit5 기본 어노테이션
- @Test
- @BeforeAll / @AfterAll
- @BeforeEach / @AfterEach
- @Disabled

## 테스트 이름 표시 방법
> 테스트 이름 기본 표기 전략은 메서드 이름으로 설정된다

1. @DisplayNameGeneration
    ```java
    @DisplayName("Custom Test Case")
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class CustomTest {
    
        @Test
        void custom_test() {
            // Test Code
        }
    }
    ```

2. @DisplayName
    ```java
   @DisplayName("Custom Test Case")
   class CustomTest {
       
       @Test
       @DisplayName("Test Method Name")
       void custom_test() {
           // Test Code
       }
   }
    ```
## Assertion
- assertEquals(expected, actual)
- assertNotNull(actual)
- assertTrue(boolean)
- assertAll(executables)
- assertThrows(expectedType, executable)
- assertTimeout(duration, executable)
- ...

## 조건에 따라 테스트 실행하기
- assumeTrue(boolean)
- assumingThat(boolean, executable)
- @EnabledOnOs({OS.MAC, OS.WINDOWS, OS.LINUX})
- @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
- @EnabledIfEnvironmentVariable(named, matches)
- ...

## 태깅과 필터링

### 태깅
> 테스트를 태깅함으로써 프로파일에 따라 테스트 그룹화 할 수 있다

```java
class CustomTest {
    @Test
    @Tag("local")
    void testMethod1() {
        // Test Code
    }
    
    @Test
    @Tag("alpha")
    void testMethod2() {
        // Test Code
    }
}
```

### IntelliJ 태깅 설정

<img width="1133" alt="JUnit-Tag-Configuration" src="https://user-images.githubusercontent.com/43853352/72307556-0a236780-36be-11ea-99ca-4d8aa7af9a17.png">

### Maven Profile 설정

```xml
<profiles>
    <profile>
        <id>default</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <groups>local</groups>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
    <profile>
        <id>alpha</id>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <groups>local | alpha</groups>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

## 테스트 반복하기

### RepeatedTest
```java
@DisplayName("반복 테스트")
@RepeatedTest(value = 5, name = "{displayName} :: repetition {currentRepetition} of {totalRepetitions}")
void repeatedTest(RepetitionInfo repetitionInfo) {
    // Test Code
}
```

### ParameterizedTest 
```java
@DisplayName("파라미터 테스트")
@ParameterizedTest(name = "{index} {displayName} value={0}")
@ValueSource(strings = {"A", "B", "C"})
@NullAndEmptySource
void parameterizedTest(String value) {
    // Test Code
}
```

- 하나의 파라미터 값을 변경해서 아규먼트에 넘겨줄 때는 <code>@ConvertWith</code> 어노테이션에 <code>SimpleArgumentConverter</code>를
구현한 클래스를 명시한다
```java
@ParameterizedTest
@ValueSource(strings = {"A", "B", "C"})
void test(@ConvertWith(CustomArgumentConverter.class) Study study) {
      // Test Code
}
```
  
- 2개 이상의 아규먼트를 처리할 때는 <code>ArgumentAccessor</code>를 사용하는 방법과 <code>ArgumentsAggregator</code> 인터페이스를 구현한 클래스와
<code>AggregateWith</code> 어노테이션을 함께 사용하는 방법이 있다
```java
@ParameterizedTest
@CsvSource({"10, 이름1", "20, 이름2"})
void test(@AggregateWith(CustomAggregator.class) Study study) {
      // Test Code
}
```

## 테스트 인스턴스
> 각 테스트마다 의존성을 없애기 위해서 기본 전략으로 테스트 메서드마다 테스트 인스턴스를 생성한다

JUnit5부터는 <code>@TestInstance</code> 어노테이션으로 테스트 인스턴스의 전략을 설정할 수 있다

- TestInstance.Lifecycle.PER_CLASS
- TestInstance.Lifecycle.PER_METHOD

## 테스트 순서
테스트 메서드를 원하는 순서대로 실행하기를 기대한다면 두 가지 설정을 해줘야 한다. 먼저 테스트 클래스 인스턴스를
하나만 생성하도록 <code>@TestInstance</code> 어노테이션을 설정하고, 다음으로 <code>@TestMethodOrder</code> 어노테이션을
설정한다

### MethodOrder 구현체
- Alphanumeric
- OrderAnnotation
- Random

> OrderAnnotation 구현체를 사용할 때 Order 어노테이션을 설정하는데, Order 값이 낮을수록 더 우선순위를 갖고 먼저 실행된다

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrder.OrderAnnotation.class)
class CustomTest {
    
    @Order(1)
    void test1() {
        // Test Code
    }

    @Order(2)
    void test1() {
        // Test Code
    }
}
```

## JUnit Platform Properties
> <code>src/test/resources/junit-platform.properties</code> 파일을 생성하고 옵션을 설정한다

```properties
# 테스트 라이프사이클 지정
junit.jupiter.testinstance.lifecycle.default = per_class

# 확장팩 자동 감지 기능 설정
junit.jupiter.extensions.autodetection.enabled = true

# 특정 기능 무시하고 실행하기 설정
junit.jupiter.conditions.deactivate = org.junit.*DisabledCondition

# 테스트 이름 표기 전략 설정
junit.jupiter.displayname.generator.default = \
    org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores
```

## Extension

### Extension 등록 방법
1.@ExtendWith
```java
@ExtendWith(CustomExtension.class)
class CustomTest {
}
```

2.@RegisterExtension
```java
class CustomTest {
       @RegisterExtendsion
       static CustomExtension customExtension = new CustomExtension();
}
```
   
3.ServiceLoader
```properties
# Extension 자동 감지 기능
junit.jupiter.extensions.autodetection.enabled = true
```

<hr/>

# [Mockito](https://site.mockito.org/)
- Mock: 실제 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체
- Mockito: Mock 객체를 쉽게 만들고 관리 및 검증할 수 있는 방법을 제

## Mockito 모듈 추가
Spring Boot 2.2 이상 버전의 프로젝트를 생성하면, <code>spring-boot-starter-test</code> 모듈이 Mockito 모듈을 추가해준다. 직접 추가하기 위해서는
아래 디펜던시를 추가해준다.

```xml
<dependencies>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>3.2.4</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>3.2.4</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Mock 객체 생성

### Mockito 클래스의 mock 메서드
```java
MemberService memberService = Mockito.mock(MemberService.class);
```

### 애노테이션
반드시 @Mock 애노테이션을 처리할 <code>MockitoExtension</code>을 추가해야 한다.

```java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;
}
```

특정 메서드에서만 Mock 객체를 사용할 경우에는 파라미터에 추가해서 메서드 범위에서만 사용하도록 설정할 수 있다.

```java
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createStudyServiceInstance(@Mock MemberService memberService,
                                    @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }
}
```

## Mock 객체 Stubbing
- 특정 매개변수가 주어진 경우에 원하는 결과값이 반환되거나 에러가 발생하게 조작할 수 있다.
```java
Member member = new Member(1L, "jayden@chequer.io");

when(memberService.findById(1L)).thenReturn(Optional.of(member));

assertEquals("jayden@chequer.io",memberService.findById(1L).get().getEmail());
```

- 동일한 매개변수로 여러번 호출할 때, 각각 다르게 행동할 수 있도록 조작할 수 있다.
```java
when(memberService.findById(any()))
    .thenReturn(Optional.of(member)) // 첫 번째 호출에는 Optional에 감싸진 member 객체 반환 
    .thenThrow(new RuntimeException()) // 두 번째 호출에는 에러 발생
    .thenReturn(Optional.empty()); // 세 번째는 Optional 빈 값을 반환
```

- Void 메서드에서 특정 매개변수를 받게 되면 에러를 발생 시킬 수 있다.
```java
doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

assertThrows(IllegalArgumentException.class,() -> {
    memberService.validate(1L);
});
```

## References
- 인프런 "더 자바, 애플리케이션을 테스트하는 다양한 방법" 강의
- [JUnit5 User Guide](https://junit.org/junit5/docs/current/user-guide)
- [Mockito Core Doc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)