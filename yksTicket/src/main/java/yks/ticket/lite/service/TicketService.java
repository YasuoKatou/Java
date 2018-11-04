package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import yks.ticket.lite.common.DateUtil;
import yks.ticket.lite.common.TicketApConstatnt.TicketMessage;
import yks.ticket.lite.dao.TicketDao;
import yks.ticket.lite.dao.TicketKindDao;
import yks.ticket.lite.dao.TicketMemoDao;
import yks.ticket.lite.dao.TicketPriorityDao;
import yks.ticket.lite.dao.TicketProgressDao;
import yks.ticket.lite.dao.TicketStatusDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.dto.TicketAppendRequestDto;
import yks.ticket.lite.dto.TicketDto;
import yks.ticket.lite.dto.TicketKindDto;
import yks.ticket.lite.dto.TicketKindSaveRequestDto;
import yks.ticket.lite.dto.TicketListRequestDto;
import yks.ticket.lite.dto.TicketListResponseDto;
import yks.ticket.lite.dto.TicketMastersRequestDto;
import yks.ticket.lite.dto.TicketMastersResponseDto;
import yks.ticket.lite.dto.TicketMemoDto;
import yks.ticket.lite.dto.TicketMemoListRequestDto;
import yks.ticket.lite.dto.TicketMemoListResponseDto;
import yks.ticket.lite.dto.TicketPriorityDto;
import yks.ticket.lite.dto.TicketPrioritySaveRequestDto;
import yks.ticket.lite.dto.TicketProgressDto;
import yks.ticket.lite.dto.TicketProgressSaveRequestDto;
import yks.ticket.lite.dto.TicketRequestDto;
import yks.ticket.lite.dto.TicketResponseDto;
import yks.ticket.lite.dto.TicketStatusDto;
import yks.ticket.lite.dto.TicketStatusSaveRequestDto;
import yks.ticket.lite.entity.TicketEntity;
import yks.ticket.lite.entity.TicketKindEntity;
import yks.ticket.lite.entity.TicketMemoEntity;
import yks.ticket.lite.entity.TicketPriorityEntity;
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
	/** チケットメモテーブルDao. */
	@Autowired private TicketMemoDao ticketMemoDao;
	/** チケットステータスDao. */
	@Autowired private TicketStatusDao ticketStatusDao;
	/** チケット進捗Dao. */
	@Autowired private TicketProgressDao ticketProgressDao;
	/** チケット種類Dao. */
	@Autowired private TicketKindDao ticketKindDao;
	/** チケット優先順位Dao. */
	@Autowired private TicketPriorityDao ticketPriorityDao;

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
					.start_date(DateUtil.toDateString(entity.getStart_date()))
					.finish_date(DateUtil.toDateString(entity.getFinish_date()))
					.progress_id(entity.getProgress_id())
					.kind_id(entity.getKind_id())
					.priority_id(entity.getPriority_id())
					.project_id(entity.getProject_id())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return TicketListResponseDto.builder()
				.ticketList(list)
				.build();
	}

	/**
	 * チケット取得
	 *
	 * @param inDto チケット取得リクエストDto.
	 * @return チケット取得レスポンスDto.
	 * @throws 取得データなし
	 * @since 0.0.1
	 */
	public TicketResponseDto getTicket(TicketRequestDto inDto) throws Exception {
		TicketEntity entity = this.ticketDao.findById(inDto.getId());
		if (entity != null) {
			return TicketResponseDto.builder().ticketDto(TicketDto.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.description(entity.getDescription())
					.status_id(entity.getStatus_id())
					.start_date(DateUtil.toDateString(entity.getStart_date()))
					.finish_date(DateUtil.toDateString(entity.getFinish_date()))
					.progress_id(entity.getProgress_id())
					.kind_id(entity.getKind_id())
					.priority_id(entity.getPriority_id())
					.project_id(entity.getProject_id())
					.versionNo(entity.getVersionNo())
					.build())
					.build();
		} else {
			throw new Exception("チケットデータなし");
		}
	}

	/**
	 * チケットの登録
	 *
	 * @param login ログイン情報
	 * @param inDto チケット登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto appendTicket(LoginDto login, TicketAppendRequestDto inDto) throws Exception {
		Long userId = login.getId();
		// チケット登録データ作成
		TicketDto ticket = inDto.getTicket();
		Long ticketId = this.ticketDao.findMaxId() + 1;
		TicketEntity ticketEntity = TicketEntity.builder()
				.id(ticketId)
				.title(ticket.getTitle())
				.description(ticket.getDescription())
				.status_id(ticket.getStatus_id())
				.start_date(DateUtil.toSqlDate(ticket.getStart_date()))
				.finish_date(DateUtil.toSqlDate(ticket.getFinish_date()))
				.progress_id(ticket.getProgress_id())
				.kind_id(ticket.getKind_id())
				.priority_id(ticket.getPriority_id())
				.project_id(ticket.getProject_id())
				.build();
		ticketEntity.setCreateUserId(userId);
		// チケット登録
		if (this.ticketDao.append(ticketEntity) != 1) {
			logger.error("チケット登録失敗" + ticketEntity.toString());
			throw new Exception("チケット登録失敗");
		}
		// チケットコメントの登録データ作成
		Long memoId = this.ticketMemoDao.findMaxId(ticketId) + 1;
		TicketMemoEntity memoEntity = TicketMemoEntity.builder()
				.id(memoId)
				.ticket_id(ticketId)
				.memo(TicketMessage.NEW_TICKET_MESSAGE)
				.root_memo_id(memoId)
				.parent_memo_id(memoId)
				.build();
		memoEntity.setCreateUserId(userId);
		// チケットコメントを登録
		if (this.ticketMemoDao.append(memoEntity) != 1) {
			logger.error("チケット登録失敗" + memoEntity.toString());
			throw new Exception("チケット登録失敗");
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * チケットメモ（履歴）一覧の取得
	 * @param inDto チケットメモ（履歴）一覧取得リクエストDto
	 * @return チケットメモ（履歴）一覧取得レスポンスDto
	 * @since 0.0.1
	 */
	public TicketMemoListResponseDto getTicketMemoList(TicketMemoListRequestDto inDto) {
		List<TicketMemoDto> memoList = new ArrayList<>();
		this.ticketMemoDao.findByTicketId(inDto.getTicket_id()).forEach(entity -> {
			memoList.add(TicketMemoDto.builder()
					.id(entity.getId())
					.ticket_id(entity.getTicket_id())
					.memo(entity.getMemo())
					.root_memo_id(entity.getRoot_memo_id())
					.parent_memo_id(entity.getParent_memo_id())
					.createDate(DateUtil.toDateString(entity.getCreateDate()))
					.createUserId(entity.getCreateUserId())
					.updateDate(DateUtil.toDateString(entity.getUpdateDate()))
					.updateUserId(entity.getUpdateUserId())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return TicketMemoListResponseDto.builder()
				.memoList(memoList)
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
		// チケット種類一覧を取得
		final List<TicketKindDto> kindList = new ArrayList<>();
		this.ticketKindDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			kindList.add(TicketKindDto.builder()
					.project_id(entity.getProject_id())
					.id(entity.getId())
					.disp_seq(entity.getDisp_seq())
					.name(entity.getName())
					.versionNo(entity.getVersionNo())
					.build());
		});
		// チケット優先順位一覧を取得
		final List<TicketPriorityDto> priorityList = new ArrayList<>();
		this.ticketPriorityDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			priorityList.add(TicketPriorityDto.builder()
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
				.kindList(kindList)
				.priorityList(priorityList)
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
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
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
	 * @param inDto チケットス進捗登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto saveTicketProgress(LoginDto login, TicketProgressSaveRequestDto inDto) throws Exception {
		final TicketProgressEntity entity = TicketProgressEntity.builder().build();
		entity.setProject_id(inDto.getProject_id());
		entity.setCreateUserId(login.getId());
		entity.setUpdateUserId(login.getId());
		Long maxId = this.ticketProgressDao.getMaxId(inDto.getProject_id());
		// 一旦全てを無効に設定
		this.ticketProgressDao.setUnavailable(inDto.getProject_id());
		for (TicketProgressDto progressDto : inDto.getProgressList()) {
			entity.setDisp_seq(progressDto.getDisp_seq());
			entity.setName(progressDto.getName());
			if (progressDto.getId() != null) {
				// 更新
				entity.setId(progressDto.getId());
				if (this.ticketProgressDao.updateItem(entity) != 1) {
					logger.error("チケット進捗更新失敗 : " + entity.toString());
					throw new Exception("チケット進捗更新失敗");
				}
			} else {
				// 登録
				entity.setId(++maxId);
				if (this.ticketProgressDao.appendItem(entity) != 1) {
					logger.error("チケット進捗登録失敗 : " + entity.toString());
					throw new Exception("チケット進捗登録失敗");
				}
			}
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}


	/**
	 * チケット優先順位の登録を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto チケット優先順位登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto saveTicketPriority(LoginDto login, TicketPrioritySaveRequestDto inDto) throws Exception {
		final TicketPriorityEntity entity = TicketPriorityEntity.builder().build();
		entity.setProject_id(inDto.getProject_id());
		entity.setCreateUserId(login.getId());
		entity.setUpdateUserId(login.getId());
		Long maxId = this.ticketPriorityDao.getMaxId(inDto.getProject_id());
		// 一旦全てを無効に設定
		this.ticketPriorityDao.setUnavailable(inDto.getProject_id());
		for (TicketPriorityDto priorityDto : inDto.getPriorityList()) {
			entity.setDisp_seq(priorityDto.getDisp_seq());
			entity.setName(priorityDto.getName());
			if (priorityDto.getId() != null) {
				// 更新
				entity.setId(priorityDto.getId());
				if (this.ticketPriorityDao.updateItem(entity) != 1) {
					logger.error("チケット優先順位更新失敗 : " + entity.toString());
					throw new Exception("チケット優先順位更新失敗");
				}
			} else {
				// 登録
				entity.setId(++maxId);
				if (this.ticketPriorityDao.appendItem(entity) != 1) {
					logger.error("チケット優先順位登録失敗 : " + entity.toString());
					throw new Exception("チケット優先順位登録失敗");
				}
			}
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}


	/**
	 * チケット種類の登録を行う.
	 *
	 * @param login ログイン情報
	 * @param inDto チケット種類登録リクエストDto.
	 * @return 処理結果
	 * @throws Exception 登録失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto saveTicketKind(LoginDto login, TicketKindSaveRequestDto inDto) throws Exception {
		final TicketKindEntity entity = TicketKindEntity.builder().build();
		entity.setProject_id(inDto.getProject_id());
		entity.setCreateUserId(login.getId());
		entity.setUpdateUserId(login.getId());
		Long maxId = this.ticketKindDao.getMaxId(inDto.getProject_id());
		// 一旦全てを無効に設定
		this.ticketKindDao.setUnavailable(inDto.getProject_id());
		for (TicketKindDto kindDto : inDto.getKindList()) {
			entity.setDisp_seq(kindDto.getDisp_seq());
			entity.setName(kindDto.getName());
			if (kindDto.getId() != null) {
				// 更新
				entity.setId(kindDto.getId());
				if (this.ticketKindDao.updateItem(entity) != 1) {
					logger.error("チケット種類更新失敗 : " + entity.toString());
					throw new Exception("チケット種類更新失敗");
				}
			} else {
				// 登録
				entity.setId(++maxId);
				if (this.ticketKindDao.appendItem(entity) != 1) {
					logger.error("チケット種類登録失敗 : " + entity.toString());
					throw new Exception("チケット種類登録失敗");
				}
			}
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}