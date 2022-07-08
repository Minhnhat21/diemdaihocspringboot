package com.example.thongbaotrungtuyendh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

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


}
