package com.iboxpay.registrar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    System.err.println("调用registerBeanDefinitions方法");
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.err.println("调用setBeanFactory方法");
  }

}
