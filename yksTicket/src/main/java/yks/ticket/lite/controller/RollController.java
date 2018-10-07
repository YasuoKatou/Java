package yks.ticket.lite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.RollNameResponseDto;
import yks.ticket.lite.dto.RollRecordRequestDto;
import yks.ticket.lite.dto.RollSettingRequestDto;
import yks.ticket.lite.dto.RollSettingResponseDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.service.RollSettingService;

/**
 * ロールサービスコントローラ.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/roll")
public class RollController extends ControllerBase {
	/** ロール情報設定サービス. */
	@Autowired private RollSettingService rollSettingService;

	/**
	 * ロール名称一覧を取得する.
	 *
	 * @return ロール情報レスポンスDto.
	 * @since 0.0.1
	 */
	@PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<RollNameResponseDto> getRollList() {
		return this.rollSettingService.getRollList();
	}

	/**
	 * ロール設定情報を取得
	 *
	 * @param inDto ロール設定情報取得リクエストDto.
	 * @return ロール設定レスポンスDto.
	 * @since 0.0.1
	 */
	@PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RollSettingResponseDto getRoll(@RequestBody RollSettingRequestDto inDto) {
		return this.rollSettingService.getRoll(inDto);
	}

	/**
	 * 新規にロールの登録を行う.
	 *
	 * @param inDto ロール設定情報登録リクエストDto.
	 * @return 処理結果Dto
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@PostMapping(value="/append", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StatusResponseDto appendRoll(@RequestBody RollRecordRequestDto inDto) throws Exception {
		// ログイン情報を取得
		LoginDto login = super.getLogin(inDto);
		// ロールの新規登録
		return this.rollSettingService.appendRoll(login, inDto);
	}

	/**
	 * ロールの更新を行う.
	 *
	 * @param inDto ロール設定情報登録リクエストDto.
	 * @return 処理結果Dto
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@PostMapping(value="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StatusResponseDto updateRoll(@RequestBody RollRecordRequestDto inDto) throws Exception {
		// ログイン情報を取得
		LoginDto login = super.getLogin(inDto);
		// ロールの更新
		return this.rollSettingService.updateRoll(login, inDto);
	}
}