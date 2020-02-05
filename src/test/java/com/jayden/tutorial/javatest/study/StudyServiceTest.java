package com.jayden.tutorial.javatest.study;

import com.jayden.tutorial.javatest.domain.Member;
import com.jayden.tutorial.javatest.domain.Study;
import com.jayden.tutorial.javatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;

    @Mock StudyRepository studyRepository;

    @Test
    @DisplayName("Mock 객체 주입을 통한 StudyService 인스턴스 생성 테스트")
    void createStudyServiceInstance() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock 객체 Stubbing 연습 문제")
    void stubbingExampleTest() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // stubbing
        Member member = new Member(1L, "test@test.com");
        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member.getId(), study.getOwnerId());

        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());

        // 순서 검증
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }
}