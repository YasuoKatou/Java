package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.TicketDao;
import yks.ticket.lite.dao.TicketProgressDao;
import yks.ticket.lite.dao.TicketStatusDao;
import yks.ticket.lite.dto.TicketDto;
import yks.ticket.lite.dto.TicketListRequestDto;
import yks.ticket.lite.dto.TicketListResponseDto;
import yks.ticket.lite.dto.TicketMastersRequestDto;
import yks.ticket.lite.dto.TicketMastersResponseDto;
import yks.ticket.lite.dto.TicketProgressDto;
import yks.ticket.lite.dto.TicketStatusDto;

/**
 * チケット管理サービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class TicketService {
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
	TicketListResponseDto getTicketList(TicketListRequestDto inDto) {
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
	TicketMastersResponseDto getTicketMasters(TicketMastersRequestDto inDto) {
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
}