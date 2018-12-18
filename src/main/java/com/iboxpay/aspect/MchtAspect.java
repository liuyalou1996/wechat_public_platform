package com.iboxpay.aspect;

import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.iboxpay.entity.Merchant;

@Aspect
@Component
public class MchtAspect {

  @SuppressWarnings("unchecked")
  @Before("execution(* com.iboxpay.web.controllers.MerchantController.merchantInfo(..) )")
  public void preProcess(JoinPoint jp) {
    Object[] args = jp.getArgs();

    System.out.println("原参数为：" + Arrays.toString(args));

    if (args == null || args.length == 0) {
      return;
    }

    // 更改请求处理方法的参数
    Map<String, Object> param = (Map<String, Object>) args[0];
    param.put("age", "20");
    param.put("name", null);
  }

  @Before("execution(* com.iboxpay.web.controllers.MerchantController.response2(..) ) && args(mcht) "
      + "||execution(* com.iboxpay.web.controllers.MerchantController.response3(..) ) && args(mcht) ")
  public void preProcess(Merchant mcht) {
    mcht.setMno("14786632348");
  }
}
