package com.chen.mysql.master.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mysql.master.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}