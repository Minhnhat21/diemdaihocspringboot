package com.example.thongbaotrungtuyendh.controller;

import java.util.List;

import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.helper.CSVHelper;
import com.example.thongbaotrungtuyendh.payload.response.CSVResponseMessage;
import com.example.thongbaotrungtuyendh.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/api/csv")
public class CSVController {
    @Autowired
    CSVService fileService;

    @PostMapping("/upload")
    public ResponseEntity<CSVResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        String TYPE = "text/csv";
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CSVResponseMessage("Vui lòng nhập file"));
        }
        if (TYPE.equals(file.getContentType())) {
            try {
                fileService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new CSVResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new CSVResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CSVResponseMessage(message));
    }
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllTutorials() {
        try {
            List<Student> students = fileService.getAllStudents();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
