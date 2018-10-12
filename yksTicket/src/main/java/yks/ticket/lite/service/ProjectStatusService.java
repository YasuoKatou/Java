package yks.ticket.lite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.ProjectStatusDao;
import yks.ticket.lite.dto.ProjectStatusListRequestDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto;

/**
 * プロジェクトステータスサービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class ProjectStatusService {
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
					.build());
		});
		return ProjectStatusListResponseDto.builder()
				.statusList(list)
				.build();
	}
}