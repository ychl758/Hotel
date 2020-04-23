package com.gataway.common.oauth2;

import com.gataway.entity.ManagerInfo;
import com.gataway.entity.ManagerToken;
import com.gataway.service.ManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 认证
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ManagerService managerService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用, 控制role 和 permissins时使用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ManagerInfo manager = (ManagerInfo)principals.getPrimaryPrincipal();//得到身份的信息的合集
        Integer managerId = manager.getManagerId();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//聚合授权信息

        // 模拟权限和角色
        Set<String> permsSet = new HashSet<>();
        Set<String> roles = new HashSet<>();
        if (managerId == 1) {
            permsSet.add("delete");
            permsSet.add("update");
            permsSet.add("view");
            permsSet.add("add");
            roles.add("admin");
        } else {
            permsSet.add("view");
            roles.add("test");
        }

        info.setStringPermissions(permsSet);
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        ManagerToken managerToken = managerService.queryByToken(accessToken);
        //token失效
        SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
        Date expireTime;
        boolean flag = true;
        try {
            expireTime     = sm.parse(managerToken.getExpireTime());
            flag = managerToken == null || expireTime.getTime() < System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(flag){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        ManagerInfo managerInfo = managerService.getManagerInfo(managerToken.managerId);
        //账号锁定
        // if(managerInfo.getStatus() == 0){
        //     throw new LockedAccountException("账号已被锁定,请联系管理员");
        // }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(managerInfo, accessToken, getName());

        return info;
    }
}
