package yks.ticket.lite.controller.master;

import static org.junit.Assert.fail;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;

/**
 * ロールマスタコントローラのテストを行う.
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
public class RollMasterControllerTest {
	/** コントローラを呼び出すクラス */
	@Autowired private WebTestClient webClient;

	/**
	 * ロールマスタ取得を確認する.
	 *
	 * @since 0.0.1
	 */
	@Test
	public void test_master() {
		try {
			// コントローラ呼び出し
			webClient.post().uri("/yksticket/roll/master")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(Charset.forName("UTF-8"))
				.exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			// レスポンスを確認
			.expectBody()
			.jsonPath("$.group").isNotEmpty()
			.jsonPath("$.group").isArray()
			.jsonPath("$.group.length()").isEqualTo(4)
			.jsonPath("$.group[0].id").isEqualTo(Long.valueOf(1000L))
			.jsonPath("$.group[0].name").isEqualTo("プロジェクト")
			.jsonPath("$.group[1].id").isEqualTo(Long.valueOf(2000L))
			.jsonPath("$.group[1].name").isEqualTo("ファイル")
			.jsonPath("$.group[2].id").isEqualTo(Long.valueOf(3000L))
			.jsonPath("$.group[2].name").isEqualTo("チケット")
			.jsonPath("$.group[3].id").isEqualTo(Long.valueOf(4000L))
			.jsonPath("$.group[3].name").isEqualTo("Wiki")
			.jsonPath("$.item").isNotEmpty()
			.jsonPath("$.item").isArray()
			.jsonPath("$.item.length()").isEqualTo(12)
			.jsonPath("$.item[0].id").isEqualTo(Long.valueOf(1001L))
			.jsonPath("$.item[0].name").isEqualTo("プロジェクトの編集")
			.jsonPath("$.item[0].group_id").isEqualTo(Long.valueOf(1000L))
			.jsonPath("$.item[1].id").isEqualTo(Long.valueOf(1002L))
			.jsonPath("$.item[1].name").isEqualTo("プロジェクトの終了/再開")
			.jsonPath("$.item[1].group_id").isEqualTo(Long.valueOf(1000L))
			.jsonPath("$.item[10].id").isEqualTo(Long.valueOf(4001L))
			.jsonPath("$.item[10].name").isEqualTo("Wikiの閲覧")
			.jsonPath("$.item[10].group_id").isEqualTo(Long.valueOf(4000L))
			.jsonPath("$.item[11].id").isEqualTo(Long.valueOf(4002L))
			.jsonPath("$.item[11].name").isEqualTo("Wikiページの編集")
			.jsonPath("$.item[11].group_id").isEqualTo(Long.valueOf(4000L))
			;
		} catch (Exception ex) {
			ex.getStackTrace();
			fail("projects test error");
		}
	}
}