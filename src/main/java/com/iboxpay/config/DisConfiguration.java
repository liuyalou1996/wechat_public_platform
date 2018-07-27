package com.iboxpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DisConfiguration {

  @Bean(name = "disconfMgrBean", destroyMethod = "destroy")
  public DisconfMgrBean disconfgMgrBean() {
    DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
    disconfMgrBean.setScanPackage("com.iboxpay.disconf");//设置扫描的包，用于扫描带有@DisconfFile注解的类
    return disconfMgrBean;
  }

  @Bean(name = "disconfMgrBean2", initMethod = "init", destroyMethod = "destroy")
  public DisconfMgrBeanSecond disconfgMgrBean2() {
    return new DisconfMgrBeanSecond();
  }

}
