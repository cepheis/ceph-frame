package com.cepheis.spring.web;

import com.cepheis.common.josn.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/10.
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    @ExceptionHandler(Throwable.class)
    public JsonError defaultErrorHandler(Throwable exception, HttpServletRequest request, HttpServletResponse response) {
        if (exception instanceof Exception) {
            return null;
        }
        if (exception instanceof CustomException) {
            return new JsonError(exception.getMessage(), ((CustomException) exception).getCode());
        }
        if (exception instanceof ApiException) {
            return new JsonError(exception.getMessage(), ErrorCodeUtils.ErrorCode.apiError.getCode());
        }
        //以上2种属于业务异常，业务中已经处理
        printLog(request, exception);
        if (exception instanceof BindException) {
            return new JsonError(exception.getMessage(), ErrorCodeUtils.ErrorCode.bindError.getCode());
        }
        if (exception instanceof MissingServletRequestParameterException
                || exception instanceof TypeMismatchException || exception instanceof MissingParameterException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.paramError);
        }
        if (exception instanceof NullPointerException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.nullError);
        }
        if (exception instanceof SQLException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.dbQueryError);
        }
        if (exception instanceof DataAccessException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.sqlError);
        }
        if (exception instanceof ReflectionException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.dataConvertError);
        }
        if (exception instanceof DuplicateKeyException) {
            return new JsonError(ErrorCodeUtils.ErrorCode.dbDumplicateError);
        }
        if (StringUtils.contains(exception.getClass().getSimpleName(), "ClientAbortException")) {
            return null;
        }
        return new JsonError(exception.getMessage());
    }

    private void printLog(HttpServletRequest request, Throwable exception) {
        logger.error("操作失败, URI:" + request.getRequestURI()
                        + ", IP : " + getIp(request)
                        + ", Referer:" + request.getHeader("Referer")
                        + ", UA:" + request.getHeader("User-Agent")
                        + ", Version : " + getVersion(request)
                        + ", Param : " + JsonMapper.to(request.getParameterMap())
                        + ", Cookie : " + JsonMapper.to(request.getCookies()),
                exception);
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (request.getHeader("X-Forwarded-For") != null) {
            ip = request.getHeader("X-Forwarded-For");
        } else if (request.getHeader("X-Real-IP") != null) {
            ip = request.getHeader("X-Real-IP");
        }
        return ip;
    }

    public String getVersion(HttpServletRequest request) {
        return StringUtils.defaultString(request.getHeader("tgVersion"), request.getHeader("version"));
    }
}
