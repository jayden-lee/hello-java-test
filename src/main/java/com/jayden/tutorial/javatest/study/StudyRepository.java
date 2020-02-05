package com.jayden.tutorial.javatest.study;

import com.jayden.tutorial.javatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
