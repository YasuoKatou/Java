package yks.ticket.lite.controller.master;

import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;

/**
 * ログインコントローラのテストを行う.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, value = "server.port=8080")
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
public class LoginControllerTest {
	/** コントローラを呼び出すクラス */
	@Autowired private WebTestClient webClient;

	/**
	 * 管理者でログイン（正常時）
	 */
	@Test
	public void test_login_admin() {
		try {
			// リクエストパラメータの作成
			Map<String, String> requestParamMap = new HashMap<>();
			requestParamMap.put("login_id", "admin");
			requestParamMap.put("passwd", "admin");
			BodyInserter<Object, ReactiveHttpOutputMessage> requestParam = BodyInserters.fromObject(requestParamMap);
			// コントローラ呼び出し
			webClient.post().uri("/yksticket/login")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(Charset.forName("UTF-8"))
				.body(requestParam)
				.exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			// レスポンスを確認
			.expectBody()
			.jsonPath("$.status").isEqualTo("OK")
			.jsonPath("$.session_id").isNotEmpty();
		} catch (Exception ex) {
			ex.getStackTrace();
			fail("projects test error");
		}
	}

	/**
	 * 管理者でログイン（ログインID未設定）
	 */
	@Test
	public void test_no_loginid() {
		try {
			// リクエストパラメータの作成
			Map<String, String> requestParamMap = new HashMap<>();
			requestParamMap.put("login_id", "hogehoge");
			requestParamMap.put("passwd", "admin");
			BodyInserter<Object, ReactiveHttpOutputMessage> requestParam = BodyInserters.fromObject(requestParamMap);
			// コントローラ呼び出し
			webClient.post().uri("/yksticket/login")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(Charset.forName("UTF-8"))
				.body(requestParam)
				.exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			// レスポンスを確認
			.expectBody()
			.jsonPath("$.status").isEqualTo("NG")
			.jsonPath("$.session_id").isEmpty();
		} catch (Exception ex) {
			ex.getStackTrace();
			fail("projects test error");
		}
	}
}