package org.dizena.common.aop;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dizena.common.anno.CheckAuthAdvice;
import org.dizena.common.anno.CheckRoleAdvice;
import org.dizena.common.enums.ResultCode;
import org.dizena.common.except.RoleAuthException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class CheckRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            CheckRoleAdvice annotation = method.getAnnotation(CheckRoleAdvice.class);
            if (annotation == null) {
                annotation = method.getDeclaringClass().getAnnotation(CheckRoleAdvice.class);
            }
            if (annotation != null) {
                handleCheckRole(annotation);
            }
            CheckAuthAdvice annotation2 = method.getAnnotation(CheckAuthAdvice.class);
            if (annotation2 == null) {
                annotation2 = method.getDeclaringClass().getAnnotation(CheckAuthAdvice.class);
            }
            if (annotation2 != null) {
                handleCheckAuth(annotation2);
            }
        }
        return true;
    }

    private void handleCheckRole(CheckRoleAdvice annotation) {
        String[] roles = annotation.roles();
        String type = annotation.type();
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            boolean check;
            String flag = null;
            if ("or".equalsIgnoreCase(type)) {
                check = false;
                for (String role : roles) {
                    if (subject.hasRole(role)) {
                        check = true;
                        break;
                    }
                }
                flag = String.join(",", roles);
            } else {
                check = true;
                for (String role : roles) {
                    if (!subject.hasRole(role)) {
                        check = false;
                        flag = role;
                        break;
                    }
                }
            }
            if (!check) {
                throw new RuntimeException("缺乏角色" + flag);
            }
        } else {
            throw new RuntimeException("请登录后再试");
        }
    }

    private void handleCheckAuth(CheckAuthAdvice annotation) {
        String auth = annotation.auth();
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            if (!subject.isPermitted(auth)) {
                //throw new RuntimeException("缺乏" + auth + "权限");
                throw new RoleAuthException();
            }
        } else {
            throw new RuntimeException("请登录后再试");
        }
    }

}
