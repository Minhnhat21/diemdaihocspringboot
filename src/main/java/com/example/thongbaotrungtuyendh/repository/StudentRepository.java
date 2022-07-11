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

    @Query(value = "select s from Student s where (:keyword is null or s.fullName like %:keyword%)")
    Page<Student> findStudentByFullName(Pageable pageable, String keyword);

    List<Student> findByOrderByTotalScoreDesc();

    @Query(value = "select s from Student s order by s.id")
    Page<Student> findAll(Pageable pageable);

    @Query(value = "select s from Student s where((:keyName is null or s.fullName like %:keyName%) " +
            "and (:keyMajors is null or :keyMajors = ''  or s.majorsRegister.majorsName = :keyMajors))")
    Page<Student> findItems(Pageable pageable, String keyName, String keyMajors);

    @Query(value = "select s from Student s where (s.majorsRegister.majorsName = ?1 or ?1 is null)")
    List<Student> findStudentByMajorsRegister(String keyMajors);
}
