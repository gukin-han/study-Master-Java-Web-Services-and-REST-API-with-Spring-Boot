package com.gukin.rest.webservices.restwebservices.exception;

import com.gukin.rest.webservices.restwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

// 해당 레이어(Controller)에서 던져지는 모든 예외를 캐치하기 위해 사용된다
// 필요한 것은 try-catch 혹은 try-with-resources를 이용해서 예외 처리를 구현하고
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class) // 이 어노테이션은 Exception 타입 혹은 서브 클래스를 다루는 메서드에 붙여진다
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request)
            throws Exception {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex,
                                                                          WebRequest request)
            throws Exception {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                ex.getMessage(),
//                request.getDescription(false));

//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                ex.getFieldError().getDefaultMessage(),
//                request.getDescription(false));

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Total Errors: "
                        + ex.getErrorCount()
                        + " First Error: "
                        + ex.getFieldError().getDefaultMessage(),

                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}