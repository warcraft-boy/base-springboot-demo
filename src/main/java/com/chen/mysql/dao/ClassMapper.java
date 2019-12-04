package com.chen.mysql.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mysql.model.Class;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassMapper extends BaseMapper<Class> {
    int deleteByPrimaryKey(Integer id);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);
}