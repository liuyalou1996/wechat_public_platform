package com.iboxpay.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iboxpay.annotation.DataSourceRouting;
import com.iboxpay.constants.DataSourceType;
import com.iboxpay.mapper.StudentMapper;

@Service
@DataSourceRouting(DataSourceType.ORACLE)
public class StudentServiceImpl {

  @Autowired
  private StudentMapper studentMapper;

  // @DataSourceRouting(DataSourceType.MYSQL)
  public List<Map<String, Object>> getAllStudent() {
    return studentMapper.listStudent();
  }

}
