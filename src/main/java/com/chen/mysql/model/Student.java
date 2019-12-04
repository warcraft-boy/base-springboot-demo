package com.chen.mysql.model;

import lombok.Data;

@Data
public class Student {

    /**
     * 学生主键
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 班级主键
     */
    private Integer classId;
    /**
     * 班级名称
     */
    private String className;

}