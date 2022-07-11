package com.example.thongbaotrungtuyendh.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

import com.example.thongbaotrungtuyendh.entity.EExamSubject;
import com.example.thongbaotrungtuyendh.entity.MajorsRegister;
import com.example.thongbaotrungtuyendh.entity.Score;
import com.example.thongbaotrungtuyendh.entity.Student;
import com.example.thongbaotrungtuyendh.repository.ExamSubjectRepository;
import com.example.thongbaotrungtuyendh.repository.MajorsRegisterRepository;
import com.example.thongbaotrungtuyendh.repository.ScoreRepository;
import com.example.thongbaotrungtuyendh.repository.StudentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVHelper {
//    public static String TYPE = "text/csv";
    String[] HEADERs = { "CitizenIdentity", "FullName", "Email", "DOB", "Gender", "Address", "PriorityPoint",
                                "MajorsName", "ExamSubjectName1" , "Score1", "ExamSubjectName2" , "Score2",  "ExamSubjectName3" , "Score3"};

    @Autowired
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final MajorsRegisterRepository majorsRegisterRepository;
    private final ExamSubjectRepository examSubjectRepository;

    public CSVHelper(StudentRepository studentRepository, ScoreRepository scoreRepository, MajorsRegisterRepository majorsRegisterRepository, ExamSubjectRepository examSubjectRepository) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.majorsRegisterRepository = majorsRegisterRepository;
        this.examSubjectRepository = examSubjectRepository;
    }


//    public static boolean hasCSVFormat(MultipartFile file) {
//        if (!TYPE.equals(file.getContentType())) {
//            return false;
//        }
//        return true;
//    }

    public void csvToStudents(InputStream is, List<Student> students, List<Score> scoreSet) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                if(studentRepository.findStudentByCitizenIdentity(Long.parseLong(csvRecord.get("CitizenIdentity"))) == null) {
                    Student student = new Student(
                            Long.parseLong(csvRecord.get("CitizenIdentity")),
                            csvRecord.get("FullName"),
                            csvRecord.get("Email"),
                            LocalDate.parse(csvRecord.get("DOB")),
                            Boolean.parseBoolean(csvRecord.get("Gender")),
                            csvRecord.get("Address"),
                            Float.parseFloat(csvRecord.get("PriorityPoint"))
                    );
                    String majors = csvRecord.get("MajorsName");
                    MajorsRegister majorsRegister = majorsRegisterRepository.findMajorsRegisterByMajorsName(majors);
                    student.setMajorsRegister(majorsRegister);

                    Score score1 = new Score(Float.parseFloat(csvRecord.get("Score1")), student);
                    score1.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(csvRecord.get("ExamSubjectName1"))));
                    Score score2 = new Score(Float.parseFloat(csvRecord.get("Score2")), student);
                    score1.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(csvRecord.get("ExamSubjectName2"))));
                    Score score3 = new Score(Float.parseFloat(csvRecord.get("Score3")), student);
                    score1.setExamSubject(examSubjectRepository.findExamSubjectByEExamSubject(EExamSubject.valueOf(csvRecord.get("ExamSubjectName3"))));

                    float totalScore = score1.getTestScore() + score2.getTestScore() + score3.getTestScore() + student.getPriorityPoint();
                    student.setTotalScore(totalScore);

                    if (student.getTotalScore() >= student.getMajorsRegister().getBasicPoint()) {
                        student.setPass(true);
                    } else {
                        student.setPass(false);
                    }

                    scoreSet.add(score1);
                    scoreSet.add(score2);
                    scoreSet.add(score3);

                    students.add(student);
                } else {
                    Student studentUpdate = studentRepository.findStudentByCitizenIdentity(Long.parseLong(csvRecord.get("CitizenIdentity")));
                    studentUpdate.setFullName(csvRecord.get("FullName"));
                    studentUpdate.setGender(Boolean.parseBoolean(csvRecord.get("Gender")));
                    studentUpdate.setDob(LocalDate.parse(csvRecord.get("DOB")));
                    studentUpdate.setPriorityPoint(Float.parseFloat(csvRecord.get("PriorityPoint")));
                    studentUpdate.setEmail(csvRecord.get("Email"));
                    studentUpdate.setAddress(csvRecord.get("Address"));
                    students.add(studentUpdate);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
