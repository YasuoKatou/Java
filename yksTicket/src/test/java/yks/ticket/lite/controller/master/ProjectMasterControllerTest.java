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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;

/**
 * プロジェクトマスタメンテナンスコントローラのテストを行う.
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
public class ProjectMasterControllerTest {
	/** コントローラを呼び出すクラス */
	@Autowired private WebTestClient webClient;

	/**
	 * プロジェクト一覧取得を行う（終了プロジェクトも含む）.
	 */
	@Test
	@DatabaseSetup("classpath:ProjectMasterDaoTest_D01/")
	public void test_projects_all() {
		try {
			// リクエストパラメータの作成
			Map<String, String> requestParamMap = new HashMap<>();
			requestParamMap.put("alive", null);
			BodyInserter<Object, ReactiveHttpOutputMessage> requestParam = BodyInserters.fromObject(requestParamMap);
			// コントローラ呼び出し
			webClient.post().uri("/yksticket/maintenance/projects")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(Charset.forName("UTF-8"))
				.body(requestParam)
				.exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			// レスポンスを確認
			.expectBody()
			.jsonPath("$[0].id").isEqualTo(Long.valueOf(11L))
			.jsonPath("$[0].name").isEqualTo("テストプロジェクト")
			.jsonPath("$[0].description").isEqualTo("単体テストデータ#1")
			.jsonPath("$[0].manager_id").isEqualTo(Long.valueOf(101L))
			.jsonPath("$[0].alive").isEqualTo("yes")
			.jsonPath("$[0].opened").isEqualTo("no")
			.jsonPath("$[0].manager_name1").isEqualTo("Ａｂｂｏｔ")
			.jsonPath("$[0].manager_name2").isEqualTo("Ａａｒｏｎ")
			.jsonPath("$[1].id").isEqualTo(Long.valueOf(12L))
			.jsonPath("$[1].name").isEqualTo("プロジェクト Ｘ")
			.jsonPath("$[1].description").isEqualTo("単体テストデータ#2")
			.jsonPath("$[1].manager_id").isEqualTo(Long.valueOf(102L))
			.jsonPath("$[1].alive").isEqualTo("no")
			.jsonPath("$[1].opened").isEqualTo("yes")
			.jsonPath("$[1].manager_name1").isEqualTo("Ａｂｉｔｂｏｌ")
			.jsonPath("$[1].manager_name2").isEqualTo("Ａｃｈｉｌｌｅ－Ｃｌａｕｄｏ");
		} catch (Exception ex) {
			ex.getStackTrace();
			fail("projects test error");
		}
	}

	/**
	 * プロジェクト一覧取得を行う（進行中プロジェクトのみ）.
	 */
	@Test
	@DatabaseSetup("classpath:ProjectMasterDaoTest_D01/")
	public void test_projects_alive() {
		try {
			// リクエストパラメータの作成
			Map<String, String> requestParamMap = new HashMap<>();
			requestParamMap.put("alive", "yes");
			BodyInserter<Object, ReactiveHttpOutputMessage> requestParam = BodyInserters.fromObject(requestParamMap);
			// コントローラ呼び出し
			webClient.post().uri("/yksticket/maintenance/projects")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(Charset.forName("UTF-8"))
				.body(requestParam)
				.exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			// レスポンスを確認
			.expectBody()
			.jsonPath("$[0].id").isEqualTo(Long.valueOf(11L))
			.jsonPath("$[0].name").isEqualTo("テストプロジェクト")
			.jsonPath("$[0].description").isEqualTo("単体テストデータ#1")
			.jsonPath("$[0].manager_id").isEqualTo(Long.valueOf(101L))
			.jsonPath("$[0].alive").isEqualTo("yes")
			.jsonPath("$[0].opened").isEqualTo("no")
			.jsonPath("$[0].manager_name1").isEqualTo("Ａｂｂｏｔ")
			.jsonPath("$[0].manager_name2").isEqualTo("Ａａｒｏｎ");
		} catch (Exception ex) {
			ex.getStackTrace();
			fail("projects test error");
		}
	}
}