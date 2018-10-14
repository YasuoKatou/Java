package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;
import yks.ticket.lite.dto.ProjectStatusListRequestDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto.Status;

/**
 * プロジェクトステータ管理スサービスのテストクラス.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, value = "server.port=8080")
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
public class ProjectStatusServiceTest {
	/** プロジェクトステータ管理スサービス. */
	@Autowired private ProjectStatusService projectStatusService;

	/**
	 * プロジェクトステータス一覧取得を確認
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:ProjectStatusDaoTest_D01/")
	public void test_getProjectStatusList() {
		ProjectStatusListRequestDto inDto = ProjectStatusListRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.build();
		ProjectStatusListResponseDto outDto = this.projectStatusService.getProjectStatusList(inDto);
		assertNotNull("戻り値あり", outDto);
		List<Status> list = outDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);
		// +0
		Status stat = list.get(0);
		assertEquals("+0.ステータスID", stat.getId(), Long.valueOf(1L));
		assertEquals("+0.ステータス名称", stat.getName(), "未着手");
		// +1
		stat = list.get(1);
		assertEquals("+1.ステータスID", stat.getId(), Long.valueOf(2L));
		assertEquals("+1.ステータス名称", stat.getName(), "10%");
		// +2
		stat = list.get(2);
		assertEquals("+2.ステータスID", stat.getId(), Long.valueOf(3L));
		assertEquals("+2.ステータス名称", stat.getName(), "完了");
	}
}