package com.kaptchaDemo;

public enum OgrEnum {
    type1("1","type1"), type2("2","type2"), type3("3","type3");
    private String type;
    private String code;

    OgrEnum(String type, String code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}


enum ContractTempletEnum {
    PPT(1,"普通模板"),SIGNT(2,"签约模板");

    private Integer id;
    private String name;

    ContractTempletEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId() { return id; }
    public String getName() { return name; }

    public static ContractTempletEnum getById(Integer id){
        for(ContractTempletEnum transactType : values()){
            if (transactType.getId() == id) {
                //获取指定的枚举
                return transactType;
            }
        }
        return null;
    }

    }
