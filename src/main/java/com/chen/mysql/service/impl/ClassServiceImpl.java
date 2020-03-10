package com.chen.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mysql.dao.ClassMapper;
import com.chen.mysql.model.Class;
import com.chen.mysql.service.ClassService;
import org.springframework.stereotype.Service;

/**
 * @Description: <br>
 * @Date: Created in 2020/3/10 <br>
 * @Author: chenjianwen
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
}
