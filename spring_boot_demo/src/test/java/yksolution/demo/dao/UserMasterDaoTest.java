package yksolution.demo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import yksolution.demo.DaoTestBase;
import yksolution.demo.entity.UserMasterEntity;

/**
 * ユーザ情報テーブルDaoのテストクラス
 * @author Y.Katou
 */
public class UserMasterDaoTest extends DaoTestBase {

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