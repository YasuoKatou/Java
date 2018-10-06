package yks.ticket.lite.controller.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.controller.ControllerBase;
import yks.ticket.lite.dto.RollMasterResponseDto;
import yks.ticket.lite.service.mater.RollMasterService;

/**
 * ロールマスタコントローラ.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/roll")
public class RollMasterController extends ControllerBase {
	/** ロールマスタメンテンスサービス. */
	@Autowired private RollMasterService rollMasterService;

	/**
	 * ロールマスタを取得する.
	 *
	 * @return ロールマスタ取得レスポンスDto.
	 * @since 0.0.1
	 */
	@PostMapping(value="/master", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RollMasterResponseDto getRollMaster() {
		return rollMasterService.getRollMaster();
	}
}