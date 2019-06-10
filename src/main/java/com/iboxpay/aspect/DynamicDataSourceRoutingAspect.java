package com.iboxpay.aspect;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.iboxpay.annotation.DataSourceRouting;
import com.iboxpay.holder.DataSourceTypeHolder;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class DynamicDataSourceRoutingAspect {

  // @Before("@annotation(dataSourceRouting)")
  @Before("@within(dataSourceRouting) || @annotation(dataSourceRouting)")
  public void determineDataSource(JoinPoint jp, DataSourceRouting dataSourceRouting) {
    System.err.println("进入DynamicDataSourceRoutingAspect");
    System.err.println(jp.getSignature().getName());
    if (Objects.isNull(dataSourceRouting)) {
      return;
    }
    System.err.println("设置数据源" + dataSourceRouting.value());
    DataSourceTypeHolder.setDataSourceType(dataSourceRouting.value());
  }

}
