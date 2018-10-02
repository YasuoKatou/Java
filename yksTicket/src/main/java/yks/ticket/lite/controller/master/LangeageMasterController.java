package yks.ticket.lite.controller.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.dto.LanguageResponseDto;
import yks.ticket.lite.service.mater.LanguageMasterService;

/**
 * 言語マスタコントローラ
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/lang")
public class LangeageMasterController {
	/** 言語マスタサービス. */
	@Autowired private LanguageMasterService languageMasterService;

	/**
	 * 言語一覧を取得する.
	 * 
	 * @return 言語一覧
	 * @since 0.0.1
	 */
	@PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<LanguageResponseDto> getLanguageList() {
		return this.languageMasterService.getLanguageList();
	}
}