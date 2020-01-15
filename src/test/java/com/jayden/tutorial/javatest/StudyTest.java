package com.jayden.tutorial.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Study Test Case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

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
        Study study = new Study();

        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "Study 객체를 처음 만들면 상태값이 DRAFT여야 한다."),
            () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );
    }

    @Test
    @Tag("alpha")
    void create_2() {
        System.out.println("create_2");
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @DisplayName("create_3")
    @Disabled
    void create_3() {
        System.out.println("create_3");
        Study study = new Study();
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
        assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
            new Study(10);
            Thread.sleep(1000);
        });
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