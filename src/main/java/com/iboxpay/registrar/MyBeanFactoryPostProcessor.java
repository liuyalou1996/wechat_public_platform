package com.iboxpay.registrar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    System.err.println("调用postProcessBeanFactory方法");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.err.println("调用setApplicationContext方法");
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    System.err.println("调用postProcessBeanDefinitionRegistry方法");
  }

}
