package com.iboxpay.aspect;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import com.iboxpay.annotation.DataSourceRoutable;
import com.iboxpay.holder.DataSourceTypeHolder;

@Component
@Aspect
public class DynamicDataSourceRoutingAspect implements PriorityOrdered {

  @Before("@within(dataSourceRouting) || @annotation(dataSourceRouting)")
  public void determineDataSource(JoinPoint jp, DataSourceRoutable dataSourceRouting) {
    if (!Objects.isNull(dataSourceRouting)) {
      DataSourceTypeHolder.setDataSourceType(dataSourceRouting.value());
    } else {
      Class<?> clazz = jp.getTarget().getClass();
      if (clazz.isAnnotationPresent(DataSourceRoutable.class)) {
        DataSourceRoutable annotionOnClass = clazz.getAnnotation(DataSourceRoutable.class);
        DataSourceTypeHolder.setDataSourceType(annotionOnClass.value());
      }
    }
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 1;
  }

}
