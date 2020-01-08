package com.chen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: <br>读取yml文件自定义配置
 * @Date: Created in 2020/1/7 <br>
 * @Author: chenjianwen
 */
@Component
@Data
@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomConfig {

    private String value; //值
    private Person person; //对象
    private int[] arr; //数组
    private List<String> list; //list
    private Map<String, String> map; //map
    private List<Map<String, String>> listMap; //list中存map
}

