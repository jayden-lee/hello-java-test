package com.jayden.tutorial.javatest.study;

import com.jayden.tutorial.javatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    @DisplayName("Mock 객체 주입을 통한 StudyService 인스턴스 생성 테스트")
    void createStudyServiceInstance(@Mock MemberService memberService,
                                    @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}