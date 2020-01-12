package com.jayden.tutorial.javatest;

import org.junit.jupiter.api.*;

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
    void create_1() {
        System.out.println("create_1");
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
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

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }
}