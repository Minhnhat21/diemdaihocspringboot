package com.example.thongbaotrungtuyendh.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StudentBadRequestException extends Exception {


    private String error;
    private String message;
    private boolean isJson;
    @JsonIgnore
    private boolean isPrintStackTrace;

    public StudentBadRequestException() {
        super();
    }

    public StudentBadRequestException(String message, boolean isJson) {
        super(message);
        this.message = message;
        this.isJson = isJson;
    }


    public StudentBadRequestException(String error, String message, boolean isJson) {
        super(message);
        this.error = error;
        this.message = message;
        this.isJson = isJson;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public boolean isPrintStackTrace() {
        return isPrintStackTrace;
    }

    public void setPrintStackTrace(boolean printStackTrace) {
        isPrintStackTrace = printStackTrace;
    }

}
