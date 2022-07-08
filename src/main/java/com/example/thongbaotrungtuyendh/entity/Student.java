package com.example.thongbaotrungtuyendh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity{

    @NotNull
    @Column(name = "citizen_identity", unique = true)
    private Long citizenIdentity;

    @NotBlank
    @Column(name = "fullname")
    private  String fullName;

    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "priority_point")
    private float priorityPoint;
    @Column(name = "total_score")
    private float totalScore;

    private boolean isPass;

    @ManyToOne
    @JoinColumn(name = "majors_register_id")
    private MajorsRegister majorsRegister;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Score> scores;



    @Override
    public String toString() {
        return "Student{" +
                "citizenIdentity=" + citizenIdentity +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dto=" + dob +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", priorityPoint=" + priorityPoint +
                ", totalScore=" + totalScore +
                ", majorsRegister=" + majorsRegister +
                ", isPass=" + isPass +
                '}';
    }
}
