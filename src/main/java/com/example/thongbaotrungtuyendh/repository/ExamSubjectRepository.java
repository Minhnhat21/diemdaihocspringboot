package com.example.thongbaotrungtuyendh.repository;

import com.example.thongbaotrungtuyendh.entity.EExamSubject;
import com.example.thongbaotrungtuyendh.entity.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Integer> {
    @Query(value = "select s from ExamSubject s where(:eExamSubject = s.eExamSubject)")
    ExamSubject findExamSubjectByEExamSubject(EExamSubject eExamSubject);
}
