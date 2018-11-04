package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
import yks.ticket.lite.common.TicketApConstatnt.TicketMessage;
import yks.ticket.lite.dao.TicketDao;
import yks.ticket.lite.dao.TicketMemoDao;
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
	/** チケットテーブルDao */
	@Autowired private TicketDao ticketDao;
	/** チケットメモテーブルDao */
	@Autowired private TicketMemoDao ticketMemoDao;

	/**
	 * チケット一覧取得をテスト.
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D01/")
	public void test_getTicketList() {
		Long projectId = Long.valueOf(2L);
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
		assertEquals("+0:開始日", ticket.getStart_date(), "2018-09-25");
		assertEquals("+0:終了日", ticket.getFinish_date(), "2018-09-26");
		assertEquals("+0:進捗ID", ticket.getProgress_id(), Long.valueOf(1L));
		assertEquals("+0:種類ID", ticket.getKind_id(), Long.valueOf(302L));
		assertEquals("+0:優先順位ID", ticket.getPriority_id(), Long.valueOf(402L));
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
		assertEquals("+1:種類ID", ticket.getKind_id(), Long.valueOf(303L));
		assertEquals("+1:優先順位ID", ticket.getPriority_id(), Long.valueOf(403L));
		assertEquals("+1:プロジェクトID", ticket.getProject_id(), projectId);
		assertEquals("+1:バージョンNo", ticket.getVersionNo(), Integer.valueOf(6));
	}

	/**
	 * チケット取得をテスト.
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D01/")
	public void test_getTicket() {
		Long ticketId = Long.valueOf(2L);
		// テスト対象のサービスを呼び出す
		try {
			TicketResponseDto outDto = this.ticketService.getTicket(TicketRequestDto.builder()
					.id(ticketId)
					.build());
	
			// サービスの戻り値を判定する.
			assertNotNull("戻り値あり", outDto);
			TicketDto dto = outDto.getTicketDto();
			assertNotNull("取得データあり", dto);
			// +0
			assertEquals("チケットID", dto.getId(), ticketId);
			assertEquals("タイトル", dto.getTitle(), "ccc");
			assertEquals("説明", dto.getDescription(), "ddd");
			assertEquals("ステータスID", dto.getStatus_id(), Long.valueOf(202L));
			assertEquals("開始日", dto.getStart_date(), "2018-09-25");
			assertEquals("終了日", dto.getFinish_date(), "2018-09-26");
			assertEquals("進捗ID", dto.getProgress_id(), Long.valueOf(1L));
			assertEquals("種類ID", dto.getKind_id(), Long.valueOf(302L));
			assertEquals("優先順位ID", dto.getPriority_id(), Long.valueOf(402L));
			assertEquals("プロジェクトID", dto.getProject_id(), Long.valueOf(2L));
			assertEquals("バージョンNo", dto.getVersionNo(), Integer.valueOf(2));
		} catch (Exception ex) {
			fail("チケット取得失敗");
		}
	}

	/**
	 * チケット登録の確認
	 * @since 0.0.1
	 */
	@Test
	public void test_appendTicket() {
		Long userId = Long.valueOf(3L);
		// テストデータの作成
		LoginDto login = LoginDto.builder().id(userId).build();
		// リクエストの作成
		Long status_id   = Long.valueOf(101L);
		Long progress_id = Long.valueOf(102L);
		Long kind_id     = Long.valueOf(103L);
		Long priority_id = Long.valueOf(104L);
		Long project_id  = Long.valueOf(105L);
		TicketDto ticket = TicketDto.builder()
				.title("テストチケット")
				.description("チケットの説明")
				.status_id(status_id)
				.start_date("2018-11-01")
				.finish_date("2018-11-04")
				.progress_id(progress_id)
				.kind_id(kind_id)
				.priority_id(priority_id)
				.project_id(project_id)
				.build();
		// チケットの登録
		try {
			StatusResponseDto serviceResult =
			this.ticketService.appendTicket(login, TicketAppendRequestDto.builder()
					.ticket(ticket)
					.build());
			assertEquals("チケット登録結果", serviceResult.getStatus(), StatusResponseDto.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("チケット登録テスト失敗");
		}
		// 正しく登録が行われたかを確認する
		// ※（１／２）チケット本体
		Long ticketId = this.ticketDao.findMaxId();
		try {
			TicketResponseDto ticketRespDto = this.ticketService.getTicket(
					TicketRequestDto.builder().id(ticketId).build());
			assertNotNull("戻り値あり", ticketRespDto);
			assertNotNull("チケットデータあり", ticketRespDto.getTicketDto());
			ticket = ticketRespDto.getTicketDto();
			assertEquals("タイトル", ticket .getTitle(), "テストチケット");
			assertEquals("説明", ticket.getDescription(), "チケットの説明");
			assertEquals("ステータスID", ticket .getStatus_id(), status_id);
			assertEquals("作業開始日", ticket .getStart_date(), "2018-11-01");
			assertEquals("作業終了日", ticket .getFinish_date(), "2018-11-04");
			assertEquals("進捗ID", ticket .getProgress_id(), progress_id);
			assertEquals("種類ID", ticket .getKind_id(), kind_id);
			assertEquals("優先順位ID", ticket .getPriority_id(), priority_id);
			assertEquals("プロジェクトID", ticket .getProject_id(), project_id);
			assertEquals("バージョンNo", ticket .getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("登録チケットの取得失敗");
		}
		// ※（２／２）チケット履歴
		TicketMemoListResponseDto memoRespDto = this.ticketService.getTicketMemoList(
				TicketMemoListRequestDto.builder().ticket_id(ticketId).build());
		assertNotNull("戻り値あり", memoRespDto);
		List<TicketMemoDto> memoList = memoRespDto.getMemoList();
		assertNotNull("メモ一覧あり", memoList);
		assertEquals("メモ一覧一見あり", memoList.size(), 1);
		TicketMemoDto memoDto = memoList.get(0);
		Long memoId = this.ticketMemoDao.findMaxId(ticketId);
		assertEquals("メモID", memoDto.getId(), memoId);
		assertEquals("チケットID", memoDto.getTicket_id(), ticketId);
		assertEquals("メモ内容", memoDto.getMemo(), TicketMessage.NEW_TICKET_MESSAGE);
		assertEquals("ルートメモID", memoDto.getRoot_memo_id(), memoId);
		assertEquals("親メモID", memoDto.getParent_memo_id(), memoId);
		assertEquals("作成者ID", memoDto.getCreateUserId(), userId);
		assertNotNull("作成日時", memoDto.getCreateDate());
		assertEquals("更新者ID", memoDto.getUpdateUserId(), userId);
		assertEquals("バージョンNo", memoDto.getVersionNo(), Integer.valueOf(1));
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
			assertEquals("+1:プロジェクトID", statusDto.getProject_id(), projectId);
			assertEquals("+1:ステータスID", statusDto.getId(), Long.valueOf(5L));
			assertEquals("+1:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+1:ステータス名称", statusDto.getName(), "ぱげ");
			assertEquals("+1:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(1));
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
				.versionNo(Integer.valueOf(111))
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
			assertNotNull("進捗データあり", progressList);
			assertEquals("進捗一覧件数", progressList.size(), 2);
			// +0
			TicketProgressDto progressDto = progressList.get(0);
			assertEquals("+0:プロジェクトID", progressDto.getProject_id(), projectId);
			assertEquals("+0:進捗ID", progressDto.getId(), Long.valueOf(11L));
			assertEquals("+0:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(1));
			assertEquals("+0:進捗名称", progressDto.getName(), "ほげ");
			assertEquals("+0:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(112));
			// +1
			progressDto = progressList.get(1);
			assertEquals("+1:プロジェクトID", progressDto.getProject_id(), projectId);
			assertEquals("+1:進捗ID", progressDto.getId(), Long.valueOf(15L));
			assertEquals("+1:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+1:進捗名称", progressDto.getName(), "ぱげ");
			assertEquals("+1:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			fail("テスト異常終了 " + ex.toString());
		}
	}

	/**
	 * チケット優先順位管理の更新をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D05/")
	public void test_saveTicketPriority() {
		Long userId = Long.valueOf(10012L);
		Long projectId = Long.valueOf(2L);
		// 呼び出すサービスのデータを作成
		List<TicketPriorityDto> priorityList = new ArrayList<>();
		priorityList.add(TicketPriorityDto.builder()
				.id(Long.valueOf(11L))
				.name("高")
				.disp_seq(Integer.valueOf(1))
				.project_id(projectId)
				.versionNo(Integer.valueOf(111))
				.build());
		priorityList.add(TicketPriorityDto.builder()
				.id(null)
				.name("低")
				.disp_seq(Integer.valueOf(2))
				.project_id(projectId)
				.build());
		TicketPrioritySaveRequestDto requestDto = TicketPrioritySaveRequestDto.builder()
				.project_id(projectId)
				.priorityList(priorityList)
				.build();
		// テスト対象のサービスを呼び出す
		try {
			StatusResponseDto responseDto = this.ticketService.saveTicketPriority(
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
			priorityList = outDto.getPriorityList();
			assertNotNull("優先順位データあり", priorityList);
			assertEquals("優先順位一覧件数", priorityList.size(), 2);
			// +0
			TicketPriorityDto priorityDto = priorityList.get(0);
			assertEquals("+0:プロジェクトID", priorityDto.getProject_id(), projectId);
			assertEquals("+0:優先順位ID", priorityDto.getId(), Long.valueOf(11L));
			assertEquals("+0:優先順位表示順", priorityDto.getDisp_seq(), Integer.valueOf(1));
			assertEquals("+0:優先順位名称", priorityDto.getName(), "高");
			assertEquals("+0:優先順位データバージョン", priorityDto.getVersionNo(), Integer.valueOf(112));
			// +1
			priorityDto = priorityList.get(1);
			assertEquals("+1:プロジェクトID", priorityDto.getProject_id(), projectId);
			assertEquals("+1:優先順位ID", priorityDto.getId(), Long.valueOf(16L));
			assertEquals("+1:優先順位表示順", priorityDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+1:優先順位名称", priorityDto.getName(), "低");
			assertEquals("+1:優先順位データバージョン", priorityDto.getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			fail("テスト異常終了 " + ex.toString());
		}
	}

	/**
	 * チケット種類管理の更新をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D06/")
	public void test_saveTicketKind() {
		Long userId = Long.valueOf(10012L);
		Long projectId = Long.valueOf(2L);
		// 呼び出すサービスのデータを作成
		List<TicketKindDto> kindList = new ArrayList<>();
		kindList.add(TicketKindDto.builder()
				.id(Long.valueOf(11L))
				.name("バグ")
				.disp_seq(Integer.valueOf(1))
				.project_id(projectId)
				.versionNo(Integer.valueOf(111))
				.build());
		kindList.add(TicketKindDto.builder()
				.id(null)
				.name("操作ミス")
				.disp_seq(Integer.valueOf(2))
				.project_id(projectId)
				.build());
		TicketKindSaveRequestDto requestDto = TicketKindSaveRequestDto.builder()
				.project_id(projectId)
				.kindList(kindList)
				.build();
		// テスト対象のサービスを呼び出す
		try {
			StatusResponseDto responseDto = this.ticketService.saveTicketKind(
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
			kindList = outDto.getKindList();
			assertNotNull("種類データあり", kindList);
			assertEquals("種類一覧件数", kindList.size(), 2);
			// +0
			TicketKindDto kindDto = kindList.get(0);
			assertEquals("+0:プロジェクトID", kindDto.getProject_id(), projectId);
			assertEquals("+0:種類ID", kindDto.getId(), Long.valueOf(11L));
			assertEquals("+0:種類表示順", kindDto.getDisp_seq(), Integer.valueOf(1));
			assertEquals("+0:種類名称", kindDto.getName(), "バグ");
			assertEquals("+0:種類データバージョン", kindDto.getVersionNo(), Integer.valueOf(12));
			// +1
			kindDto = kindList.get(1);
			assertEquals("+1:プロジェクトID", kindDto.getProject_id(), projectId);
			assertEquals("+1:種類ID", kindDto.getId(), Long.valueOf(16L));
			assertEquals("+1:種類表示順", kindDto.getDisp_seq(), Integer.valueOf(2));
			assertEquals("+1:種類名称", kindDto.getName(), "操作ミス");
			assertEquals("+1:種類データバージョン", kindDto.getVersionNo(), Integer.valueOf(1));
		} catch (Exception ex) {
			fail("テスト異常終了 " + ex.toString());
		}
	}
}