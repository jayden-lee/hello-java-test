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

## 참고자료
- 인프런 "더 자바, 애플리케이션을 테스트하는 다양한 방법" 강의
- https://junit.org/junit5/docs/current/user-guide