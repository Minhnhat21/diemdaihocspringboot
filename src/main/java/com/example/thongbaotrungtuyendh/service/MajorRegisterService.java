package com.example.thongbaotrungtuyendh.service;

import com.example.thongbaotrungtuyendh.entity.MajorsRegister;
import com.example.thongbaotrungtuyendh.repository.MajorsRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorRegisterService {
    @Autowired
    private final MajorsRegisterRepository majorsRegisterRepository;

    public MajorRegisterService(MajorsRegisterRepository majorsRegisterRepository) {
        this.majorsRegisterRepository = majorsRegisterRepository;
    }

    public List<MajorsRegister> getMajorsList() {
        return majorsRegisterRepository.findAll();
    }

    public MajorsRegister getMajorsListByID(Integer id) {
        return majorsRegisterRepository.findById(id).get();
    }
}
