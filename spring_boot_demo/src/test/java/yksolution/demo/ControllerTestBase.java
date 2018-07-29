package yksolution.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class ControllerTestBase extends TestBase {
  /** コントローラを呼び出すクラス */
  @Autowired protected WebTestClient webClient;
}