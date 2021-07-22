package com.zmgab.springbootshiro.shiro;

import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("调用授权验证：" + primaryPrincipal);

        // 根据主身份信息获取角色 和 权限信息
        if ("zmg".equals(primaryPrincipal)) {
            SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
            // user admin guest
            sai.addRole("user");

            sai.addStringPermission("user:*:*");
            return sai;
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        User byName = service.findByName(principal);
        System.out.println(byName);
        if (byName.getUsername().equals(principal)) {
            return new SimpleAuthenticationInfo(principal, byName.getPassword(), ByteSource.Util.bytes(byName.getSalt()), this.getName());
        }
        return null;
    }
}
