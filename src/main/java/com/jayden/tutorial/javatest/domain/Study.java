package com.jayden.tutorial.javatest.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private StudyStatus status = StudyStatus.DRAFT;

    @Column
    private int limitCount = 10;

    @Column
    private String name;

    @Column
    private LocalDateTime openedDateTime;

    @Column
    private Long ownerId;

    public Study(int limitCount, String name) {
        this.limitCount = limitCount;
        this.name = name;
    }

    public Study(int limitCount) {
        if (limitCount < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다");
        }
        this.limitCount = limitCount;
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}
