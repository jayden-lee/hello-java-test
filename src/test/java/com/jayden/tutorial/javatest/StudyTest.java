package com.jayden.tutorial.javatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void create1() {
        System.out.println("create1");
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void create2() {
        System.out.println("create2");
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @Disabled
    void create3() {
        System.out.println("create3");
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