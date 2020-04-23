package com.gataway.common.exceptionHandle;

import com.alibaba.fastjson.JSONObject;
import com.gataway.common.base.BaseResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class ExceptionController {
    private Logger log = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse globalException(Throwable ex) {
        log.error("未知异常 ex: ", ex);
        return new BaseResponse<>(false, "未知异常, 请联系管理员", null);
    }

    @ExceptionHandler(AuthorizationException.class)
    public JSONObject handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        JSONObject json = new JSONObject();
        json.put("msg", "没有权限，请联系管理员授权");

        return json;
    }

}

