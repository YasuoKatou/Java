package yks.ticket.lite.service.mater;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.master.ProjectListRequestDto;
import yks.ticket.lite.dao.master.ProjectMasterDao;
import yks.ticket.lite.dto.master.ProjectDto;
import yks.ticket.lite.entity.master.ProjectMasterEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * プロジェクトマスタメンテナンスサービス.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class ProjectService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** プロジェクトマスタDao. */
	@Autowired private ProjectMasterDao projectMasterDao;

	/**
	 * プロジェクト一覧を取得する.
	 * @param inDto プロジェクト一覧取得要求Dto
	 * @return プロジェクトDto一覧
	 * @since 0.0.1
	 */
	public List<ProjectDto> getProjectList(ProjectListRequestDto inDto) {
		List<ProjectDto> list = new ArrayList<>();
		projectMasterDao.findProjects(inDto.getAlive()).forEach(entity -> {
			String name1 = null;
			String name2 = null;
			UserMasterEntity manager = entity.getManager();
			if (manager != null) {
				name1 = manager.getName1();
				name2 = manager.getName2();
			}
			list.add(ProjectDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.description(entity.getDescription())
					.manager_id(entity.getManager_id())
					.alive(entity.getAlive())
					.opened(entity.getOpened())
					.manager_name1(name1)
					.manager_name2(name2)
					.build());
		});
		return list;
	}

	/**
	 * プロジェクトの登録を行う.
	 * @param inDto プロジェクトDto
	 * @since 0.0.1
	 */
	public void appendProject(ProjectDto inDto) {
		int count = projectMasterDao.insert(ProjectMasterEntity.builder()
				.name(inDto.getName())
				.description(inDto.getDescription())
				.manager_id(inDto.getManager_id())
				.alive(inDto.getAlive())
				.opened(inDto.getOpened())
				.build());
		logger.info("プロジェクトを追加 : " + count);
	}
}