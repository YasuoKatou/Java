package yks.ticket.lite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.dto.LoginRequestDto;
import yks.ticket.lite.dto.LoginResponseDto;
import yks.ticket.lite.dto.LogoutRequestDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.service.LoginService;

/**
 * ログインサービスコントローラ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket")
public class Login {
	/** ログインサービス */
	@Autowired private LoginService loginService;
	/** httpセッション. */
	@Autowired HttpSession session;

	/**
	 * ログイン
	 * 
	 * @param inDto ログインリクエストDto.
	 * @return ログインレスポンスDto.
	 * @since 0.0.1
	 */
	@PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LoginResponseDto login(@RequestBody LoginRequestDto inDto) {
		// セッションIDをリクエストDtoに設定
		inDto.setSession_id(session.getId());
		// ログイン処理
		return this.loginService.doLogin(inDto);
	}

	/**
	 * ログアウト
	 * 
	 * @param inDto ログアウトリクエストDto.
	 * @return 処理結果を戻すDto.
	 * @since 0.0.1
	 */
	@PostMapping(value="/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StatusResponseDto logout(@RequestBody LogoutRequestDto inDto) {
		// ログアウト処理
		return this.loginService.doLogout(inDto);
	}
}