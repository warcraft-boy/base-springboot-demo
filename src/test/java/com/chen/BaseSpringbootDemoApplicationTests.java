package com.chen;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.config.CustomConfig;
import com.chen.mongodb.model.Department;
import com.chen.mongodb.model.User;
import com.chen.mongodb.repository.UserRepository;
import com.chen.mysql.dao.ClassMapper;
import com.chen.mysql.dao.StudentMapper;
import com.chen.mysql.model.Student;
import com.chen.redis.RedisUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.misc.Hash;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
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
    @Autowired
    private CustomConfig customConfig;
    @Autowired
    private RabbitTemplate rabbitTemplate;


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
        //redisUtil.delete("device");
    }

    /**
     * 以下MongoRepository和MongoTemplate都可以操作mongodb，建议两者配合使用
     */
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
    //批量插入
    @Test
    public void test11(){
        List<User> users = new ArrayList<>();
        for(int i = 0;i < 5;i++){
            User u = new User();
            u.setName("chen" + i);
            u.setAge(18);
            users.add(u);
        }
        userRepository.saveAll(users);
    }
    //查询数据总条数
    @Test
    public void test12(){
        long count = userRepository.count();
        System.out.println(count);
    }
    //按条件查看数据是否存在
    @Test
    public void test13(){
        User user = new User();
        user.setAge(18);
        Example<User> example = Example.of(user);
        boolean exist = userRepository.exists(example);
        System.out.println(exist);
    }
    //按照主键查看数据是否存在
    @Test
    public void test14(){
        ObjectId id = new ObjectId("5df89aa9cc5f11430d46e786");
        boolean exist = userRepository.existsById(id);
        System.out.println(exist);
    }
    //排序查询，按照年龄排序
    @Test
    public void test15(){
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        List<User> users = userRepository.findAll(sort);
        System.out.println(users);
    }
    //分页查询
    @Test
    public void test16(){
        int pageNo = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        long count = userPage.getTotalElements(); //数据总条数
        List<User> users = userPage.getContent(); //当前页的数据
        System.out.println(count);
        System.out.println(users);
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

    //==========================yml自定义属性读取值==============================
    @Test
    public void test31(){
        //设置单个值
        System.out.println(customConfig.getValue());
        //对象
        System.out.println(customConfig.getPerson());
        //数组
        int[] arr = customConfig.getArr();
        for(int i : arr){
            System.out.println(i);
        }
        //集合
        System.out.println(customConfig.getList());
        //map
        System.out.println(customConfig.getMap().toString());
        //包含集合的map
        System.out.println(customConfig.getListMap());
    }

    //===============================rabbitmq=================================

    /**
     * direct 点对点模式
     */
    //发送消息
    @Test
    public void test32(){
        Map<String,Object> map = new HashMap<>();
        map.put("key", "this is the fist message");
        map.put("data", Arrays.asList("hello", 3, true));
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu", map); //第一个参数表示交换机，第二个表示交换机中定义的队列的key值，第三个是发的消息
    }
    //接受消息
    @Test
    public void test33(){
        Object obj = rabbitTemplate.receiveAndConvert("atguigu");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }
}
