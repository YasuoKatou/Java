package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.dto.TicketDto;
import yks.ticket.lite.dto.TicketKindDto;
import yks.ticket.lite.dto.TicketListRequestDto;
import yks.ticket.lite.dto.TicketListResponseDto;
import yks.ticket.lite.dto.TicketMastersRequestDto;
import yks.ticket.lite.dto.TicketMastersResponseDto;
import yks.ticket.lite.dto.TicketPriorityDto;
import yks.ticket.lite.dto.TicketProgressDto;
import yks.ticket.lite.dto.TicketProgressSaveRequestDto;
import yks.ticket.lite.dto.TicketStatusDto;
import yks.ticket.lite.dto.TicketStatusSaveRequestDto;

/**
 * チケット管理サービスのテストクラス.
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
public class TicketServiceTest {
	/** チケットサービス */
	@Autowired private TicketService ticketService;

	/**
	 * チケット一覧取得をテスト.
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D01/")
	public void test_getTicketList() {
		Long projectId = Long.valueOf(2L);
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		// テスト対象のサービスを呼び出す
		TicketListResponseDto outDto = this.ticketService.getTicketList(TicketListRequestDto.builder()
				.project_id(projectId)
				.build());

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", outDto);
		List<TicketDto> list = outDto.getTicketList();
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 2);
		// +0
		TicketDto ticket = list.get(0);
		assertEquals("+0:チケットID", ticket.getId(), Long.valueOf(2L));
		assertEquals("+0:タイトル", ticket.getTitle(), "ccc");
		assertEquals("+0:説明", ticket.getDescription(), "ddd");
		assertEquals("+0:ステータスID", ticket.getStatus_id(), Long.valueOf(202L));
		assertEquals("+0:開始日", sdfDate.format(ticket.getStart_date()), "2018-09-25");
		assertEquals("+0:終了日", sdfDate.format(ticket.getFinish_date()), "2018-09-26");
		assertEquals("+0:進捗ID", ticket.getProgress_id(), Long.valueOf(1L));
		assertEquals("+0:プロジェクトID", ticket.getProject_id(), projectId);
		assertEquals("+0:バージョンNo", ticket.getVersionNo(), Integer.valueOf(2));
		// +1
		ticket = list.get(1);
		assertEquals("+1:チケットID", ticket.getId(), Long.valueOf(3L));
		assertEquals("+1:タイトル", ticket.getTitle(), "eee");
		assertEquals("+1:説明", ticket.getDescription(), "fff");
		assertEquals("+1:ステータスID", ticket.getStatus_id(), Long.valueOf(203L));
		assertNull("+1:開始日", ticket.getStart_date());
		assertNull("+1:終了日", ticket.getFinish_date());
		assertEquals("+1:進捗ID", ticket.getProgress_id(), Long.valueOf(5L));
		assertEquals("+1:プロジェクトID", ticket.getProject_id(), projectId);
		assertEquals("+1:バージョンNo", ticket.getVersionNo(), Integer.valueOf(6));
	}

	/**
	 * チケット関連マスタ取得をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D02/")
	public void test_getTicketMasters() {
		Long projectId = Long.valueOf(2L);
		// テスト対象のサービスを呼び出す
		TicketMastersResponseDto outDto = this.ticketService.getTicketMasters(
				TicketMastersRequestDto.builder().project_id(projectId).build());

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", outDto);
		List<TicketStatusDto> statusList = outDto.getStatusList();
		assertNotNull("ステータスデータあり", statusList);
		assertEquals("ステータス一覧件数", statusList.size(), 3);
		// +0
		TicketStatusDto statusDto = statusList.get(0);
		assertEquals("+0:プロジェクトID", statusDto.getProject_id(), projectId);
		assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(1L));
		assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(111));
		assertEquals("+0:ステータス名称", statusDto.getName(), "未着手");
		assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(11));
		// +1
		statusDto = statusList.get(1);
		assertEquals("+1:プロジェクトID", statusDto.getProject_id(), projectId);
		assertEquals("+1:ステータスID", statusDto.getId(), Long.valueOf(2L));
		assertEquals("+1:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(112));
		assertEquals("+1:ステータス名称", statusDto.getName(), "作業中");
		assertEquals("+1:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(12));
		// +2
		statusDto = statusList.get(2);
		assertEquals("+2:プロジェクトID", statusDto.getProject_id(), projectId);
		assertEquals("+2:ステータスID", statusDto.getId(), Long.valueOf(3L));
		assertEquals("+2:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(113));
		assertEquals("+2:ステータス名称", statusDto.getName(), "完了");
		assertEquals("+2:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(13));

		List<TicketProgressDto> progressList = outDto.getProgressList();
		assertNotNull("進捗データあり", progressList);
		assertEquals("進捗一覧件数", progressList.size(), 3);
		// +0
		TicketProgressDto progressDto = progressList.get(0);
		assertEquals("+0:プロジェクトID", progressDto.getProject_id(), projectId);
		assertEquals("+0:進捗ID", progressDto.getId(), Long.valueOf(11L));
		assertEquals("+0:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(1));
		assertEquals("+0:進捗名称", progressDto.getName(), "10%");
		assertEquals("+0:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(111));
		// +1
		progressDto = progressList.get(1);
		assertEquals("+1:プロジェクトID", progressDto.getProject_id(), projectId);
		assertEquals("+1:進捗ID", progressDto.getId(), Long.valueOf(12L));
		assertEquals("+1:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(2));
		assertEquals("+1:進捗名称", progressDto.getName(), "50%");
		assertEquals("+1:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(112));
		// +2
		progressDto = progressList.get(2);
		assertEquals("+2:プロジェクトID", progressDto.getProject_id(), projectId);
		assertEquals("+2:進捗ID", progressDto.getId(), Long.valueOf(13L));
		assertEquals("+2:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(3));
		assertEquals("+2:進捗名称", progressDto.getName(), "100%");
		assertEquals("+2:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(113));

		List<TicketKindDto> kindList = outDto.getKindList();
		assertNotNull("進捗データあり", kindList);
		assertEquals("進捗一覧件数", kindList.size(), 2);
		// +0
		TicketKindDto kindDto = kindList.get(0);
		assertEquals("+0:プロジェクトID", kindDto.getProject_id(), projectId);
		assertEquals("+0:種類ID", kindDto.getId(), Long.valueOf(5L));
		assertEquals("+0:種類表示順", kindDto.getDisp_seq(), Integer.valueOf(1));
		assertEquals("+0:種類名称", kindDto.getName(), "バグ");
		assertEquals("+0:種類データバージョン", kindDto.getVersionNo(), Integer.valueOf(2));
		// +1
		kindDto = kindList.get(1);
		assertEquals("+1:プロジェクトID", kindDto.getProject_id(), projectId);
		assertEquals("+1:種類ID", kindDto.getId(), Long.valueOf(6L));
		assertEquals("+1:種類表示順", kindDto.getDisp_seq(), Integer.valueOf(2));
		assertEquals("+1:種類名称", kindDto.getName(), "bug");
		assertEquals("+1:種類データバージョン", kindDto.getVersionNo(), Integer.valueOf(3));

		List<TicketPriorityDto> proiotyList = outDto.getPriorityList();
		assertNotNull("進捗データあり", proiotyList);
		assertEquals("進捗一覧件数", proiotyList.size(), 2);
		// +0
		TicketPriorityDto proiotyDto = proiotyList.get(0);
		assertEquals("+0:プロジェクトID", proiotyDto.getProject_id(), projectId);
		assertEquals("+0:優先順位ID", proiotyDto.getId(), Long.valueOf(3L));
		assertEquals("+0:優先順位表示順", proiotyDto.getDisp_seq(), Integer.valueOf(211));
		assertEquals("+0:優先順位名称", proiotyDto.getName(), "即対応");
		assertEquals("+0:優先順位データバージョン", proiotyDto.getVersionNo(), Integer.valueOf(11));
		// +1
		proiotyDto = proiotyList.get(1);
		assertEquals("+1:プロジェクトID", proiotyDto.getProject_id(), projectId);
		assertEquals("+1:優先順位ID", proiotyDto.getId(), Long.valueOf(4L));
		assertEquals("+1:優先順位表示順", proiotyDto.getDisp_seq(), Integer.valueOf(214));
		assertEquals("+1:優先順位名称", proiotyDto.getName(), "そのうち");
		assertEquals("+1:優先順位データバージョン", proiotyDto.getVersionNo(), Integer.valueOf(10));
	}

	/**
	 * チケットステータス管理の更新をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D03/")
	public void test_saveTicketStatus() {
		Long userId = Long.valueOf(10012L);
		Long projectId = Long.valueOf(2L);
		// 呼び出すサービスのデータを作成
		List<TicketStatusDto> statusList = new ArrayList<>();
		statusList.add(TicketStatusDto.builder()
				.id(Long.valueOf(1L))
				.name("ほげ")
				.disp_seq(Integer.valueOf(1))
				.project_id(projectId)
				.versionNo(Integer.valueOf(11))
				.build());
		statusList.add(TicketStatusDto.builder()
				.id(null)
				.name("ぱげ")
				.disp_seq(Integer.valueOf(2))
				.project_id(projectId)
				.build());
		TicketStatusSaveRequestDto requestDto = TicketStatusSaveRequestDto.builder()
				.project_id(projectId)
				.statusList(statusList)
				.build();
		// テスト対象のサービスを呼び出す
		try {
			StatusResponseDto responseDto = this.ticketService.saveTicketStatus(
					  LoginDto.builder().id(userId).build()
					, requestDto);
			// サービスの戻り値を判定する.
			assertNotNull("戻り値あり", responseDto);
			assertEquals("正常終了", responseDto.getStatus(), StatusResponseDto.SUCCESS);

			// 更新結果は、データを取得して確認する
			TicketMastersResponseDto outDto = this.ticketService.getTicketMasters(
					TicketMastersRequestDto.builder().project_id(Long.valueOf(2L)).build());

			// サービスの戻り値を判定する.
			assertNotNull("戻り値あり", outDto);
			statusList = outDto.getStatusList();
			assertNotNull("ステータスデータあり", statusList);
			assertEquals("ステータス一覧件数", statusList.size(), 2);
			// +0
			TicketStatusDto statusDto = statusList.get(0);
			assertEquals("+0:プロジェクトID", statusDto.getProject_id(), projectId);
			assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(1L));
			assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(1));
			assertEquals("+0:ステータス名称", statusDto.getName(), "ほげ");
			assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(12));
			// +1
			statusDto = statusList.get(1);
			assertEquals("+0:プロジェクトID", statusDto.getProject_id(), projectId);
			assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(5L));
			assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+0:ステータス名称", statusDto.getName(), "ぱげ");
			assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			fail("テスト異常終了 " + ex.toString());
		}
	}

	/**
	 * チケット進捗管理の更新をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D04/")
	public void test_saveTicketProgress() {
		Long userId = Long.valueOf(10012L);
		Long projectId = Long.valueOf(2L);
		// 呼び出すサービスのデータを作成
		List<TicketProgressDto> progressList = new ArrayList<>();
		progressList.add(TicketProgressDto.builder()
				.id(Long.valueOf(11L))
				.name("ほげ")
				.disp_seq(Integer.valueOf(1))
				.project_id(projectId)
				.versionNo(Integer.valueOf(11))
				.build());
		progressList.add(TicketProgressDto.builder()
				.id(null)
				.name("ぱげ")
				.disp_seq(Integer.valueOf(2))
				.project_id(projectId)
				.build());
		TicketProgressSaveRequestDto requestDto = TicketProgressSaveRequestDto.builder()
				.project_id(projectId)
				.progressList(progressList)
				.build();
		// テスト対象のサービスを呼び出す
		try {
			StatusResponseDto responseDto = this.ticketService.saveTicketProgress(
					  LoginDto.builder().id(userId).build()
					, requestDto);
			// サービスの戻り値を判定する.
			assertNotNull("戻り値あり", responseDto);
			assertEquals("正常終了", responseDto.getStatus(), StatusResponseDto.SUCCESS);

			// 更新結果は、データを取得して確認する
			TicketMastersResponseDto outDto = this.ticketService.getTicketMasters(
					TicketMastersRequestDto.builder().project_id(Long.valueOf(2L)).build());

			// サービスの戻り値を判定する.
			assertNotNull("戻り値あり", outDto);
			progressList = outDto.getProgressList();
			assertNotNull("ステータスデータあり", progressList);
			assertEquals("ステータス一覧件数", progressList.size(), 2);
			// +0
			TicketProgressDto statusDto = progressList.get(0);
			assertEquals("+0:プロジェクトID", statusDto.getProject_id(), projectId);
			assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(11L));
			assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(1));
			assertEquals("+0:ステータス名称", statusDto.getName(), "ほげ");
			assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(112));
			// +1
			statusDto = progressList.get(1);
			assertEquals("+0:プロジェクトID", statusDto.getProject_id(), projectId);
			assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(15L));
			assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+0:ステータス名称", statusDto.getName(), "ぱげ");
			assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			fail("テスト異常終了 " + ex.toString());
		}
	}
}