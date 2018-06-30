package yksolution.demo.core.filter;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yksolution.demo.TestBase;

public class LoginFilterTest extends TestBase {

  private static final String myName = LoginFilterTest.class.getSimpleName();

  @Autowired
  private LoginFilter filter;

  @Test
  public void test() {
    assertNotNull("テストクラス(LoginFilterTest)のDI失敗", filter);
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