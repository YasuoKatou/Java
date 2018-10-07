package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import yks.ticket.lite.dao.RollNameDao;
import yks.ticket.lite.dao.RollSettingDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.RollNameResponseDto;
import yks.ticket.lite.dto.RollRecordRequestDto;
import yks.ticket.lite.dto.RollSettingRequestDto;
import yks.ticket.lite.dto.RollSettingResponseDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.entity.RollNameEntity;
import yks.ticket.lite.entity.RollSettingEntity;

/**
 * ロール情報設定サービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class RollSettingService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

	/**
	 * 新規にロールの登録を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto ロール設定情報登録リクエストDto.
	 * @return 処理結果Dto
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto appendRoll(LoginDto login, RollRecordRequestDto inDto) throws Exception {
		Long userId = login.getId();
		Long nameId = this.rollNameDao.findMaxId() + 1;
		// ロール名称の登録
		RollNameEntity rollName = RollNameEntity.builder()
				.id(nameId)
				.name(inDto.getName())
				.description(inDto.getDescript())
				.build();
		rollName.setCreateUserId(userId);
		if (this.rollNameDao.insert(rollName) != 1) {
			logger.error("ロール名称登録失敗 : " + rollName.toString());
			throw new Exception("ロール名称登録失敗");
		}
		// ロール項目の登録
		this.insertRollSetting(userId, nameId, inDto.getItems());

		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * ロール設定情報の更新を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto ロール設定更新情報
	 * @return 処理結果Dto.
	 * @throws Exception 更新失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto updateRoll(LoginDto login, RollRecordRequestDto inDto) throws Exception {
		Long userId = login.getId();
		Long nameId = inDto.getId();
		// ロール名称の更新
		RollNameEntity rollName = RollNameEntity.builder()
				.id(nameId)
				.name(inDto.getName())
				.description(inDto.getDescript())
				.build();
		rollName.setUpdateUserId(userId);
		if (this.rollNameDao.updateByPk(rollName) != 1) {
			logger.error("ロール名称更新失敗 : " + rollName.toString());
			throw new Exception("ロール名称新失敗");
		}
		// ロール項目の更新（Delete/Insert）
		this.rollSettingDao.deleteByRollNameId(nameId);
		this.insertRollSetting(userId, nameId, inDto.getItems());

		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * ロール項目を設定する.
	 *
	 * @param userId 登録ユーザID
	 * @param nameId ロール名称ID
	 * @param items ロール項目
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	private void insertRollSetting(Long userId, Long nameId
			, List<RollRecordRequestDto.RollSetting> items) throws Exception {
		RollSettingEntity rollSetting = RollSettingEntity.builder()
				.build();
		rollSetting.setCreateUserId(userId);
		for (RollRecordRequestDto.RollSetting info : items) {
			rollSetting.setRollNameId(nameId);
			rollSetting.setRollItemId(info.getId());
			rollSetting.setRollGroupId(info.getGroup_id());
			if (this.rollSettingDao.insert(rollSetting) != 1) {
				logger.error("ロール設定情報登録失敗 : " + rollSetting.toString());
				throw new Exception("ロール設定情報登録失敗");
			}
		}
	}
}