package yks.ticket.lite.controller.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.controller.ControllerBase;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.RequestDto;
import yks.ticket.lite.dto.StatusResponseDto;
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
public class UserMasterController extends ControllerBase {
	/** ユーザマスタメンテンスサービス. */
	@Autowired private UserService userService;

	/**
	 * ユーザ一覧を取得する.
	 * 
	 * @param inDto リクエストDto
	 * @return ユーザ一覧
	 * @since 0.0.1
	 */
	@PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserDto> getUseList(@RequestBody RequestDto inDto) {
		// ログイン情報を取得
		LoginDto login = super.getLogin(inDto);
		if (login != null) {
			// ユーザ一覧を取得
			return this.userService.getUserList();
		} else {
			return null;
		}
	}

	/**
	 * ユーザ情報の追加を行う.
	 * 
	 * @param userDto ユーザ情報
	 * @return ユーザ情報
	 * @exception 処理異常
	 * @since 0.0.1
	 */
	@PostMapping(value="/append", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserDto appendUser(@RequestBody UserDto userDto) throws Exception {
		// ログイン情報を取得
		LoginDto login = super.getLogin(userDto);
		// ユーザ情報を登録する
		return this.userService.appendUser(login, userDto);
	}

	/**
	 * ユーザ情報の更新を行う.
	 * @param userDto ユーザ情報
	 * @return 更新結果
	 * @throws Exception 更新失敗
	 */
	@PostMapping(value="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StatusResponseDto updateUser(@RequestBody UserDto userDto) throws Exception {
		// ログイン情報を取得
		LoginDto login = super.getLogin(userDto);
		// ユーザ情報を更新する
		return this.userService.updateUser(login, userDto);
	}

	/**
	 * ユーザ情報を取得する.
	 * 
	 * @param userDto ユーザ情報取得リクエスト
	 * @return ユーザ情報
	 * @throws Exception 取得失敗
	 * @since 0.0.1
	 */
	@PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserDto getUserInfo(@RequestBody UserDto userDto) throws Exception {
		return this.userService.getUserInfo(userDto);
	}
}