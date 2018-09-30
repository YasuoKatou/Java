package yks.ticket.lite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import yks.ticket.lite.dao.SessionDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.RequestHeaderDto;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * コントローラの基本クラス.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public class ControllerBase {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** セッション管理Dao. */
	@Autowired private SessionDao sessionDao;

	/**
	 * ログイン情報を取得する.
	 * 
	 * @param requestHeader リクエストヘッダー
	 * @return ログイン情報
	 * @since 0.0.1
	 */
	protected LoginDto getLogin(RequestHeaderDto requestHeader) {
		try {
			UserMasterEntity entity = sessionDao.findUserBySessionId(requestHeader.getHeader().getSession_id());
			if (entity == null) {
				logger.error("no user information : " + requestHeader.getHeader().getSession_id());
				return null;
			}
			return LoginDto.builder()
					.id(entity.getId())
					.name1(entity.getName1())
					.name2(entity.getName2())
					.email(entity.getEmail())
					.language_id(entity.getLanguage_id())
					.build();
		} catch (Exception ex) {
			logger.error("セッション情報取得失敗 : " + ex.toString());
			return null;
		}
	}
}