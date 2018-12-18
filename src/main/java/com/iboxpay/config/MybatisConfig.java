package com.iboxpay.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@MapperScan(basePackages = "com.iboxpay.**.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBean")
@EnableTransactionManagement
public class MybatisConfig {

  @ConfigurationProperties(prefix = "druid")
  @Bean(name = "druidDataSource", initMethod = "init", destroyMethod = "close")
  public DataSource druidDataSource() {
    return DruidDataSourceBuilder.create().build();
  }

  @Bean("sqlSessionFactoryBean")
  public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("druidDataSource") DataSource dataSource)
      throws IOException {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
    factoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mapper/*Mapper.xml"));
    return factoryBean;
  }

  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager(
      @Qualifier("druidDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
