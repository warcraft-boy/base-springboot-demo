package com.chen.mongodb.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @Description: <br>
 * @Date: Created in 2020/1/2 <br>
 * @Author: chenjianwen
 */
@Data
@Document(collection = "department")
public class Department implements Serializable {

    @Id
    private ObjectId id;

    @Field("dept_no")
    private String deptNo;

    @Field("dept_name")
    private String deptName;
}
