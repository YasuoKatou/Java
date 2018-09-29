package yks.ticket.lite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.UserDto;
import yks.ticket.lite.service.mater.UserService;

/**
 * ユーザマスタメンテナンスコントローラ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/user")
public class User extends ControllerBase {
	/** ユーザマスタメンテンスサービス. */
	@Autowired private UserService userService;

	/**
	 * ユーザの追加を行う.
	 * 
	 * @param userDto ユーザ情報
	 * @return ユーザ情報
	 * @exception 処理異常
	 * @since 0.0.1
	 */
	@PostMapping(value="/append", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserDto appendUser(@RequestBody UserDto userDto) throws Exception {
		LoginDto login = super.getLogin(userDto);
		return userService.appendUser(login, userDto);
	}
}