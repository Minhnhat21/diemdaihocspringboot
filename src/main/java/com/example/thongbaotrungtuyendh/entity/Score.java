package com.example.thongbaotrungtuyendh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "score")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Score extends BaseEntity{


    private Float testScore;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "exam_subject_id")
    private ExamSubject examSubject;

    public Score(Float testScore, Student student) {
        this.testScore = testScore;
        this.student = student;
    }
}
