package com.iboxpay.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iboxpay.annotation.DataSourceRoutable;
import com.iboxpay.constants.DataSourceType;
import com.iboxpay.mapper.StudentMapper;

@Service
@DataSourceRoutable(DataSourceType.ORACLE)
public class StudentServiceImpl {

  @Autowired
  private StudentMapper studentMapper;

  @Transactional(propagation = Propagation.REQUIRED)
  // @DataSourceRoutable(DataSourceType.MYSQL)
  public List<Map<String, Object>> getAllStudent() {
    return studentMapper.listStudent();
  }

}
