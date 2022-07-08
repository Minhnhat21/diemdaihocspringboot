package com.example.thongbaotrungtuyendh.repository;

import com.example.thongbaotrungtuyendh.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "select s from Student s where(s.citizenIdentity = :citizenID)")
    Student findStudentByCitizenIdentity(Long citizenID);

    @Query(value = "select s from Student s where (:keyword is null or s.fullName like %:keyword%  or s.email like %:keyword%)")
    List<Student> findStudentByFullNameAndEmail(String keyword);

    List<Student> findByOrderByTotalScoreDesc();

    @Query(value = "select s from Student s order by s.id")
    Page<Student> findAll(Pageable pageable);
}
