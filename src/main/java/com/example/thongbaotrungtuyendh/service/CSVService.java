package com.example.thongbaotrungtuyendh.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.thongbaotrungtuyendh.entity.Score;
import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.helper.CSVHelper;
import com.example.thongbaotrungtuyendh.repository.ExamSubjectRepository;
import com.example.thongbaotrungtuyendh.repository.MajorsRegisterRepository;
import com.example.thongbaotrungtuyendh.repository.ScoreRepository;
import com.example.thongbaotrungtuyendh.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;

    public final CSVHelper csvHelper;

    public CSVService(StudentRepository studentRepository, ScoreRepository scoreRepository, MajorsRegisterRepository majorsRegisterRepository, ExamSubjectRepository examSubjectRepository, CSVHelper csvHelper) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.csvHelper = csvHelper;
    }

    public void save(MultipartFile file) throws IOException {
        try {
            InputStream is = file.getInputStream();
            List<Student> students = new ArrayList<Student>();
            List<Score> scoreSet = new ArrayList<Score>();

            csvHelper.csvToStudents(is, students, scoreSet);

            studentRepository.saveAll(students);
            scoreRepository.saveAll(scoreSet);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }


    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
