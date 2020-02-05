package com.jayden.tutorial.javatest;

import org.junit.Before;
import org.junit.Test;

public class StudyJUnit4Test {

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void createTest_1() {
        System.out.println("Create Test 1");
    }

    @Test
    public void createTest_2() {
        System.out.println("Create Test 2");
    }
}
