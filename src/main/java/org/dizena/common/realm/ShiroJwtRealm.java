package org.dizena.common.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.dizena.common.enums.ResultCode;
import org.dizena.common.except.TokenException;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.service.UserService;
import org.dizena.modules.master.zload.ConstantMaster;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ShiroJwtRealm extends AuthorizingRealm
{
    @Resource
    private UserService service;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token)
    {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal)
    {
        String account = principal.toString();//JwtUtil.getAccount(principal.toString());
        User user = service.login(account);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>(Arrays.asList(user.getRoles().split(",")));
        simpleAuthorizationInfo.setRoles(roles);
        Set<String> permission = new HashSet<>(Arrays.asList(user.getAuths().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
    {
        if (authenticationToken == null || authenticationToken.getCredentials() == null)
        {
            throw new TokenException();
        }
        String token = (String) authenticationToken.getCredentials();

        String account = JwtUtil.getAccount(token);

        User user = service.login(account);
        if (user == null)
        {
            throw new AuthenticationException("User didn't existed!");
        }

        if (user.getLocked().equals(ConstantMaster.LOCKED))
        {
            throw new AuthenticationException("user account locked");
        }

        if (user.getLocked().equals(ConstantMaster.CLOSE))
        {
            throw new AuthenticationException("user cancel account");
        }

        if (!JwtUtil.verify(token, account, user.getPasswd()))
        {
            throw new AuthenticationException("please restart login");
        }
        return new SimpleAuthenticationInfo(account, token, ConstantJwt.TOKEN_KEY);
    }
}