package com.example.thongbaotrungtuyendh.payload.request;



import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class ScoreRequest {
    private Float score1;
    private Float score2;
    private Float score3;

    private List<String> examSubject;

    public ScoreRequest() {
    }

    public ScoreRequest(Float score1, Float score2, Float score3, List<String> examSubject) {
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.examSubject = examSubject;
    }


}
