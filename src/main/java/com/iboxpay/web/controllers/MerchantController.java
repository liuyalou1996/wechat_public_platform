package com.iboxpay.web.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iboxpay.entity.Merchant;

@Controller
public class MerchantController {

  @RequestMapping("/getMerchant.json")
  @ResponseBody
  public Merchant response() {
    Merchant merchant = new Merchant();
    merchant.setMid(1);
    merchant.setMname("lyl");
    merchant.setMno("152164");
    return merchant;
  }

  @RequestMapping("/getMerchantMap.json")
  @ResponseBody
  public Map<String, Object> merchantInfo(@RequestBody Map<String, Object> map) {
    System.out.println(map);
    return map;
  }
}
