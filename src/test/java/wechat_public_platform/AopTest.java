package wechat_public_platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iboxpay.Application;
import com.iboxpay.service.StudentServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AopTest {

  @Autowired
  private StudentServiceImpl studentServiceImpl;

  @Test
  public void test() {
    studentServiceImpl.getAllStudent();
  }

}
