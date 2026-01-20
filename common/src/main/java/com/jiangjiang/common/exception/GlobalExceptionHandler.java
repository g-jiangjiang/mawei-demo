package com.jiangjiang.common.exception;

import com.jiangjiang.common.api.Result;
import com.jiangjiang.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获非法参数异常 (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgument(IllegalArgumentException e) {
        log.error("参数校验异常: {}", e.getMessage());
        return Result.error(ResultCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    // 捕获参数缺失异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingParam(MissingServletRequestParameterException e) {
        log.error("缺少请求参数: {}", e.getParameterName());
        return Result.error(ResultCode.PARAM_ERROR.getCode(), "缺少参数: " + e.getParameterName());
    }

    // 捕获参数类型不匹配
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.error("参数类型错误: {} should be {}", e.getName(), e.getRequiredType());
        return Result.error(ResultCode.PARAM_ERROR.getCode(), "参数类型错误: " + e.getName());
    }

    // 捕获所有未知的异常 (Exception)
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统未知异常", e);
        return Result.error(ResultCode.FAILED.getCode(), "系统繁忙，请稍后重试");
    }
}