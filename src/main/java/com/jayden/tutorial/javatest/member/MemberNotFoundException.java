package com.jayden.tutorial.javatest.member;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("Member Not Found");
    }
}
