package com.example.thongbaotrungtuyendh.service;

import com.example.thongbaotrungtuyendh.entity.ExamSubject;
import com.example.thongbaotrungtuyendh.repository.ExamSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSubjectService {

    @Autowired
    private ExamSubjectRepository examSubjectRepository;

    public ExamSubjectService(ExamSubjectRepository examSubjectRepository) {
        this.examSubjectRepository = examSubjectRepository;
    }

    public List<ExamSubject> getAllExamSubject() {
        return examSubjectRepository.findAll();
    }
}
