package com.chen;

import cn.coralglobal.message.api.enums.SmsTypeEnum;
import cn.coralglobal.message.api.exception.MessageCenterBuilderException;
import cn.coralglobal.message.api.exception.MessageCenterSendException;
import cn.coralglobal.message.api.service.*;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chen.config.CustomConfig;
import com.chen.mongodb.model.Department;
import com.chen.mongodb.model.User;
import com.chen.mongodb.repository.UserRepository;
import com.chen.mysql.master.dao.StudentMapper;
import com.chen.mysql.master.model.Student;
import com.chen.mysql.slave1.dao.ActorMapper;
import com.chen.mysql.slave1.model.Actor;
import com.chen.rabbitmq.Book;
import com.chen.redis.RedisUtil;
import com.chen.test.model.UserEs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import net.bytebuddy.asm.Advice;
import org.bson.types.ObjectId;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SpringBootTest(classes = BaseSpringbootDemoApplication.class)
@RunWith(SpringRunner.class)
public class BaseSpringbootDemoApplicationTests {

    @Autowired
    private StudentMapper studentMapper;
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
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private ActorMapper actorMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public BaseSpringbootDemoApplicationTests(){}
    @Autowired
    private MsgCenterServiceTemplate msgCenterServiceTemplate;
    @Autowired
    private EmailServiceTemplate emailServiceTemplate;
    @Autowired
    private EmailServiceCaptchaTemplate emailCaptchaTemplate;
    @Autowired
    private SmsServiceTemplate smsServiceTemplate;
    @Autowired
    private SmsServiceCaptchaTemplate smsCaptchaTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;



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
        //redisTemplate.opsForValue().set("age",18);
        //Integer age = (Integer) redisTemplate.opsForValue().get("age");
        //String name = (String) redisTemplate.opsForValue().get("key");
        //System.out.println(age);
//        redisTemplate.opsForSet().add("keys", "123");
//        redisTemplate.opsForSet().add("keys", "234");
//        redisTemplate.opsForSet().add("keys", "123");
//        Set<String> keys = redisTemplate.opsForSet().members("keys");
//        System.out.println(keys);
        stringRedisTemplate.opsForValue().set("str", "chenjianwen", 50, TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().get("str"));
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
        map.put("value", 110);
        map.put("data", Arrays.asList("hello", 3, true));
        //只需要传入要发送的对象，自动序列化发送给rabbitmq
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu", map); //第一个参数表示交换机，第二个表示交换机中定义的队列的key值，第三个是发的消息

