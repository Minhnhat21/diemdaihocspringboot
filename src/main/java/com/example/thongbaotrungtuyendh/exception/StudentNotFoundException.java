package com.example.thongbaotrungtuyendh.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends Exception {



    private static final long serialVersionUID = 1L;
    private String error;
    private String message;
    private HttpStatus httpStatus;
    @JsonIgnore
    private boolean isPrintStackTrace;

    public StudentNotFoundException() {
        super();
    }

    public StudentNotFoundException(String error, String message) {
        super(message);
        this.error = error;
        this.message = message;
    }

    public StudentNotFoundException(String error, String message, HttpStatus httpStatus) {
        super(message);
        this.error = error;
        this.message = message;
        this.httpStatus = httpStatus;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isPrintStackTrace() {
        return isPrintStackTrace;
    }

    public void setPrintStackTrace(boolean printStackTrace) {
        isPrintStackTrace = printStackTrace;
    }
}
