package yks.ticket.lite.dao.master;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;
import yks.ticket.lite.entity.master.RollItemMasterEntity;
import yks.ticket.lite.service.DBInitService;

/**
 * ロール項目マスタDaoテストクラス.
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
public class RollItemMasterDaoTest {
	/** ロール項目マスタDao. */
	@Autowired private RollItemMasterDao rollItemMasterDao;

	/**
	 * ロール項目マスタ登録（IDの自動採番）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_01() {
		try {
			RollItemMasterEntity entity = RollItemMasterEntity.builder()
					.name("ロール名")
					.group_id(Long.valueOf(101L))
					.build();
			entity.setCreateUserId(1L);
			int count = this.rollItemMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * ロール項目マスタ登録（ID指定で成功する）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_02() {
		try {
			RollItemMasterEntity entity = RollItemMasterEntity.builder()
					.id(Long.MAX_VALUE)
					.name("ロール名")
					.group_id(Long.valueOf(101L))
					.build();
			entity.setCreateUserId(1L);
			int count = this.rollItemMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * ロール項目マスタの全取得を確認する.（チェックは全て行わない）
	 * @since 0.0.1
	 */
	@Test
	public void test_findAll() {
		List<RollItemMasterEntity> list = this.rollItemMasterDao.findAll();
		assertEquals("取得数 : ", 12, list.size());
		// +0
		RollItemMasterEntity entity = list.get(0);
		assertEquals("+0.識別子", Long.valueOf(1001L), entity.getId());
		assertEquals("+0.項目名称", "プロジェクトの編集", entity.getName());
		assertEquals("+0.グループID", Long.valueOf(1000L), entity.getGroup_id());
		assertNotNull("+0.作成日付", entity.getCreateDate());
		assertEquals("+0.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+0.バージョン", 1, entity.getVersionNo().intValue());
		// +1
		entity = list.get(1);
		assertEquals("+1.識別子", Long.valueOf(1002L), entity.getId());
		assertEquals("+1.項目名称", "プロジェクトの終了/再開", entity.getName());
		assertEquals("+1.グループID", Long.valueOf(1000L), entity.getGroup_id());
		assertNotNull("+1.作成日付", entity.getCreateDate());
		assertEquals("+1.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+1.バージョン", 1, entity.getVersionNo().intValue());
		// +8
		entity = list.get(8);
		assertEquals("+8.識別子", Long.valueOf(3004L), entity.getId());
		assertEquals("+8.項目名称", "チケットの編集", entity.getName());
		assertEquals("+8.グループID", Long.valueOf(3000L), entity.getGroup_id());
		assertNotNull("+8.作成日付", entity.getCreateDate());
		assertEquals("+8.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+8.バージョン", 1, entity.getVersionNo().intValue());
		// +10
		entity = list.get(10);
		assertEquals("+10.識別子", Long.valueOf(4001L), entity.getId());
		assertEquals("+10.項目名称", "Wikiの閲覧", entity.getName());
		assertEquals("+10.グループID", Long.valueOf(4000L), entity.getGroup_id());
		assertNotNull("+10.作成日付", entity.getCreateDate());
		assertEquals("+10.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+10.バージョン", 1, entity.getVersionNo().intValue());
		// +11
		entity = list.get(11);
		assertEquals("+11.識別子", Long.valueOf(4002L), entity.getId());
		assertEquals("+11.項目名称", "Wikiページの編集", entity.getName());
		assertEquals("+11.グループID", Long.valueOf(4000L), entity.getGroup_id());
		assertNotNull("+11.作成日付", entity.getCreateDate());
		assertEquals("+11.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+11.バージョン", 1, entity.getVersionNo().intValue());
	}
}