package com.jayden.tutorial.javatest.study;

import com.jayden.tutorial.javatest.domain.Member;
import com.jayden.tutorial.javatest.domain.Study;
import com.jayden.tutorial.javatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> optionalMember = memberService.findById(memberId);
        Member member = optionalMember
            .orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId));

        study.setOwnerId(member.getId());

        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member);

        return newStudy;
    }
}
