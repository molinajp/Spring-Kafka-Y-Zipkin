package com.example.errorhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class APIError {

    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public APIError() {
        timestamp = LocalDateTime.now();
    }

    public APIError(int code) {
        this();
        this.code = code;
        this.message = "Unexpected error";
    }

    public APIError(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

}
