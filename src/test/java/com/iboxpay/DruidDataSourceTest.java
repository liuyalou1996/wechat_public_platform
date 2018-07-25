package com.iboxpay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.pool.DruidDataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidDataSourceTest {

  @Autowired
  private DruidDataSource dataSource;
  @Test
  public void contextLoads() {
    System.out.println(dataSource);
  }

}
