package yksolution.demo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yksolution.demo.DemoApplication;
import yksolution.demo.entity.UserMasterEntity;
import yksolution.demo.test.common.CsvDataSetLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
  DemoApplication.class,
  UserMasterDao.class
})
@SpringBootTest
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
/**
 * ユーザ情報テーブルDaoのテストクラス
 * @author Y.Katou
 */
public class UserMasterDaoTest {

  @Autowired private UserMasterDao userMasterDao;

  /**
   * 指定のユーザを取得する.
   * @since 0.0.1
   */
  @Test
  @DatabaseSetup("classpath:UserMasterDaoTest_01/")
  public void testFindUser() {
    String userId = "hoge2";
    UserMasterEntity userMaster = userMasterDao.findUser(userId);
    assertNotNull("Entity", userMaster);
    assertEquals("ユーザ名", userId, userMaster.getUserId());
    assertEquals("パスワード", "page1", userMaster.getPasswd());
    assertEquals("権限", "admin3", userMaster.getAuthority());
  }
}