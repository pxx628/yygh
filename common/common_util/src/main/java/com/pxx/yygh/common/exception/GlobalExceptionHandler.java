package com.pxx.yygh.common.exception;

import com.pxx.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义的异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.build(201,"自定义异常被使用");
    }
}
