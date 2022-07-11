package com.example.thongbaotrungtuyendh.controller;

import com.example.thongbaotrungtuyendh.entity.ExamSubject;
import com.example.thongbaotrungtuyendh.entity.MajorsRegister;
import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.exception.StudentNotFoundException;
import com.example.thongbaotrungtuyendh.payload.request.ScoreRequest;
import com.example.thongbaotrungtuyendh.payload.request.StudentRequest;
import com.example.thongbaotrungtuyendh.payload.response.PageInfo;
import com.example.thongbaotrungtuyendh.service.ExamSubjectService;
import com.example.thongbaotrungtuyendh.service.MajorRegisterService;
import com.example.thongbaotrungtuyendh.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "api/diemthi")
public class MainController {
    private final StudentService studentService;
    private final ExamSubjectService examSubjectService;
    private final MajorRegisterService majorRegisterService;

    @Autowired
    public MainController(StudentService studentService, ExamSubjectService examSubjectService, MajorRegisterService majorRegisterService) {
        this.studentService = studentService;
        this.examSubjectService = examSubjectService;
        this.majorRegisterService = majorRegisterService;
    }


    @GetMapping("/q")
    public HttpEntity<PageInfo<Student>> getStudents(@ApiParam("Page") @RequestParam(value = "page", required = false) Integer page,
                                                     @ApiParam("Limit") @RequestParam(value = "limit", required = false) Integer limit) {
        return new HttpEntity<>(studentService.getStudents(page,limit));
    }

    @ApiOperation("get details student by ID")
    @GetMapping("/{id}")
    public HttpEntity<Student> getStudentByID(@ApiParam("student ID") @PathVariable("id") Integer id) throws StudentNotFoundException {
        return new HttpEntity<>(studentService.getStudentById(id));
    }

    @GetMapping("/subject")
    @PreAuthorize("hasRole('USER')")
    public HttpEntity<List<ExamSubject>> getAll() {
        return new HttpEntity<>(examSubjectService.getAllExamSubject());
    }

    @GetMapping("/majors")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<List<MajorsRegister>> getAllMajors() {
        return new HttpEntity<>(majorRegisterService.getMajorsList());
    }

    //Register new student
    @PostMapping("/register")
    public HttpEntity<Student> registerStudent(@Valid @RequestBody StudentRequest studentRequest) {
        return new HttpEntity<>(studentService.addNewStudent(studentRequest));
    }


    //Update Student
    @PutMapping("/{id}")
    public HttpEntity<Student> updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) {
        return new HttpEntity<>(studentService.updateStudent(id,student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudentByID(@PathVariable("id") Integer id) {
        studentService.deleteStudentByID(id);
        return ResponseEntity.ok().build();
    }

    //Search
    @GetMapping("/search")
    public HttpEntity<PageInfo<Student>> searchResultsExam(@ApiParam("Page") @RequestParam(value = "page", required = false) Integer page,
                                                       @ApiParam("Limit") @RequestParam(value = "limit", required = false) Integer limit,
                                                        @RequestParam(value = "nameKey", required = false) String nameKey,
                                                       @RequestParam(value = "majorKey", required = false) String majorKey) {
        return new HttpEntity<>(studentService.studentsSearchList(page, limit,nameKey, majorKey));
    }

    //Search
    @GetMapping("/search/majors")
    public HttpEntity<List<Student>> searchResultsMajors(@RequestParam(value = "majorKey", required = false) String majorKey) {
        return new HttpEntity<>(studentService.studentsSearchListByMajorsName(majorKey));
    }


}
