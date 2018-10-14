package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import yks.ticket.lite.dao.ProjectStatusDao;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.ProjectStatusListRequestDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto;
import yks.ticket.lite.dto.ProjectStatusUpdateRequestDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto.Status;
import yks.ticket.lite.entity.ProjectStatusEntity;

/**
 * プロジェクトステータ管理スサービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class ProjectStatusService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** プロジェクトステータス管理テーブルDao. */
	@Autowired private ProjectStatusDao projectStatusDao;

	/**
	 * プロジェクトステータス一覧を取得する.
	 *
	 * @param inDto プロジェクトスタータス一覧取得リクエストDto.
	 * @return プロジェクトスタータス一覧レスポンスDto.
	 * @since 0.0.1
	 */
	public ProjectStatusListResponseDto getProjectStatusList(ProjectStatusListRequestDto inDto) {
		List<ProjectStatusListResponseDto.Status> list = new ArrayList<>();
		this.projectStatusDao.findByProject(inDto.getProject_id()).forEach(entity -> {
			list.add(ProjectStatusListResponseDto.Status.builder()
					.id(entity.getId())
					.name(entity.getName())
					.dispSeq(entity.getDispSeq())
					.versionNo(entity.getVersionNo())
					.build());
		});
		return ProjectStatusListResponseDto.builder()
				.statusList(list)
				.build();
	}

	/**
	 * プロジェクトステータスを更新する.
	 *
	 * @param login ログイン情報
	 * @param inDto プロジェクトスタータス更新リクエストDto.
	 * @return 処理結果
	 * @exception 更新失敗
	 * @since 0.0.1
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StatusResponseDto updateProjectStatus(LoginDto login, ProjectStatusUpdateRequestDto inDto)
	throws Exception{
		Long userId = login.getId();
		Long projectId = inDto.getProject_id();
		ProjectStatusEntity entity = ProjectStatusEntity.builder()
				.projectId(projectId)
				.available("yes")
				.build();
		entity.setCreateUserId(userId);
		entity.setUpdateUserId(userId);

		// 一旦全てのステータスを使用不可に設定する
		int count = this.projectStatusDao.updateProject2NotUse(projectId);
		logger.info("ステータスの使用不可 : " + count);
		// 更新処理
		List<Status> list = inDto.getStatusList();
		for (Status status : list) {
			if (status.getId() != null) {
				entity.setId(status.getId());
				entity.setName(status.getName());
				entity.setDispSeq(status.getDispSeq());
				if (this.projectStatusDao.updateStatus(entity) != 1) {
					logger.error("ステータスの更新失敗 : " + entity.toString());
					throw new Exception("ステータスの更新失敗");
				}
			}
		}
		// 追加処理
		for (Status status : list) {
			if (status.getId() == null) {
				Long id = this.projectStatusDao.getNextStatusId(projectId);
				entity.setId(id);
				entity.setName(status.getName());
				entity.setDispSeq(status.getDispSeq());
				if (this.projectStatusDao.appendStatus(entity) != 1) {
					logger.error("ステータスの追加失敗 : " + entity.toString());
					throw new Exception("ステータスの登録失敗");
				}
			}
		}

		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}