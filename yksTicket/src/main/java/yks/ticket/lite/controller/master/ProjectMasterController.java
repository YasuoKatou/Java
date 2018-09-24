package yks.ticket.lite.controller.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.dao.master.ProjectListRequestDto;
import yks.ticket.lite.dto.master.ProjectDto;
import yks.ticket.lite.service.mater.ProjectService;

/**
 * プロジェクトマスタメンテナンスコントローラ.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Controller
@RequestMapping(value="/yksticket/maintenance")
public class ProjectMasterController {
	@Autowired private ProjectService projectService;

	/**
	 * プロジェクト一覧を取得する/
	 * @param alive 終了したプロジェクトを含める／含めないの制御文字列
	 * @return プロジェクトDtoリスト
	 * @since 0.0.1
	 */
	@RequestMapping(value="/projects", method=RequestMethod.POST)
	@ResponseBody
	public List<ProjectDto> getProjects(@RequestBody ProjectListRequestDto inDto) {
		return projectService.getProjectList(inDto);
	}

	@RequestMapping(value="/newproject", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addProject(@RequestBody ProjectDto projectDto) {
		return "";
	}
}