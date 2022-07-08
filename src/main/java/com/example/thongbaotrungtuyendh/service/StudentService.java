package com.example.thongbaotrungtuyendh.service;

import com.example.thongbaotrungtuyendh.entity.EExamSubject;
import com.example.thongbaotrungtuyendh.entity.Score;
import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.exception.StudentNotFoundException;
import com.example.thongbaotrungtuyendh.payload.request.ScoreRequest;
import com.example.thongbaotrungtuyendh.payload.request.StudentRequest;
import com.example.thongbaotrungtuyendh.payload.response.PageInfo;
import com.example.thongbaotrungtuyendh.repository.ExamSubjectRepository;
import com.example.thongbaotrungtuyendh.repository.MajorsRegisterRepository;
import com.example.thongbaotrungtuyendh.repository.ScoreRepository;
import com.example.thongbaotrungtuyendh.repository.StudentRepository;
import com.example.thongbaotrungtuyendh.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.util.HashSet;
import java.util.Set;

import java.util.List;

@Service

public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final MajorsRegisterRepository majorsRegisterRepository;
    private final ExamSubjectRepository examSubjectRepository;

    public StudentService(StudentRepository studentRepository, ScoreRepository scoreRepository, MajorsRegisterRepository majorsRegisterRepository, ExamSubjectRepository examSubjectRepository) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.majorsRegisterRepository = majorsRegisterRepository;
        this.examSubjectRepository = examSubjectRepository;
    }


    public PageInfo<Student> getStudents(Integer page, Integer limit) {
        Pageable pageable = AppUtils.buildPageRequest(page, limit);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return AppUtils.buildPageResponse(studentPage);
    }

    public Student getStudentById(Integer id) throws StudentNotFoundException {
        return studentRepository.findById(id).orElseThrow(()
                -> new StudentNotFoundException("Not found", "Student with " + id + "not found"));
    }

    public Student addNewStudent(StudentRequest  studentRequest) {
        Student newStudent = new Student();
        newStudent.setCitizenIdentity(studentRequest.getCitizenIdentity());
        newStudent.setFullName(studentRequest.getFullName());
        newStudent.setAddress(studentRequest.getAddress());
        newStudent.setEmail(studentRequest.getEmail());
        newStudent.setDob(studentRequest.getDob());
        newStudent.setPriorityPoint(studentRequest.getPriorityPoint());
        newStudent.setGender(studentRequest.isGender());
        String major = studentRequest.getMajorsName();
        newStudent.setMajorsRegister(majorsRegisterRepository.findMajorsRegisterByMajorsName(major));
        studentRepository.save(newStudent);
        inputExamScore(newStudent, studentRequest.getScore1(), studentRequest.getScore2(), studentRequest.getScore3(), studentRequest.getExamSubject());
        return studentRepository.save(newStudent);
    }

    public void inputExamScore(Student studentScore, Float score_1,Float score_2,Float score_3, List<String> subjectStr) {

        Score score1 = new Score();
        Score score2 = new Score();
        Score score3 = new Score();

        Set<Score> subjectList = new HashSet<>();

        score1.setStudent(studentScore);
        score1.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(subjectStr.get(0))));
        score1.setTestScore(score_1);

        score2.setStudent(studentScore);
        score2.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(subjectStr.get(1))));
        score2.setTestScore(score_2);

        score3.setStudent(studentScore);
        score3.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(subjectStr.get(2))));
        score3.setTestScore(score_3);

        studentScore.setTotalScore(score1.getTestScore() + score2.getTestScore() + score3.getTestScore() + studentScore.getPriorityPoint());

        if(studentScore.getTotalScore() >= studentScore.getMajorsRegister().getBasicPoint()) {
            studentScore.setPass(true);
        } else {
            studentScore.setPass(false);
        }

        subjectList.add(score1);
        subjectList.add(score2);
        subjectList.add(score3);

        scoreRepository.saveAll(subjectList);
        studentRepository.save(studentScore);
    }

    public Student updateStudent(Integer id, Student student) {
        Student studentUpdate = studentRepository.findById(id).get();
        studentUpdate.setFullName(student.getFullName());
        studentUpdate.setGender(student.isGender());
        studentUpdate.setDob(student.getDob());
        studentUpdate.setPriorityPoint(student.getPriorityPoint());
        studentUpdate.setEmail(student.getEmail());
        return studentRepository.save(studentUpdate);
    }

    //Delete student by ID
    @Transactional
    public void deleteStudentByID(Integer id) {
        studentRepository.deleteById(id);
    }
    //Search student by fullname and email
    public List<Student> studentsSearchList(String keyword) throws StudentNotFoundException {
        if(studentRepository.findStudentByFullNameAndEmail(keyword) != null) {
            return studentRepository.findStudentByFullNameAndEmail(keyword);
        } else
            throw new StudentNotFoundException("Not found","Student not found", HttpStatus.NOT_FOUND);
    }
    //Search student by
    public Student studentResults(Long  citizenIdentity) throws StudentNotFoundException {
        if(studentRepository.findStudentByCitizenIdentity(citizenIdentity) != null) {
            return studentRepository.findStudentByCitizenIdentity(citizenIdentity);
        } else
            throw new StudentNotFoundException("Not found","Student not found", HttpStatus.NOT_FOUND);

    }

    public int rankingOfStudent(Long citizenIdentity) {
        List<Student> sortListStudent = studentRepository.findByOrderByTotalScoreDesc();
        int index = sortListStudent.indexOf(studentRepository.findStudentByCitizenIdentity(citizenIdentity));
        return index + 1;
    }



}
