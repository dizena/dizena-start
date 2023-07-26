package org.dizena.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dizena.common.anno.IgnoreResponseAdvice;
import org.dizena.common.bean.ResultVO;
import org.dizena.common.enums.ResultCode;
import org.dizena.common.except.RoleAuthException;
import org.dizena.common.except.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查注解是否存在，存在则忽略拦截
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return !Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 判断请求是否来自Knife4j
        String uri = request.getURI().toString();
        if (uri.contains("/v2/api-docs") || uri.contains("/swagger-resources")) {
            return body;
        }
        if (body == null) {
            return ResultVO.success();
        }
        if (body instanceof ResultVO) {
            return body;
        }
        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return ResultVO.success(body);
        }
        if (body instanceof LinkedHashMap) {
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) body;
            JSONObject obj = new JSONObject();
            obj.put("status", map.get("status"));
            obj.put("error", map.get("error"));
            obj.put("uri", map.get("path"));
            obj.put("ts", map.get("timestamp"));
            // 此处错误可以记录下来
            log.error(obj.toJSONString());
            return ResultVO.fail(500, "AccessToken失效或错误");
        }
        return ResultVO.success(body);
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handlerException(Exception e, HttpServletRequest request) {
        Map<String, Object> obj;
        String uri = request.getRequestURI();
        StackTraceElement[] steps = e.getStackTrace();
        StackTraceElement ste = steps[0];
        obj = new HashMap<>();
        obj.put("uri", uri);
        obj.put("exceptType", e.getClass().getName());
        obj.put("exceptMsg", e.getMessage());
        obj.put("className", ste.getClassName());
        obj.put("methodName", ste.getMethodName());
        obj.put("lineNumber", ste.getLineNumber());
        log.error(JSON.toJSONString(obj));

        if (e instanceof TokenException) {
            return ResultVO.fail(ResultCode.TOKEN_ERROR);
        }

        if (e instanceof RoleAuthException) {
            return ResultVO.fail(ResultCode.ROLE_AUTH_ERROR);
        }

        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getField()).append(" ：").append(fieldError.getDefaultMessage()).append(" ");
            }
            return ResultVO.fail(ResultCode.PARAM_IS_INVALID, sb.toString());
        }

        return ResultVO.fail(ResultCode.ERROR, e.getMessage());
    }


}