        //这种可定制消息内容和消息头，message要序列化成字节数组，在org.springframework.amqp.core.Message里面
        //rabbitTemplate.send(exchange, routeKey, message);
    }
    //接受消息
    @Test
    public void test33(){
        Object obj = rabbitTemplate.receiveAndConvert("atguigu");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }


    /**
     * fanout 广播模式
     */
    //发送消息
    @Test
    public void test34(){
        Book book = new Book();
        book.setName("西游记");
        book.setAuthor("吴承恩");
        //由于fanout是将消息群发到所有队列，所以路由键routKey有没有都无所谓
        rabbitTemplate.convertAndSend("exchange.fanout","", book);
    }
    //接收消息
    @Test
    public void test35(){
        Object obj = rabbitTemplate.receiveAndConvert("atguigu");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }


    /**
     * topic 订阅模式
     */
    //发送消息
    @Test
    public void test36() throws JsonProcessingException {
        Book book = new Book();
        book.setName("西游记");
        book.setAuthor("吴承恩");
        //routKey路由键为"*.news"，*表示通配符，表示发送给所有已".news"结尾的队列
        rabbitTemplate.convertAndSend("exchange.topic", "*.news", book);
        //2.将对象序列化成字节数组传输
        byte[] data = objectMapper.writeValueAsBytes(book);
        rabbitTemplate.convertAndSend("exchange.topic", "*.news", data);
    }
    //接收消息
    @Test
    public void test37(){
        Object obj = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }

    /**
     * 创建交换机，队列
     */
    //创建交换机
    @Test
    public void test38(){
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange.direct"));
        System.out.println("创建交换机完成");
    }
    //删除交换机
    @Test
    public void test39(){
        amqpAdmin.deleteExchange("amqpadmin.exchange.direct");
        System.out.println("删除交换机完成");
    }
    //创建队列
    @Test
    public void test40(){
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("创建队列完成");

    }
    //删除队列
    @Test
    public void test41(){
        amqpAdmin.deleteQueue("amqpadmin.queue");
        System.out.println("删除队列完成");
    }
    //创建交换机和队列的绑定规则
    @Test
    public void test42(){
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin.exchange.direct", "amqpadmin.queue", null));
    }

    /**
     * confirm模式
     * 消息成功到达交换机时，ack=true。消息没到达交换机时，ack=false
     */
    @Test
    public void test_42(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param ack   交换机是否收到消息
             * @param cause 失败的信息
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    //发送成功
                    System.out.println("消息发送成功");
                    //TODO 发送成功后的逻辑

                }else{
                    //发送失败
                    System.err.println("消息发送失败，原因：" + cause);
                    //TODO 发送失败后的逻辑

                }
            }
        });
        rabbitTemplate.convertAndSend("amq.topic", "amqpadmin.*", "hello mq");
    }

    /**
     * 回退模式
     * 消息成功达到交换机且未到达队列时，触发回调函数
     */
    @Test
    public void test_43(){
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message   消息对象
             * @param i     返回码
             * @param s     错误信息
             * @param exchange  交换机
             * @param queue     队列
             */
            @Override
            public void returnedMessage(Message message, int i, String s, String exchange, String queue) {
                System.err.println(message);
                System.err.println(i);
                System.err.println(s);
                System.err.println(exchange);
                System.err.println(queue);
            }
        });
        rabbitTemplate.setMandatory(true); // 设置强制标志 仅适用于回退模式
        rabbitTemplate.convertAndSend("amq.topic", "amqpadmin.*", "hello mq");
    }

    //===============================多数据源测试=================================
    @Test
    public void test43(){
        Actor actor = new Actor();
        actor.setFirstName("Chen");
        actor.setLastName("Jevin");
        actor.setLastUpdate(new Date());
        actorMapper.insert(actor);

        Student s = new Student();
        s.setClassId(1);
        s.setName("Chen");
        s.setClassName("501");
        studentMapper.insert(s);
    }

    @Test
    public void test44() throws MessageCenterBuilderException, MessageCenterSendException {
        //msgCenterTemplate.msg(MessageSubject.newBuilder().template("1245233580516110337").replace("god is a girl", "10010").users("玛莎拉蒂", "兰博基尼").platform("demo hunter").build());
        msgCenterServiceTemplate.msg(MessageSubject.newBuilder().template("1245233580516110337").users("she").build());
    }

    @Test
    public void test45() throws MessageCenterBuilderException, MessageCenterSendException {
        emailServiceTemplate.email(EmailSubject.newBuilder().template("1462617943169871873").email("alichen3116@aliyun.com").number("123456").replace("123456").platform("base-springboot").build());
//        emailServiceTemplate.email(EmailSubject.newBuilder().template("1462617943169871873").email("alichen3116@aliyun.com").number("666").replace("666").platform("base-springboot").build());
//        emailServiceTemplate.email(EmailSubject.newBuilder().template("1285138969192845314").email("alichen3116@aliyun.com").replace("kaka").platform("base-springboot").build());
        //emailServiceTemplate.email(EmailSubject.newBuilder().template("1249578436797501442").email("alichen3116@aliyun.com").platform("base-springboot").replace("chenjianwen", "2020-04-05").build());
//        emailServiceTemplate.email(EmailSubject.newBuilder().template("1249577988099248130").email("ChenJevin@163.com").platform("base-springboot").build());

    }

    @Test
    public void test46() throws MessageCenterSendException {
        String value = stringRedisTemplate.opsForValue().get("email:code:1281130574945300481:alichen3116@aliyun.com");
        System.out.println(value);
        boolean b = emailCaptchaTemplate.checkCaptcha("1281130574945300481", "alichen3116@aliyun.com", "108090", null);
        System.out.println(b);
    }

    @Test
    public void test47() throws MessageCenterBuilderException, MessageCenterSendException {
//        smsServiceTemplate.sms(SmsSubject.newBuilder().template("1462670599091400705").mobile("13023635020").number("123456").replace("123456").type(SmsTypeEnum.S).build());
        smsServiceTemplate.sms(SmsSubject.newBuilder().template("1353580357366087682").mobile("13023635020").replace("111").type(SmsTypeEnum.S).build());
        //smsServiceTemplate.sms(SmsSubject.newBuilder().template("1262258402450292738").mobile("13023635020").type(SmsTypeEnum.V).platform("bilibili").build());
    }

    @Test
    public void test48() throws MessageCenterSendException {
        String value = stringRedisTemplate.opsForValue().get("sms:code:1262258402450292738:13023635020");
        System.out.println(value);
        boolean b = smsCaptchaTemplate.checkCaptcha("1262258402450292738", "13023635020", "606874", null);
        System.out.println(b);
//        stringRedisTemplate.delete("sms:code:1262264766891393025:13023635020");
    }

    @Test
    public void test49(){
        String v = stringRedisTemplate.opsForValue().get("cg:captcha:img:am8b62611li29pdmc6no");
        System.out.println(v);
    }

    @Test
    public void test50() throws Exception {
        //读取本地文件
        FileReader fileReader = new FileReader("/Users/chenjianwen/Downloads/QQ_6.6.5.dmg");
        //将文件转换成字节数组
        byte[] result = fileReader.readBytes();
        //通过Base64将字节数组转换成字符串
        String fileContent = Base64.getEncoder().encodeToString(result);
        EmailFile ef= new EmailFile();
        ef.setFileName("QQ_6.6.5.dmg"); //自定义文件名称
        ef.setFileContent(fileContent);
        emailServiceTemplate.email(EmailSubject.newBuilder().template("1249577988099248130").email("alichen3116@aliyun.com").platform("base-springboot").file(ef).build());

        //Base64将字符串转为字节数组，再转为流
//        byte[] fileByte = cn.hutool.core.codec.Base64.decode(fileContent);
//        InputStream is = new ByteArrayInputStream(fileByte);
    }

    @Test
    public void test51(){
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Student> p = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 2);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        IPage<Student> bsbcPage = studentMapper.selectPage(p, wrapper);
        System.out.println(bsbcPage);
    }

    @Test
    public void test52(){
        Integer value = (Integer) redisTemplate.opsForValue().get("test");
        if(value != null && value == 0){
            System.err.println("次数已经用完");
        }
        if(value == null){
            redisTemplate.opsForValue().set("test", 2, 24, TimeUnit.HOURS);
        }
        if(value != null && value > 0){
            value = value - 1;
            redisTemplate.opsForValue().set("test", value, 24, TimeUnit.HOURS);
        }
    }

    @Test
    public void test53(){
        Integer val = (Integer) redisTemplate.opsForValue().get("test");
        if(val == null){
            redisTemplate.opsForValue().increment("test", 1);
            redisTemplate.expire("test", 60, TimeUnit.SECONDS);
        }else{
            if(val > 3){
                redisTemplate.expire("test", 60, TimeUnit.SECONDS);
                System.err.println("err");
            }
            redisTemplate.opsForValue().increment("test", 1);
        }
    }


    //===============================elasticsearch测试开始=================================

    /**
     * 创建索引，PUT或POST命令
     */
    @Test
    public void test54() throws IOException {
        //创建索引
        CreateIndexRequest chen_index = new CreateIndexRequest("chen_index1");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(chen_index, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 删除索引
     */
    @Test
    public void test55() throws IOException {
        GetIndexRequest chen_index1 = new GetIndexRequest("chen_index1");
        boolean exists = restHighLevelClient.indices().exists(chen_index1, RequestOptions.DEFAULT);
        if(exists){
            //判断索引是否存在
            DeleteIndexRequest dr = new DeleteIndexRequest("chen_index1");
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(dr, RequestOptions.DEFAULT);
            System.out.println(delete.isAcknowledged());
        }else{
            System.out.println("该索引不存在");
        }
    }

    /**
     * 添加文档
     */
    @Test
    public void test56() throws IOException {
        UserEs userEs = new UserEs("橘右京", 18);
        //创建请求
        IndexRequest request = new IndexRequest("ju_index");
        //规则，相当于put /ju_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        //将请求封装成json数据
        request.source(JSON.toJSONString(userEs), XContentType.JSON);
        //客户端发送请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status());
    }

    /**
     * 获取文档，相当于 GET /index/_doc/1
     */
    @Test
    public void test57() throws Exception {
        GetRequest ju_index = new GetRequest("ju_index", "1");
        boolean exists = restHighLevelClient.exists(ju_index, RequestOptions.DEFAULT);
        if(exists){
            GetResponse documentFields = restHighLevelClient.get(ju_index, RequestOptions.DEFAULT);
            System.out.println(documentFields.getSourceAsString());
        }else {
            System.out.println("没有此文档");
        }
    }

    /**
     * 更新文档
     */
    @Test
    public void test58() throws IOException {
        GetRequest ju_index = new GetRequest("ju_index", "1");
        boolean exists = restHighLevelClient.exists(ju_index, RequestOptions.DEFAULT);
        if(exists){
            UpdateRequest ur = new UpdateRequest("ju_index", "1");
            ur.timeout("1s");

            UserEs us = new UserEs("玛维", 16);
            ur.doc(JSON.toJSONString(us), XContentType.JSON);

            UpdateResponse update = restHighLevelClient.update(ur, RequestOptions.DEFAULT);
            System.out.println(update.status());
        }else{
            System.out.println("没有此文档");
        }
    }

    /**
     * 删除文档
     */
    @Test
    public void test59() throws IOException {
        GetRequest ju_index = new GetRequest("ju_index", "1");
        boolean exists = restHighLevelClient.exists(ju_index, RequestOptions.DEFAULT);
        if(exists){
            DeleteRequest dr = new DeleteRequest("ju_index", "1");
            dr.timeout("1s");

            DeleteResponse delete = restHighLevelClient.delete(dr, RequestOptions.DEFAULT);
            System.out.println(delete.status());
        }else {
            System.out.println("没有此文档");
        }
    }

    /**
     * 批量添加文档
     */
    @Test
    public void test60() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");

        ArrayList<UserEs> ues = new ArrayList<>();
        ues.add(new UserEs("玛维1", 1));
        ues.add(new UserEs("玛维2", 2));
        ues.add(new UserEs("玛维3", 3));
        ues.add(new UserEs("玛维4", 4));

        for(int i = 0; i < ues.size(); i++){
            bulkRequest.add(new IndexRequest("ju_index")
                        .id("" + (i+1)) //不生成id，会随机生成id
                        .source(JSON.toJSONString(ues.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }

    /**
     * 条件构造查询
     */
    @Test
    public void test61() throws IOException {
        SearchRequest ju_index = new SearchRequest("ju_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "qinjiang1");
        searchSourceBuilder.query(termQueryBuilder);

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(3);

        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(6)); //设置超时时间

        ju_index.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(ju_index, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        for(SearchHit sh : search.getHits()){
            System.out.println(sh.getSourceAsMap().toString());
        }
    }

    //===============================elasticsearch测试结束=================================


}
