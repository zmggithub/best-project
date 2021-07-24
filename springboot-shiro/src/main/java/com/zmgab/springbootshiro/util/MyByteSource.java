package com.zmgab.springbootshiro.util;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 * @author: zmg
 */
public class MyByteSource extends SimpleByteSource implements Serializable {

    public MyByteSource(String string) {
        super(string);
    }
}
