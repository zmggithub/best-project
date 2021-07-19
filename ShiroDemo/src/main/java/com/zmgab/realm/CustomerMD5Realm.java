package com.zmgab.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 使用自定义realm 假如md5 + salt + hash
 */
public class CustomerMD5Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("===================" + principals.getRealmNames());
        String primaryPrincipal = (String)principals.getPrimaryPrincipal();
        System.out.println("身份信息:" + primaryPrincipal);

        // 根据身份信息 用户名 获取当前用户的角色信息,以及权限信息 xiaochen admin user
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 将数据库中查询角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        // 将数据库中查询权限信息赋值个权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        simpleAuthorizationInfo.addStringPermission("product:create");

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取身份信息
        String principal = String.valueOf(token.getPrincipal());

        // 根据用户名查询数据库
        if ("xiaochen".equals(principal)) {
            // md5
//            return new SimpleAuthenticationInfo(principal, "202cb962ac59075b964b07152d234b70", this.getName());

            // md5 + salt  参数1:数据库用户名  参数2:数据库MD5+salt之后的密码 参数3:注册时的随机盐 参数4:realm的名字
//            return new SimpleAuthenticationInfo(
//                    principal,
//                    "c15be9a15a0a238084e0c5a846f3a7b4",
//                    ByteSource.Util.bytes("x0*7ps"),
//                    this.getName());

            // md5 + salt + hash 参数1:数据库用户名  参数2:数据库MD5+salt之后的密码 参数3:注册时的随机盐 参数4:realm的名字
            return new SimpleAuthenticationInfo(
                    principal,
                    "44c42bc682c33a4dae2af47eba4c8011",
                    ByteSource.Util.bytes("x0*7ps"),
                    this.getName());
        }
        return null;
    }
}
