package com.zmgab.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomerRealm extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println(principals.getRealmNames());
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从token中获取用户名
        String principal = (String)token.getPrincipal();
        System.out.println(principal);

        // 根据身份信息使用JDBC MYBATIS查询相关数据库
        if ("xiaochen".equals(principal)) {
            // 参数: 1.正确的用户名 2.返回数据库中正确的密码  3.提供当前realm的名字
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("xiaochen","123456",this.getName());
            return info;
        }
        return null;
    }
}
