package com.chen;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.mongodb.model.Department;
import com.chen.mongodb.model.User;
import com.chen.mongodb.repository.UserRepository;
import com.chen.mysql.dao.ClassMapper;
import com.chen.mysql.dao.StudentMapper;
import com.chen.mysql.model.Student;
import com.chen.redis.RedisUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
class BaseSpringbootDemoApplicationTests {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    //=========================mybatisplus测试==============================
    /**
     * 查询所有学生信息
     */
    @Test
    public void test01(){
        List<Student> studentList = studentMapper.selectList(null);
        studentList.forEach(System.out::println);
    }
    /**
     * 查询符合某条件的学生信息
     */
    @Test
    public void test02(){
        //List<Student> studentList = studentMapper.selectList(new QueryWrapper<Student>().eq("id",2));
        List<Student> studentList2 = studentMapper.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getId,1));
        //studentList.forEach(System.out::println);
        studentList2.forEach(System.out::println);
    }
    /**
     * 查询符合某条件的学生数
     */
    @Test
    public void test03(){
        Integer count = studentMapper.selectCount(Wrappers.<Student>lambdaQuery().eq(Student::getClassName,"一班"));
        System.out.println(count);
    }
    /**
     * 根据条件查询某一具体对象
     */
    @Test
    public void test04(){
        Student student = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId,1));
        System.out.println(student);
    }
    /**
     * 修改
     */
    @Test
    public void test05(){
        int value = studentMapper.update(new Student(),Wrappers.<Student>lambdaUpdate().set(Student::getName,"冥冥").eq(Student::getId,1));
        System.out.println(value);
    }

    //==================================redis测试=======================================
    @Test
    public void test06(){
        redisTemplate.opsForValue().set("age",18);
        Integer age = (Integer) redisTemplate.opsForValue().get("age");
        //String name = (String) redisTemplate.opsForValue().get("key");
        System.out.println(age);
    }
    @Test
    public void test07(){
//        redisUtil.set("device", "computer");
//        System.out.println(redisUtil.get("device"));
        redisUtil.delete("device");
    }

    //=====================================MongoRepository测试==============================
    //插入数据
    @Test
    public void test08(){
        User user = new User();
        user.setName("重楼");
        user.setAge(21);
        userRepository.save(user);
    }
    //查询所有数据
    @Test
    public void test09(){
        List<User> users = userRepository.findAll();
        System.out.println(users);
    }
    //查询某一条数据
    @Test
    public void test10(){
        User user = new User();
        user.setName("重楼");
        Example<User> example = Example.of(user);
        User u = userRepository.findOne(example).get();
        System.out.println(u);
    }

    @Test
    public void test11(){
    }

    //====================================MongoTemplate测试==============================
    //插入数据
    @Test
    public void test22(){
        Department d = new Department();
        d.setDeptNo("007");
        d.setDeptName("技术部");
        mongoTemplate.save(d);
        //或者这样：
        //mongoTemplate.insert(d);
    }
    //查询满足某条件的数据（精确查询）
    @Test
    public void test23(){
        Query query = new Query();
        Criteria criteria = Criteria.where("deptNo").is("007");
        query.addCriteria(criteria);
        List<Department> departmentList = mongoTemplate.find(query, Department.class);
        //或者这样：
        //List<Department> departmentList = mongoTemplate.find(new Query(Criteria.where("deptNo").is("007")), Department.class);
        System.out.println(departmentList);
    }
    //查询所有
    @Test
    public void test24(){
        List<Department> departmentList = mongoTemplate.findAll(Department.class);
        System.out.println(departmentList);
    }
    //查询满足多个条件的数据
    @Test
    public void test25(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("deptNo").is("008");
        criteria.and("deptName").is("市场部");
        query.addCriteria(criteria);
        List<Department> departmentList = mongoTemplate.find(query, Department.class);
        System.out.println(departmentList);
    }
    //单条查询
    @Test
    public void test26(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("deptNo").is("008");
        query.addCriteria(criteria);
        Department department = mongoTemplate.findOne(query, Department.class);
        System.out.println(department);
    }
    //模糊查询
    @Test
    public void test27(){
        Pattern pattern = Pattern.compile("^.*" + "00" +".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("deptNo").regex(pattern);
        query.addCriteria(criteria);
        List<Department> departmentList = mongoTemplate.find(query, Department.class);
        System.out.println(departmentList);
    }
    //分页查询 query.limit(size).skip(size*(page-1))
    @Test
    public void test28(){
        Query query = new Query();
        query.limit(1).skip(1);
        List<Department> departmentList = mongoTemplate.find(query, Department.class);
        System.out.println(departmentList);
    }
    //修改
    @Test
    public void test29(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("_id").is("5e0d64fb3a571a782f3e1b8c");
        query.addCriteria(criteria);
        Update update = Update.update("deptNo", "123").set("deptName", "王炸");
        UpdateResult ur = mongoTemplate.upsert(query, update, Department.class);
        System.out.println(ur.getModifiedCount());
    }
    //删除
    @Test
    public void test30(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("_id").is("5e0d64fb3a571a782f3e1b8c");
        query.addCriteria(criteria);
        DeleteResult dr = mongoTemplate.remove(query, Department.class);
        System.out.println(dr.getDeletedCount());
    }
}
