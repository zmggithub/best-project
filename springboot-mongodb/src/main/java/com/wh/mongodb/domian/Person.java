package com.wh.mongodb.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private ObjectId id;
    private String name;
    private Integer age;
    private Address address;


}
