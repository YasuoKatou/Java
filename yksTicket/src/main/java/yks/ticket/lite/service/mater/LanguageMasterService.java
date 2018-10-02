package yks.ticket.lite.service.mater;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.master.LanguageMasterDao;
import yks.ticket.lite.dto.LanguageResponseDto;

/**
 * 言語マスタサービス.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class LanguageMasterService {
	/** 言語マスタDao. */
	@Autowired private LanguageMasterDao languageMasterDao;

	/**
	 * 言語一覧を取得する.
	 * @return 言語一覧
	 * @since 0.0.1
	 */
	public List<LanguageResponseDto> getLanguageList() {
		List<LanguageResponseDto> list = new ArrayList<>();
		this.languageMasterDao.findAll().forEach(entity -> {
			list.add(LanguageResponseDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.country(entity.getCountry())
					.remarks(entity.getRemarks())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return list;
	}
}