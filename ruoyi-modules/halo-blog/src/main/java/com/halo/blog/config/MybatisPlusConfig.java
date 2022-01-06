package com.halo.blog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:56
 * @description
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.halo.blog.mapper")
public class MybatisPlusConfig {

}
