package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.TicketDao;
import yks.ticket.lite.dao.TicketProgressDao;
import yks.ticket.lite.dao.TicketStatusDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.dto.TicketDto;
import yks.ticket.lite.dto.TicketListRequestDto;
import yks.ticket.lite.dto.TicketListResponseDto;
import yks.ticket.lite.dto.TicketMastersRequestDto;
import yks.ticket.lite.dto.TicketMastersResponseDto;
import yks.ticket.lite.dto.TicketProgressDto;
import yks.ticket.lite.dto.TicketProgressSaveRequestDto;
import yks.ticket.lite.dto.TicketStatusDto;
import yks.ticket.lite.dto.TicketStatusSaveRequestDto;
import yks.ticket.lite.entity.TicketProgressEntity;
import yks.ticket.lite.entity.TicketStatusEntity;

/**
 * チケット管理サービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class TicketService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** チケットテーブルDao. */
	@Autowired private TicketDao ticketDao;
	/** チケットステータスDao. */
	@Autowired private TicketStatusDao ticketStatusDao;
	/** チケット進捗Dao. */
	@Autowired private TicketProgressDao ticketProgressDao;

	/**
	 * 指定プロジェクトのチケットを取得
	 *
	 * @param inDto チケット一覧取得リクエストDto.
	 * @return チケット一覧取得レスポンスDto.
	 * @since 0.0.1
	 */
	public TicketListResponseDto getTicketList(TicketListRequestDto inDto) {
		final List<TicketDto> list = new ArrayList<>();
		this.ticketDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			list.add(TicketDto.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.description(entity.getDescription())
					.status_id(entity.getStatus_id())
					.start_date(entity.getStart_date())
					.finish_date(entity.getFinish_date())
					.progress_id(entity.getProgress_id())
					.project_id(entity.getProject_id())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return TicketListResponseDto.builder()
				.ticketList(list)
				.build();
	}

	/**
	 * チケットに関するマスタを取得する.
	 *
	 * @param inDto チケット関連マスタ取得リクエストDto.
	 * @return チケット関連マスタ取得レスポンスDto.
	 * @since 0.0.1
	 */
	public TicketMastersResponseDto getTicketMasters(TicketMastersRequestDto inDto) {
		// チケットステータス一覧を取得
		final List<TicketStatusDto> statusList = new ArrayList<>();
		this.ticketStatusDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			statusList.add(TicketStatusDto.builder()
					.project_id(entity.getProject_id())
					.id(entity.getId())
					.disp_seq(entity.getDisp_seq())
					.name(entity.getName())
					.versionNo(entity.getVersionNo())
					.build());
		});
		// チケット進捗一覧を取得
		final List<TicketProgressDto> progressList = new ArrayList<>();
		this.ticketProgressDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			progressList.add(TicketProgressDto.builder()
					.project_id(entity.getProject_id())
					.id(entity.getId())
					.disp_seq(entity.getDisp_seq())
					.name(entity.getName())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return TicketMastersResponseDto.builder()
				.statusList(statusList)
				.progressList(progressList)
				.build();
	}

	/**
	 * チケットステータスの登録を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto チケットステータス登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	public StatusResponseDto saveTicketStatus(LoginDto login, TicketStatusSaveRequestDto inDto) throws Exception {
		final TicketStatusEntity entity = TicketStatusEntity.builder().build();
		entity.setProject_id(inDto.getProject_id());
		entity.setCreateUserId(login.getId());
		entity.setUpdateUserId(login.getId());
		Long maxId = this.ticketStatusDao.getMaxId(inDto.getProject_id());
		// 一旦全てを無効に設定
		this.ticketStatusDao.setUnavailable(inDto.getProject_id());
		for (TicketStatusDto statusDto : inDto.getStatusList()) {
			entity.setDisp_seq(statusDto.getDisp_seq());
			entity.setName(statusDto.getName());
			if (statusDto.getId() != null) {
				// 更新
				entity.setId(statusDto.getId());
				if (this.ticketStatusDao.updateItem(entity) != 1) {
					logger.error("チケットステータス更新失敗 : " + entity.toString());
					throw new Exception("チケットステータス更新失敗");
				}
			} else {
				// 登録
				entity.setId(++maxId);
				if (this.ticketStatusDao.appendItem(entity) != 1) {
					logger.error("チケットステータス登録失敗 : " + entity.toString());
					throw new Exception("チケットステータス登録失敗");
				}
			}
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * チケット進捗の登録を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto チケットステータス登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	public StatusResponseDto saveTicketProgress(LoginDto login, TicketProgressSaveRequestDto inDto) throws Exception {
		final TicketProgressEntity entity = TicketProgressEntity.builder().build();
		entity.setProject_id(inDto.getProject_id());
		entity.setCreateUserId(login.getId());
		entity.setUpdateUserId(login.getId());
		Long maxId = this.ticketProgressDao.getMaxId(inDto.getProject_id());
		// 一旦全てを無効に設定
		this.ticketProgressDao.setUnavailable(inDto.getProject_id());
		for (TicketProgressDto statusDto : inDto.getProgressList()) {
			entity.setDisp_seq(statusDto.getDisp_seq());
			entity.setName(statusDto.getName());
			if (statusDto.getId() != null) {
				// 更新
				entity.setId(statusDto.getId());
				if (this.ticketProgressDao.updateItem(entity) != 1) {
					logger.error("チケットステータス更新失敗 : " + entity.toString());
					throw new Exception("チケットステータス更新失敗");
				}
			} else {
				// 登録
				entity.setId(++maxId);
				if (this.ticketProgressDao.appendItem(entity) != 1) {
					logger.error("チケットステータス登録失敗 : " + entity.toString());
					throw new Exception("チケットステータス登録失敗");
				}
			}
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}