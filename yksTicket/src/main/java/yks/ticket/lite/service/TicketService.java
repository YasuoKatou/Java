package yks.ticket.lite.service;

import java.sql.Date;
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
import yks.ticket.lite.dao.TicketHistoryDao;
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
import yks.ticket.lite.dto.TicketUpdateRequestDto;
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
	/** チケット履歴テーブルDao. */
	@Autowired private TicketHistoryDao ticketHistoryDao;
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
			logger.error("チケットメモ登録失敗" + memoEntity.toString());
			throw new Exception("チケット登録失敗");
		}
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * チケットの更新
	 *
	 * @param login ログイン情報
	 * @param inDto チケット更新リクエストDto
	 * @return 処理結果
	 * @throws Exception 更新失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto updateTicket(LoginDto login, TicketUpdateRequestDto inDto) throws Exception {
		TicketDto ticket = inDto.getTicket();
		Long userId = login.getId();
		Long ticketId = ticket.getId();
		// 更新前を履歴に登録
		if (this.ticketHistoryDao.copyToHistory(ticketId) != 1) {
			logger.error("チケットの履歴登録に失敗");
			throw new Exception("チケットの更新失敗");
		}
		// 変更前のチケット情報を名称付きで取得
		TicketEntity beforeTicket = this.ticketDao.findByIdWithMaster(ticketId);
		if (beforeTicket == null) {
			logger.error("変更前のチケット情報の取得に失敗");
			throw new Exception("チケットの更新失敗");
		}
		// チケットを更新
		TicketEntity updateEntity = TicketEntity.builder()
				.id(ticket.getId())
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
		updateEntity.setUpdateUserId(userId);
		if (this.ticketDao.update(updateEntity) != 1) {
			logger.error("チケットの更新に失敗");
			throw new Exception("チケットの更新失敗");
		}
		// 更新後のチケット情報を名称付きで取得
		TicketEntity afterTicket = this.ticketDao.findByIdWithMaster(ticketId);
		if (afterTicket == null) {
			logger.error("変更後のチケット情報の取得に失敗");
			throw new Exception("チケットの更新失敗");
		}
		// 更新の差分を編集
		String diffString = this.diffTicket(beforeTicket, afterTicket);
		// 差分をチケットメモに登録
		Long ticketMemoId = this.ticketMemoDao.findMaxId(ticketId) + 1;
		TicketMemoEntity ticketMemo = TicketMemoEntity.builder()
				.id(ticketMemoId)
				.ticket_id(ticketId)
				.memo(diffString)
				.root_memo_id(ticketMemoId)
				.parent_memo_id(ticketMemoId)
				.build();
		ticketMemo.setCreateUserId(userId);
		if (this.ticketMemoDao.append(ticketMemo) != 1) {
			logger.error("チケットメモ登録失敗" + ticketMemo.toString());
			throw new Exception("チケット更新失敗");
		}

		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}

	/**
	 * チケットの差分を編集する.
	 *
	 * @param before 変更前チケット情報
	 * @param after 変更後チケット情報
	 * @return 差分を編集した文字列
	 * @since 0.0.1
	 */
	private String diffTicket(TicketEntity before, TicketEntity after) {
		StringBuffer diff = new StringBuffer();
		// タイトルの確認
		String n = this.diffItem(before.getTitle(), after.getTitle());
		if (n != null) {
			diff.append("タイトルを変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 説明の確認
		n = this.diffItem(before.getDescription(), after.getDescription());
		if (n != null) {
			diff.append("説明を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 状態の確認
		TicketStatusEntity status = before.getStatus();
		String beforeStatusName = (status == null) ? "未設定" : status.getName();
		status = after.getStatus();
		String afterStatusName = (status == null) ? "未設定" : status.getName();
		n = this.diffItem(beforeStatusName, afterStatusName);
		if (n != null) {
			diff.append("状態を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 種類の確認
		TicketKindEntity kind = before.getKind();
		String beforeKindName = (kind == null) ? "未設定" : kind.getName();
		kind = after.getKind();
		String afterKindName = (kind == null) ? "未設定" : kind.getName();
		n = this.diffItem(beforeKindName, afterKindName);
		if (n != null) {
			diff.append("種類を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 進捗の確認
		TicketProgressEntity progress = before.getProgress();
		String beforeProgressName = (progress == null) ? "未設定" : progress.getName();
		progress = after.getProgress();
		String afterProgressName = (progress == null) ? "未設定" : progress.getName();
		n = this.diffItem(beforeProgressName, afterProgressName);
		if (n != null) {
			diff.append("進捗を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 優先順位の確認
		TicketPriorityEntity priority = before.getPriority();
		String beforePriorityName = (priority == null) ? "未設定" : priority.getName();
		priority = after.getPriority();
		String afterPriorityName = (priority == null) ? "未設定" : priority.getName();
		n = this.diffItem(beforePriorityName, afterPriorityName);
		if (n != null) {
			diff.append("優先順位を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 作業開始日の確認
		Date date = before.getStart_date();
		String beforeStartDate = (date == null) ? "未設定" : DateUtil.toDateString(date);
		date = after.getStart_date();
		String afterStartDate = (date == null) ? "未設定" : DateUtil.toDateString(date);
		n = this.diffItem(beforeStartDate, afterStartDate);
		if (n != null) {
			diff.append("開始日を変更 ");
			diff.append(n);
			diff.append("¥n");
		}
		// 作業終了日の確認
		date = before.getFinish_date();
		String beforeFinishDate = (date == null) ? "未設定" : DateUtil.toDateString(date);
		date = after.getFinish_date();
		String afterFinishDate = (date == null) ? "未設定" : DateUtil.toDateString(date);
		n = this.diffItem(beforeFinishDate, afterFinishDate);
		if (n != null) {
			diff.append("終了日を変更 ");
			diff.append(n);
			diff.append("¥n");
		}

		return (diff.length() == 0) ? "変更なし" : diff.toString();
	}

	/**
	 * オブジェクトの比較を行う.
	 *
	 * @param pa 比較対象１
	 * @param pb 比較対象２
	 * @return ２つのオブジェクトが同じの時、nullを戻す.
	 * ２つのオブジェクトが異なる場合、差異を文字列で編集して戻す.
	 */
	private String diffItem(Object pa, Object pb) {
		Object a = (pa == null) ? "未設定" : pa;
		Object b = (pb == null) ? "未設定" : pb;
		return a.equals(b) ? null : "【" + a.toString() + "】 --> 【" + b.toString() + "】";
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