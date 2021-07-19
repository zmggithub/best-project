package com.zmgab.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestShiroMD5 {

    public static void main(String[] args) {

        // 创建一个md5算法

//        Md5Hash md5Hash = new Md5Hash();
//        md5Hash.setBytes("123".getBytes());
//        System.out.println(md5Hash.toHex());

        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex());

        // 使用md5 + salt处理
        Md5Hash md5Hash1 = new Md5Hash("123", "x0*7ps");
        System.out.println(md5Hash1.toHex());

        // 使用md5 + salt + hash散列
        Md5Hash md5Hash2 = new Md5Hash("123", "x0*7ps", 1024);
        System.out.println(md5Hash2);
//        202cb962ac59075b964b07152d234b70
//        c15be9a15a0a238084e0c5a846f3a7b4
//        44c42bc682c33a4dae2af47eba4c8011
    }
}
