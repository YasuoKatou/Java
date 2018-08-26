package yksolution.demo.controllers;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import yksolution.demo.ControllerTestBase;

public class HelloWorldTest extends ControllerTestBase {

  private static final String myName = HelloWorldTest.class.getSimpleName();

  @Test
  public void test_helloworld() {
    System.out.println("[" + myName + "] execute test_helloworld");
    try {
      webClient.get().uri("/helloworld").exchange().expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Hello World !!");
    } catch (Exception e) {
      e.printStackTrace();
      fail("HelloWorld　コントローラ異常終了");
	  }
  }

  @Test
  public void test_hellodemo() {
    System.out.println("[" + myName + "] execute test_hellodemo");
    try {
      webClient.get().uri("/hellodemo").exchange().expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Hello Demo !!");
    } catch (Exception e) {
      e.printStackTrace();
      fail("HelloWorld　コントローラ異常終了");
    }
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