package com.exam.restservice.server.controllers.exceptions;

import com.exam.restservice.server.logic.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestException {
    @GetMapping("/test/exceptions/exception")
    public String testExceptionsException() throws Exception {
        throw new Exception("QQL Exception");
    }

    @GetMapping("/test/exceptions/RecordNotFoundException")
    public String testExceptionRecordNotFoundException() {
        throw new RecordNotFoundException("RecordNotFoundException");
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Exception> handleExceptionRecordNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e);
    }
}
