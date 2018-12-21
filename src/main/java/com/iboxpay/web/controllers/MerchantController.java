package com.iboxpay.web.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iboxpay.entity.Merchant;

@Controller
public class MerchantController {

  @RequestMapping("/index.htm")
  public ModelAndView forward(String name, MultipartFile file) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("name", name);
    mv.setViewName("index");
    return mv;
  }

  @RequestMapping("/getMerchant.json")
  @ResponseBody
  public Merchant response(HttpServletResponse response) {
    response.addCookie(new Cookie("sessionId", "123"));
    Merchant merchant = new Merchant();
    merchant.setMid(1);
    merchant.setMname("lyl");
    merchant.setMno("152164");
    return merchant;
  }

  @RequestMapping("/getMerchant2.json")
  @ResponseBody
  public Merchant response2(Merchant mcht) {
    return mcht;
  }

  @RequestMapping("/getMerchant3.json")
  @ResponseBody
  public Map<String, Object> response3(@RequestBody Map<String, Object> params) {
    return params;
  }

  @RequestMapping("/getMerchantMap.json")
  @ResponseBody
  public Map<String, Object> merchantInfo(@RequestBody Map<String, Object> map) {
    System.out.println("经过切面修改后的参数为：" + map);
    return map;
  }

  @RequestMapping("/upload.json")
  @ResponseBody
  public Map<String, Object> uploadFile(MultipartFile portrait) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (!portrait.isEmpty()) {
      map.put("resultCode", 1);
      map.put("fileName", portrait.getOriginalFilename());
      map.put("fieldName", portrait.getName());
    } else {
      map.put("resultCode", 0);
    }
    System.out.println(portrait.getSize());
    return map;
  }

  @RequestMapping("/download.json")
  public ResponseEntity<byte[]> downLoadFile(HttpServletResponse response) throws Exception {
    File file = new File("C:\\Users\\Administrator\\Pictures\\a.jpg");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
  }

}
