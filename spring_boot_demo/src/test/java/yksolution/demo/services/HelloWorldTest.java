package yksolution.demo.services;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldTest {

  private static final String myName = HelloWorldTest.class.getSimpleName();

  /** このテストクラスがテストするクラス. */
  @Autowired
  private HelloWorld helloWorld;

  @Test
  public void test() {
    assertNotNull("テストクラス(HelloWorld)の DI失敗", helloWorld);
  }

  @BeforeClass
  public static void beforeTest() {
    System.out.println("[" + myName + "] Test start");
  }

  @AfterClass
  public static void afterTest() {
    System.out.println("[" + myName + "] Test finished");
  }
}