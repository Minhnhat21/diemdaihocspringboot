package com.example.thongbaotrungtuyendh.repository;

import com.example.thongbaotrungtuyendh.entity.MajorsRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorsRegisterRepository extends JpaRepository<MajorsRegister,Integer> {
    @Query(value = "select m from MajorsRegister m where(:majorsName like m.majorsName)")
    MajorsRegister findMajorsRegisterByMajorsName(String majorsName);
}
