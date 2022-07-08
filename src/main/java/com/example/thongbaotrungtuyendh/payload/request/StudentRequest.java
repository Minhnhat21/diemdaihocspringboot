package com.example.thongbaotrungtuyendh.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotNull
    private Long citizenIdentity;

    @NotBlank
    private  String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull
    private LocalDate dob;

    @NotNull
    private boolean gender;

    @NotNull
    private String address;

    @NotNull
    private float priorityPoint;

    @NotBlank
    private String majorsName;

    private Float score1;
    private Float score2;
    private Float score3;

    private List<String> examSubject;

}
