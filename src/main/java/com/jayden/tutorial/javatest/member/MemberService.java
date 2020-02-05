package com.jayden.tutorial.javatest.member;

import com.jayden.tutorial.javatest.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
