package yks.ticket.lite.etc;

import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;
import yks.ticket.lite.common.TicketApConstatnt.FilePath;
import yks.ticket.lite.dto.ApInitData;
import yks.ticket.lite.dto.ApInitData.ApInitTable;

/**
 * 本クラスは、実装前にFramework内で正しく動作することを確認するための実装を行う.
 * 従って、このクラスはメンテナンスの保証はない.
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
public class JsonTest {
	@Autowired private ResourceLoader resourceLoader;
	@Autowired private ApplicationContext applicationContext;
	/**
	 * 初期データ登録用のJSONデータ読込を確認する.
	 * 注意：テスト時は、対象テーブルを空にしてから行うこと。
	 */
	@Test
	public void test_Json_read() {
		ObjectMapper mapper = new ObjectMapper();
		Resource resource = resourceLoader.getResource("classpath:" + FilePath.AP_INIT_JSON);
		try {
			// JSON読込み ＆ 初期データ投入情報Dtoへのマッピング
			ApInitData apInitData = mapper.readValue(resource.getFile(), ApInitData.class);
			// テーブルごとに初期データの投入を実施
			for (ApInitTable table : apInitData.getInitDataList()) {
				System.out.println(table.getTitle());
				// Daoをbeanから取得
				Object dao = applicationContext.getBean(table.getDao());
				// エンティティを取得
				Class entityClass = Class.forName(table.getEntity());
				// 初期データを登録
				int count = 0;
				for (Map<String, Object> rec : table.getValues()) {
					String json = mapper.writeValueAsString(rec);
					Object entity = mapper.readValue(json, entityClass);
					System.out.println("entity : " + entity.toString());
					Method method = dao.getClass().getMethod(table.getMethod(), entityClass);
					System.out.println("method : " + method.toString());
					count += (int)method.invoke(dao, entity);
				}
				System.out.println("登録件数 : " + count);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Not yet implemented : " + ex.toString());
		}
	}
}