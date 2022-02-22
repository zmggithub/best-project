package com.kaptchaDemo;

public class Main4Java {

    /**
     * 执行java main方法
     */
    // java -Djava.ext.dirs=./lib/ ccb.zwpt.client.YhTestClient

    public static void main(String[] args) throws Exception {

        ContractTempletEnum byId = ContractTempletEnum.getById(null);

        if (byId == null) {
            return ;
        }

        switch (byId) {
            case PPT:
                System.out.println("PPT");
                break;

            case SIGNT:
                System.out.println("SIGNT");
                break;

            default:
                throw new Exception();
        }
    }
}
