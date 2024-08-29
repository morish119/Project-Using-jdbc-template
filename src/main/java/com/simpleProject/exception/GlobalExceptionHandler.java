package com.simpleProject.exception;

import com.simpleProject.entity.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponce> handlerResourceNotFoundException(UserNotFoundException ex){
        String message = ex.getMessage();
        ApiResponce status = ApiResponce.builder().message(message).status(HttpStatus.NOT_FOUND).build();

        return  new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
    }
}
