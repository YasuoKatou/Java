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
import yks.ticket.lite.entity.RollNameEntity;

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
	public RollSettingResponseDto getRoll(RollSettingRequestDto inDto) {
		// ロール設定項目を取得
		List<RollSettingResponseDto.RollSetting> list = new ArrayList<>();
		this.rollSettingDao.findSetting(inDto.getId()).forEach(entity -> {
			list.add(RollSettingResponseDto.RollSetting.builder()
					.id(entity.getRollItemId())
					.group_id(entity.getRollGroupId())
					.build());
		});
		// ロール名称を取得
		RollNameEntity nameEntity = this.rollNameDao.findByPk(inDto.getId());
		RollSettingResponseDto outDto = RollSettingResponseDto.builder()
				.id(nameEntity.getId())
				.name(nameEntity.getName())
				.descript(nameEntity.getDescription())
				.items(list)
				.build();
		return outDto;
	}
}