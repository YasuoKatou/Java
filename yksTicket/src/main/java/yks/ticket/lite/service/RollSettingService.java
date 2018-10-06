package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.RollNameDao;
import yks.ticket.lite.dao.RollSettingDao;
import yks.ticket.lite.dto.RollNameResponseDto;
import yks.ticket.lite.dto.RollSettingRequestDto;
import yks.ticket.lite.dto.RollSettingResponseDto;

/**
 * ロール情報設定サービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class RollSettingService {
	/** ロール名称Dao. */
	@Autowired private RollNameDao rollNameDao;
	/** ロール設定Dao. */
	@Autowired private RollSettingDao rollSettingDao;

	/**
	 * ロール名称の一覧を取得する.
	 *
	 * @return ロール情報レスポンスDto一覧
	 * @since 0.0.1
	 */
	public List<RollNameResponseDto> getRollList() {
		List<RollNameResponseDto> list = new ArrayList<>();
		this.rollNameDao.findAll().forEach(entity -> {
			list.add(RollNameResponseDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.build());
		});
		return list;
	}

	/**
	 * ロール設定情報を取得する.
	 *
	 * @param inDto ロール設定情報取得リクエストDto.
	 * @return ロール設定レスポンスDto一覧
	 * @since 0.0.1
	 */
	public List<RollSettingResponseDto> getRoll(RollSettingRequestDto inDto) {
		List<RollSettingResponseDto> list = new ArrayList<>();
		this.rollSettingDao.findSetting(inDto.getId()).forEach(entity -> {
			list.add(RollSettingResponseDto.builder()
					.id(entity.getRollNameId())
					.group_id(entity.getRollGroupId())
					.build());
		});
		return list;
	}
}