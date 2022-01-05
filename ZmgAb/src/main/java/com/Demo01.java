package com;

public class Demo01 {

    public static void main(String[] args) {


        String str = "CCB87700BA211281E05339AE300B8EFC#@b63300dbd3294109a5f1022510e4f42d#@216e2d5f71474ff19c70e3e2e9aba4a1#@#@2021-09-24 00:00:00.0#@2021-09-30 00:00:00.0#@#@2#@#@#@01#@#@#@#@#@#@街道公示#@长征街道办事处门前#@04195650510#@#@公租房公字[2021]00049633#@211004004003#@2ee906dce05943d58b5d23660812eb6c#@b72753ccea0b45c1a28074c381785bb1#@#@2021-09-24 14:00:31.0#@2021-09-24 14:22:21.0#@#@";
        String[] split = str.split("#@");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
