package wechat_public_platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iboxpay.Application;
import com.iboxpay.mapper.EmpMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MapperTest {

  @Autowired
  private EmpMapper empMapper;

  @Test
  public void test() {
    System.err.println(empMapper.listEmps());
  }

}
