package yks.ticket.lite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.SessionDao;
import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.dto.LoginRequestDto;
import yks.ticket.lite.dto.LoginResponseDto;
import yks.ticket.lite.dto.RequestDto;
import yks.ticket.lite.dto.StatusResponseDto;
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

	/**
	 * ログイン処理
	 * 
	 * @param inDto ログインリクエストDto.
	 * @return ログインレスポンスDto.
	 * @since 0.0.1
	 */
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
		// 過去のセッション情報を削除
		int count = this.sessionDao.deleteByUserId(userMaster.getId());
		if (count > 0) {
			logger.info("過去のセッション情報を削除 : " + count);
		}
		// セッションテーブルを作成
		SessionEntity session = SessionEntity.builder()
				.session_id(inDto.getSession_id())
				.user_id(userMaster.getId())
				.build();
		count = this.sessionDao.insert(session);
		if (count != 1) {
			logger.error("セッション管理登録失敗 " + session.toString());
		}
		// 処理終了
		return LoginResponseDto.builder()
				.status(LoginResponseDto.SUCCESS)
				.session_id(inDto.getSession_id())
				.build();
	}

	/**
	 * ログアウト処理
	 * 
	 * @param inDto ログアウトリクエストDto.
	 * @return 処理結果を戻すDto.
	 * @since 0.0.1
	 */
	public StatusResponseDto doLogout(RequestDto inDto) {
		String sessionId = inDto.getHeader().getSession_id();
		logger.info("delete session : " + sessionId);
		int count = this.sessionDao.deleteBySessionId(sessionId);
		if (count != 1) {
			logger.error("セッション管理削除失敗 " + sessionId);
			return StatusResponseDto.builder()
					.status(StatusResponseDto.FAIL)
					.build();
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}