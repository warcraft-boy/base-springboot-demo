package com.chen.mongodb.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Description: <br>
 * @Date: Created in 2019/12/17 <br>
 * @Author: chenjianwen
 */
@Data
@Document(collection = "user")
public class User implements Serializable {

    @Id
    private ObjectId id;
    private String name;
    private Integer age;
}
