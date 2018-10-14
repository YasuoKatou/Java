package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
import yks.ticket.lite.dto.LoginDto;
import yks.ticket.lite.dto.ProjectStatusListRequestDto;
import yks.ticket.lite.dto.ProjectStatusListResponseDto;
import yks.ticket.lite.dto.ProjectStatusUpdateRequestDto;
import yks.ticket.lite.dto.StatusResponseDto;
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
		// テスト対象のサービスを呼び出す
		ProjectStatusListRequestDto inDto = ProjectStatusListRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.build();
		ProjectStatusListResponseDto outDto = this.projectStatusService.getProjectStatusList(inDto);

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", outDto);
		List<Status> list = outDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);
		// +0
		Status stat = list.get(0);
		assertEquals("+0.ステータスID", stat.getId(), Long.valueOf(1L));
		assertEquals("+0.ステータス名称", stat.getName(), "未着手");
		assertEquals("+0.表示順", stat.getDispSeq(), 10);
		assertEquals("+0.バージョン", stat.getVersionNo(), Integer.valueOf(100));
		// +1
		stat = list.get(1);
		assertEquals("+1.ステータスID", stat.getId(), Long.valueOf(2L));
		assertEquals("+1.ステータス名称", stat.getName(), "10%");
		assertEquals("+1.表示順", stat.getDispSeq(), 20);
		assertEquals("+1.バージョン", stat.getVersionNo(), Integer.valueOf(200));
		// +2
		stat = list.get(2);
		assertEquals("+2.ステータスID", stat.getId(), Long.valueOf(3L));
		assertEquals("+2.ステータス名称", stat.getName(), "完了");
		assertEquals("+2.表示順", stat.getDispSeq(), 30);
		assertEquals("+2.バージョン", stat.getVersionNo(), Integer.valueOf(300));
	}

	/**
	 * プロジェクトステータスの更新を確認
	 * 何も変更しない場合、バージョン番号のみ＋１される。
	 * @since 0.0.0
	 */
	@Test
	@DatabaseSetup("classpath:ProjectStatusDaoTest_D01/")
	public void test_updateProjectStatus_01() {
		// テストデータを取得
		ProjectStatusListRequestDto statusListRequestDto = ProjectStatusListRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.build();
		ProjectStatusListResponseDto statusListDto = this.projectStatusService.getProjectStatusList(
				statusListRequestDto);
		assertNotNull("戻り値あり", statusListDto);
		List<Status> list = statusListDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);

		// テスト対象のサービスを呼び出す
		LoginDto login = LoginDto.builder().id(Long.valueOf(1L)).build();
		ProjectStatusUpdateRequestDto inDto = ProjectStatusUpdateRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.statusList(statusListDto.getStatusList())
				.build();
		StatusResponseDto respDto = null;
		try {
			respDto = this.projectStatusService.updateProjectStatus(login, inDto);
		} catch (Exception ex) {
			fail("サービスで異常を検出");
		}

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", respDto);
		// 更新結果を確認する
		statusListDto = this.projectStatusService.getProjectStatusList(statusListRequestDto);
		assertNotNull("戻り値あり", statusListDto);
		list = statusListDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);
		// +0
		Status stat = list.get(0);
		assertEquals("+0.ステータスID", stat.getId(), Long.valueOf(1L));
		assertEquals("+0.ステータス名称", stat.getName(), "未着手");
		assertEquals("+0.表示順", stat.getDispSeq(), 10);
		assertEquals("+0.バージョン", stat.getVersionNo(), Integer.valueOf(101));
		// +1
		stat = list.get(1);
		assertEquals("+1.ステータスID", stat.getId(), Long.valueOf(2L));
		assertEquals("+1.ステータス名称", stat.getName(), "10%");
		assertEquals("+1.表示順", stat.getDispSeq(), 20);
		assertEquals("+1.バージョン", stat.getVersionNo(), Integer.valueOf(201));
		// +2
		stat = list.get(2);
		assertEquals("+2.ステータスID", stat.getId(), Long.valueOf(3L));
		assertEquals("+2.ステータス名称", stat.getName(), "完了");
		assertEquals("+2.表示順", stat.getDispSeq(), 30);
		assertEquals("+2.バージョン", stat.getVersionNo(), Integer.valueOf(301));
	}

	/**
	 * プロジェクトステータスの更新を確認
	 * 項目削除と追加を確認する。
	 * @since 0.0.0
	 */
	@Test
	@DatabaseSetup("classpath:ProjectStatusDaoTest_D01/")
	public void test_updateProjectStatus_02() {
		// テストデータを取得
		ProjectStatusListRequestDto statusListRequestDto = ProjectStatusListRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.build();
		ProjectStatusListResponseDto statusListDto = this.projectStatusService.getProjectStatusList(
				statusListRequestDto);
		assertNotNull("戻り値あり", statusListDto);
		List<Status> list = statusListDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);

		// 項目を削除する
		list.remove(1);		// 「10%」を削除
		// 項目を追加する
		list.add(1, Status.builder()	// 2番目に追加
				.dispSeq(11)
				.name("50%")
				.build());
		list.add(Status.builder()		// 最後に追加
				.dispSeq(31)
				.name("削除対象")
				.build());

		// テスト対象のサービスを呼び出す
		LoginDto login = LoginDto.builder().id(Long.valueOf(1L)).build();
		ProjectStatusUpdateRequestDto inDto = ProjectStatusUpdateRequestDto.builder()
				.project_id(Long.valueOf(1L))
				.statusList(statusListDto.getStatusList())
				.build();
		StatusResponseDto respDto = null;
		try {
			respDto = this.projectStatusService.updateProjectStatus(login, inDto);
		} catch (Exception ex) {
			fail("サービスで異常を検出");
		}

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", respDto);
		// 更新結果を確認する
		statusListDto = this.projectStatusService.getProjectStatusList(statusListRequestDto);
		assertNotNull("戻り値あり", statusListDto);
		list = statusListDto.getStatusList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 4);
		// +0
		Status stat = list.get(0);
		assertEquals("+0.ステータスID", stat.getId(), Long.valueOf(1L));
		assertEquals("+0.ステータス名称", stat.getName(), "未着手");
		assertEquals("+0.表示順", stat.getDispSeq(), 10);
		assertEquals("+0.バージョン", stat.getVersionNo(), Integer.valueOf(101));
		// +1
		stat = list.get(1);
		assertEquals("+1.ステータスID", stat.getId(), Long.valueOf(4L));
		assertEquals("+1.ステータス名称", stat.getName(), "50%");
		assertEquals("+1.表示順", stat.getDispSeq(), 11);
		assertEquals("+1.バージョン", stat.getVersionNo(), Integer.valueOf(1));
		// +2
		stat = list.get(2);
		assertEquals("+2.ステータスID", stat.getId(), Long.valueOf(3L));
		assertEquals("+2.ステータス名称", stat.getName(), "完了");
		assertEquals("+2.表示順", stat.getDispSeq(), 30);
		assertEquals("+2.バージョン", stat.getVersionNo(), Integer.valueOf(301));
		// +2
		stat = list.get(3);
		assertEquals("+2.ステータスID", stat.getId(), Long.valueOf(5L));
		assertEquals("+2.ステータス名称", stat.getName(), "削除対象");
		assertEquals("+2.表示順", stat.getDispSeq(), 31);
		assertEquals("+2.バージョン", stat.getVersionNo(), Integer.valueOf(1));
	}
}