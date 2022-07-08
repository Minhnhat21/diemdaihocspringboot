package com.example.thongbaotrungtuyendh.controller;

import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.exception.StudentNotFoundException;
import com.example.thongbaotrungtuyendh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "api/tracuu")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/search")
    public HttpEntity<Student> searchResultsExam(@RequestParam(value = "key", required = false) Long key) throws StudentNotFoundException {
        return new HttpEntity<>(studentService.studentResults(key));
    }

    @GetMapping("/rank")
    public HttpEntity<Integer> rankList(@RequestParam(value = "key", required = false) Long key) {
        return new HttpEntity<>(studentService.rankingOfStudent(key));
    }


}
