package com.zmgab.springbootshiro.shiro;

import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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
