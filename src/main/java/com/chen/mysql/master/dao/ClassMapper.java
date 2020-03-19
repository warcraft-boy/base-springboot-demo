package com.chen.mysql.master.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mysql.master.model.Class;
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