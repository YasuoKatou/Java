package yks.ticket.lite.service.mater;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.StatusResponseDto;
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
	 * ユーザ一覧を取得する.
	 * 
	 * @return ユーザ情報一覧
	 * @since 0.0.1
	 */
	public List<UserDto> getUserList() {
		List<UserMasterEntity> entity = this.userMasterDao.findListAll();
		List<UserDto> userList = new ArrayList<>();
		entity.forEach(userMaster -> {
			userList.add(UserDto.builder()
					.id(userMaster.getId())
					.login_id(userMaster.getLogin_id())
					.passwd(userMaster.getPasswd())
					.name1(userMaster.getName1())
					.name2(userMaster.getName2())
					.email(userMaster.getEmail())
					.language_id(userMaster.getLanguage_id())
					.language_name(userMaster.getLanguage().getName())
					.language_country(userMaster.getLanguage().getCountry())
					.build());
		});
		return userList;
	}

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

	/**
	 * ユーザ情報の更新を行う.
	 * 
	 * @param login ログイン情報
	 * @param inDto ユーザ情報
	 * @return 更新結果
	 * @throws Exception 更新失敗
	 * @since 0.0.1
	 */
	public StatusResponseDto updateUser(LoginDto login, UserDto inDto) throws Exception {
		// 更新データの編集
		UserMasterEntity entity = UserMasterEntity.builder()
				.id(inDto.getId())
				.login_id(inDto.getLogin_id())
				.passwd(inDto.getPasswd())
				.name1(inDto.getName1())
				.name2(inDto.getName2())
				.email(inDto.getEmail())
				.language_id(inDto.getLanguage_id())
				.build();
		entity.setUpdateUserId(login.getId());
		try {
			// 更新
			int count = this.userMasterDao.update(entity);
			// 更新の確認
			if (count != 1) {
				// 更新失敗
				logger.error("更新失敗 : " + entity.toString());
				throw new Exception("更新失敗");
			}
		} catch (Exception ex) {
			// 更新失敗
			logger.error("更新失敗(DB異常) : " + ex.toString());
			throw new Exception("更新失敗(DB異常)");
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * ユーザ情報を取得する.
	 * 
	 * @param inDto ユーザ情報取得要求
	 * @return ユーザ情報
	 * @throws Exception 取得失敗
	 * @since 0.0.1
	 */
	public UserDto getUserInfo(UserDto inDto) throws Exception {
		UserMasterEntity entity;
		try {
			entity = this.userMasterDao.findById(UserMasterEntity.builder()
				.id(inDto.getId())
				.build());
		} catch (Exception ex) {
			logger.error("取得失敗(DB異常) : " + inDto.toString());
			throw new Exception("取得失敗(DB異常)");
		}
		if (entity == null) {
			logger.error("取得失敗 : " + inDto.toString());
			throw new Exception("取得失敗");
		}
		return UserDto.builder()
				.id(entity.getId())
				.login_id(entity.getLogin_id())
				.passwd(entity.getPasswd())
				.name1(entity.getName1())
				.name2(entity.getName2())
				.email(entity.getEmail())
				.language_id(entity.getLanguage_id())
				.language_name(entity.getLanguage().getName())
				.language_country(entity.getLanguage().getCountry())
				.build();
	}
}