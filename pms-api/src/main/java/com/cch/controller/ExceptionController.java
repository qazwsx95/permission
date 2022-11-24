package com.cch.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.cch.model.vo.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public HttpResult handler(NotLoginException e){
//        return HttpResult.error(10000,"未登录或登录已失效");
//    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResult handler(NotPermissionException e){
        return HttpResult.error(400001,e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResult handler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        String msg = errors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("<br/>"));
        return HttpResult.error(400,msg);
    }
//    兜底异常
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResult handler(Throwable e, HttpServletRequest req) {
        log.error("系统异常:["+req.getMethod()+"]"+req.getRequestURI(),e);
        return HttpResult.error(500,e.getMessage());
    }








}
