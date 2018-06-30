package yksolution.demo.core.filter;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yksolution.demo.TestBase;

public class AuthorityFilterTest extends TestBase {

  private static final String myName = AuthorityFilterTest.class.getSimpleName();

  private static final String TEST_DATA_PATH = "";

  @Autowired
  private AuthorityFilter filter;

  @Test
  public void test() {
    assertNotNull("テストクラス(AuthorityFilter)のDI失敗", filter);
  }

  @Before
  public void prepareTest() {
    super.startTransaction();
    super.prepareTest(TEST_DATA_PATH);
  }

  @After
  public void finishTest() {
    super.rollback();
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