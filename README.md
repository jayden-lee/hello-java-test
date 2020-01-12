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