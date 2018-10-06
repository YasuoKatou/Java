package yks.ticket.lite.controller.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.controller.ControllerBase;
import yks.ticket.lite.dao.master.ProjectListRequestDto;
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.dto.master.ProjectDto;
import yks.ticket.lite.service.mater.ProjectService;

/**
 * プロジェクトマスタメンテナンスコントローラ.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/project")
public class ProjectMasterController extends ControllerBase {
	/** プロジェクトマスタメンテナンスサービス */
	@Autowired private ProjectService projectService;

	/**
	 * プロジェクト一覧を取得する.
	 * @param alive 終了したプロジェクトを含める／含めないの制御文字列
	 * @return プロジェクトDtoリスト
	 * @since 0.0.1
	 */
	@PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ProjectDto> getProjects(@RequestBody ProjectListRequestDto inDto) {
		// プロジェクトの一覧を取得
		return this.projectService.getProjectList(inDto);
	}

	/**
	 * 新規プロジェクトを登録する.
	 * @param projectDto
	 * @return
	 * @since 0.0.1
	 */
	@PostMapping(value="/append", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StatusResponseDto addProject(@RequestBody ProjectDto projectDto) {
		LoginDto login = super.getLogin(projectDto);
		// 新規プロジェクトの登録
		this.projectService.appendProject(login, projectDto);
		// 正常終了を戻す
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}