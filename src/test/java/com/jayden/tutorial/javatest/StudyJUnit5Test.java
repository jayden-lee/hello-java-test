package com.jayden.tutorial.javatest;

import com.jayden.tutorial.javatest.domain.Study;
import com.jayden.tutorial.javatest.domain.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Study JUnit5 Test Case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyJUnit5Test {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Before Each");
    }

    @Test
    @DisplayName("create_1")
    @EnabledOnOs({OS.MAC, OS.WINDOWS, OS.LINUX})
    @Tag("local")
    void create_1() {
        System.out.println("create_1");
        Study study = new Study(10);

        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "Study 객체를 처음 만들면 상태값이 DRAFT여야 한다."),
            () -> assertTrue(study.getLimitCount() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );
    }

    @Test
    @Tag("alpha")
    void create_2() {
        System.out.println("create_2");
        Study study = new Study(10);
        assertNotNull(study);
    }

    @Test
    @DisplayName("create_3")
    @Disabled
    void create_3() {
        System.out.println("create_3");
        Study study = new Study(10);
        assertNotNull(study);
    }

    @Test
    @DisplayName("create 4")
    void create_4() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야 한다", exception.getMessage());
    }

    @Test
    @DisplayName("create 5")
    void create_5() {
        assertTimeoutPreemptively(Duration.ofMillis(5000), () -> {
            new Study(10);
            Thread.sleep(1000);
        });
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 5, name = "{displayName} :: repetition {currentRepetition} of {totalRepetitions}")
    void repeated_create_6(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
        System.out.println("test");
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} value={0}")
    @ValueSource(strings = {"A", "B", "C"})
    @NullAndEmptySource
    void parameterizedTest(String value) {
        System.out.println(value);
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }
}