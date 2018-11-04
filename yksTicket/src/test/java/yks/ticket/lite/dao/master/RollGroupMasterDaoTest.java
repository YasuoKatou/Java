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
import yks.ticket.lite.entity.master.RollGroupMasterEntity;
import yks.ticket.lite.service.DBInitService;

/**
 * ロールグループマスタDaoテストクラス.
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
public class RollGroupMasterDaoTest {
	/** ロールグループマスタDao. */
	@Autowired private RollGroupMasterDao rollGroupMasterDao;

	/**
	 * ロールグループマスタ登録（IDの自動採番）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_01() {
		try {
			RollGroupMasterEntity entity = RollGroupMasterEntity.builder()
					.name("ロールグループ名")
					.build();
			entity.setCreateUserId(1L);
			int count = this.rollGroupMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * ロールグループマスタ登録（ID指定で成功する）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_02() {
		try {
			RollGroupMasterEntity entity = RollGroupMasterEntity.builder()
					.id(Long.MAX_VALUE)
					.name("ロールグループ名")
					.build();
			entity.setCreateUserId(1L);
			int count = this.rollGroupMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * ロールグループマスタの全取得を確認する.
	 * @since 0.0.1
	 */
	@Test
	public void test_findAll() {
		List<RollGroupMasterEntity> list = this.rollGroupMasterDao.findAll();
		assertEquals("取得数 : ", 4, list.size());
		// +0
		RollGroupMasterEntity entity = list.get(0);
		assertEquals("+0.識別子", Long.valueOf(1000L), entity.getId());
		assertEquals("+0.グループ名称", "プロジェクト", entity.getName());
		assertNotNull("+0.作成日付", entity.getCreateDate());
		assertEquals("+0.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+0.バージョン", 1, entity.getVersionNo().intValue());
		// +1
		entity = list.get(1);
		assertEquals("+1.識別子", Long.valueOf(2000L), entity.getId());
		assertEquals("+1.グループ名称", "ファイル", entity.getName());
		assertNotNull("+1.作成日付", entity.getCreateDate());
		assertEquals("+1.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+1.バージョン", 1, entity.getVersionNo().intValue());
		// +2
		entity = list.get(2);
		assertEquals("+2.識別子", Long.valueOf(3000L), entity.getId());
		assertEquals("+2.グループ名称", "チケット", entity.getName());
		assertNotNull("+2.作成日付", entity.getCreateDate());
		assertEquals("+2.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+2.バージョン", 1, entity.getVersionNo().intValue());
		// +3
		entity = list.get(3);
		assertEquals("+2.識別子", Long.valueOf(4000L), entity.getId());
		assertEquals("+2.グループ名称", "Wiki", entity.getName());
		assertNotNull("+2.作成日付", entity.getCreateDate());
		assertEquals("+2.作成者ID", DBInitService.ADMIN_USER_ID, entity.getCreateUserId());
		assertEquals("+2.バージョン", 1, entity.getVersionNo().intValue());
	}
}