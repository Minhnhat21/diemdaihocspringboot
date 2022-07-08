package com.example.thongbaotrungtuyendh.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "majors_register")
public class MajorsRegister extends BaseEntity{

    private String majorsName;

    private Float basicPoint;

    public MajorsRegister(){}

    public Float getBasicPoint() {
        return basicPoint;
    }

    public void setBasicPoint(Float basicPoint) {
        this.basicPoint = basicPoint;
    }

    public String getMajorsName() {
        return majorsName;
    }

    public void setMajorsName(String majorsName) {
        this.majorsName = majorsName;
    }
}
