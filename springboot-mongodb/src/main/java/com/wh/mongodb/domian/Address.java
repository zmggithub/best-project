package com.wh.mongodb.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street; //街道
    private String city;   //城市
    private String zip;    //邮编
}
