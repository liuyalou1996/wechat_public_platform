package com.iboxpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource("classpath:jdbc/jdbc.properties")
public class MybatisConfig {

  @Bean
  @ConfigurationProperties(prefix = "druid")
  public DruidDataSource druidDataSource() {
    return new DruidDataSource();
  }
}
