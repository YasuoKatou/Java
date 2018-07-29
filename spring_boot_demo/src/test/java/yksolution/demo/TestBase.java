package yksolution.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestBase {

  protected void startTransaction() {
	  
  }

  protected void prepareTest(String dataPath) {
	  
  }

  protected void rollback() {
	  
  }

  @Test
  public void dummyTest() {
    //「No runnable methods」対策
  }
}