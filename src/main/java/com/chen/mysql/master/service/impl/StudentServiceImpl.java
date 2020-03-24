package com.chen.mysql.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mysql.master.dao.StudentMapper;
import com.chen.mysql.master.model.Student;
import com.chen.mysql.master.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @Description: <br>
 * @Date: Created in 2020/3/10 <br>
 * @Author: chenjianwen
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
