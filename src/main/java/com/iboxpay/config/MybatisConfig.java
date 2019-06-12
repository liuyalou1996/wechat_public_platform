package com.iboxpay.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.iboxpay.constants.DataSourceType;
import com.iboxpay.holder.DataSourceTypeHolder;

@Configuration
@MapperScan(basePackages = "com.iboxpay.**.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

  @ConfigurationProperties(prefix = "spring.datasource.druid")
  @Bean(initMethod = "init", destroyMethod = "close")
  public DataSource druidDataSource() {
    return DruidDataSourceBuilder.create().build();
  }

  @Bean
  public DynamicRoutingDataSource dynamicRoutingDataSource(DataSource druidDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MYSQL, druidDataSource);

    DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
    dynamicDataSource.setTargetDataSources(targetDataSources);
    dynamicDataSource.setDefaultTargetDataSource(druidDataSource);
    return dynamicDataSource;
  }

  @Bean("sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(DynamicRoutingDataSource dataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
    factoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mapper/*Mapper.xml"));
    return factoryBean.getObject();
  }

  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager(DynamicRoutingDataSource dynamicRoutingDataSource) {
    return new DataSourceTransactionManager(dynamicRoutingDataSource);
  }

  public static class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
      System.err.println("选择的数据源为：" + DataSourceTypeHolder.getDataSourceType());
      return DataSourceTypeHolder.getDataSourceType();
    }

  }

}
