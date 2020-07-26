package com.aqm.stock.Interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aqm.stock.model.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ResponseEntity argsInvalidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        errorMap.put("errors", errors);
        return new ResponseEntity(403, "ArgumentInvalidException", errorMap);
       
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    protected ResponseEntity runtimeExceptionHandler(Exception ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("errors", ex.getMessage());
        return new ResponseEntity(402, "Exception", errorMap);
    }
}