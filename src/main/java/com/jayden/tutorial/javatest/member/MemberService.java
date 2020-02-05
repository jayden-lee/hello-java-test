package com.jayden.tutorial.javatest.member;

import com.jayden.tutorial.javatest.domain.Member;
import com.jayden.tutorial.javatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
