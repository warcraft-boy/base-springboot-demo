package com.chen;

import com.chen.mysql.dao.StudentMapper;
import com.chen.mysql.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BaseSpringbootDemoApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test01(){
        List<Student> studentList = studentMapper.selectList(null);
        studentList.forEach(System.out::println);
    }
}
