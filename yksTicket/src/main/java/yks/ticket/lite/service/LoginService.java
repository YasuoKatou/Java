package yks.ticket.lite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.SessionDao;
import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.dto.LoginRequestDto;
import yks.ticket.lite.dto.LoginResponseDto;
import yks.ticket.lite.entity.SessionEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

@Service
public class LoginService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** ユーザマスタ */
	@Autowired private UserMasterDao userMasterDao;
	/** セッション管理テーブルDao. */
	@Autowired private SessionDao sessionDao;

	public LoginResponseDto doLogin(LoginRequestDto inDto) {
		// ユーザテーブルを確認
		// TODO パスワードの暗号化を実装
		UserMasterEntity userMaster = userMasterDao.findByLoginId(
				UserMasterEntity.builder().login_id(inDto.getLogin_id()).build());
		if (userMaster == null) {
			logger.info("ユーザマスタ取得失敗 " + inDto.toString());
			return LoginResponseDto.builder()
					.status(LoginResponseDto.FAIL)
					.build();
		}
		// セッションテーブルを作成
		SessionEntity session = SessionEntity.builder()
				.session_id(inDto.getSession_id())
				.user_id(userMaster.getId())
				.build();
		int count = sessionDao.insert(session);
		if (count != 1) {
			logger.error("セッション管理登録失敗 " + session.toString());
		}
		// 処理終了
		return LoginResponseDto.builder()
				.status(LoginResponseDto.SUCCESS)
				.session_id(inDto.getSession_id())
				.build();
	}
}
