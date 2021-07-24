package com.zmgab.springbootshiro.shiro;

import com.zmgab.springbootshiro.entity.Perms;
import com.zmgab.springbootshiro.entity.Role;
import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.service.UserService;
import com.zmgab.springbootshiro.util.MyByteSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("调用授权验证：" + primaryPrincipal);

        // 根据主身份信息获取角色 和 权限信息
        User user = service.findRolesByUserName(primaryPrincipal);
        List<Role> roles = user.getRoles();

        // 授权角色信息
        if (!CollectionUtils.isEmpty(roles)) {
            SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
            roles.forEach( r -> {
                System.out.println(r);
                sai.addRole(r.getName());

                // 权限信息
                List<Perms> perms = service.findPermsByRoleId(r.getId());
                if (!CollectionUtils.isEmpty(perms)) {
                    perms.forEach(p -> {
                        System.out.println(p);
                        sai.addStringPermission(p.getName());
                    });
                }
            });
            return sai;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        User byName = service.findByName(principal);
        System.out.println(byName);
        if (byName != null) {
            if (principal.equals(byName.getUsername())) {
                return new SimpleAuthenticationInfo(
                        principal,
                        byName.getPassword(),
                        new MyByteSource(byName.getSalt()),
                        this.getName()
                );
            }
        }

        return null;
    }
}
