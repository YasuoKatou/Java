package yks.ticket.lite.service.mater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.UserDto;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * ユーザマスタメンテンスサービス
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class UserService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** ユーザマスタDao. */
	@Autowired private UserMasterDao userMasterDao;

	/**
	 * ユーザ情報の追加を行う.
	 * 
	 * @param login ログイン情報
	 * @param inDto ユーザ情報
	 * @return ユーザ情報
	 * @exception 登録失敗
	 * @since 0.0.1
	 */
	public UserDto appendUser(LoginDto login, UserDto inDto) throws Exception {
		// 登録データの編集
		UserMasterEntity entity = UserMasterEntity.builder()
				.login_id(inDto.getLogin_id())
				.passwd(inDto.getPasswd())
				.name1(inDto.getName1())
				.name2(inDto.getName2())
				.email(inDto.getEmail())
				.language_id(inDto.getLanguage_id())
				.build();
		entity.setCreateUserId(login.getId());
		try {
			// 登録
			int count = this.userMasterDao.insert(entity);
			// 登録の確認
			if (count != 1) {
				// 登録失敗
				logger.error("登録失敗 : " + entity.toString());
				throw new Exception("登録失敗");
			}
		} catch (Exception ex) {
			// 登録失敗
			logger.error("登録失敗(DB異常) : " + ex.toString());
			throw new Exception("登録失敗(DB異常)");
		}
		// ユーザIDの取得
		entity = this.userMasterDao.findByLoginId(entity);
		if (entity == null) {
			// 登録後の抽出失敗
			logger.error("ユーザID取得失敗 : " + entity.toString());
			throw new Exception("登録失敗(ユーザID取得) : ");
		}
		return UserDto.builder()
				.id(entity.getId())
				.build();
	}
}