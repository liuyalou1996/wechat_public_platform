package com.iboxpay.web.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iboxpay.service.StudentServiceImpl;

@RestController
public class StudentController {

  @Autowired
  private StudentServiceImpl studentService;

  @RequestMapping("/getAllStudents.json")
  public List<Map<String, Object>> getAllStudent() {
    return studentService.getAllStudent();
  }
}
