# Java Test Study Repo
> 인프런 자바 테스트 강좌를 학습하고 정리한 내용입니다

## JUnit
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
1. @ExtendWith
    ```java
   @ExtendWith(CustomExtension.class)
   class CustomTest {
   }
    ```

2. @RegisterExtension
    ```java
    class CustomTest {
           @RegisterExtendsion
           static CustomExtension customExtension = new CustomExtension();
    }
    ```
   
3. ServiceLoader
    ```properties
    # Extension 자동 감지 기능
    junit.jupiter.extensions.autodetection.enabled = true
    ```

## 참고자료
- 인프런 "더 자바, 애플리케이션을 테스트하는 다양한 방법" 강의
- https://junit.org/junit5/docs/current/user-guide