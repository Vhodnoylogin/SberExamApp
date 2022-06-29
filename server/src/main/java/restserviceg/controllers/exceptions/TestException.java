package restserviceg.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import restserviceg.logic.exceptions.RecordNotFoundException;

@RestController
public class TestException {
    @GetMapping("/test/exceptions/exception")
    public String testExceptionsException() throws Exception {
        throw new Exception("QQL Exception");
    }

    @GetMapping("/test/exceptions/RecordNotFoundException")
    public String testExceptionRecordNotFoundException() throws Exception {
        throw new RecordNotFoundException("RecordNotFoundException");
    }

//    @ExceptionHandler
//    public ResponseEntity<String> handleExceptions(Exception e){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(e.getMessage());
//    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> handleExceptionRecordNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
