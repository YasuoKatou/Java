package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
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
import yks.ticket.lite.dto.TicketDto;
import yks.ticket.lite.dto.TicketListRequestDto;
import yks.ticket.lite.dto.TicketListResponseDto;
import yks.ticket.lite.dto.TicketMastersRequestDto;
import yks.ticket.lite.dto.TicketMastersResponseDto;
import yks.ticket.lite.dto.TicketProgressDto;
import yks.ticket.lite.dto.TicketStatusDto;

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
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		// テスト対象のサービスを呼び出す
		TicketListResponseDto outDto = this.ticketService.getTicketList(TicketListRequestDto.builder()
				.project_id(Long.valueOf(2L))
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
		assertEquals("+0:プロジェクトID", ticket.getProject_id(), Long.valueOf(2L));
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
		assertEquals("+1:プロジェクトID", ticket.getProject_id(), Long.valueOf(2L));
		assertEquals("+1:バージョンNo", ticket.getVersionNo(), Integer.valueOf(6));
	}

	/**
	 * チケット関連マスタ取得をテスト
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:TicketServiceTest_D02/")
	public void test_getTicketMasters() {
		// テスト対象のサービスを呼び出す
		TicketMastersResponseDto outDto = this.ticketService.getTicketMasters(
				TicketMastersRequestDto.builder().project_id(Long.valueOf(2L)).build());

		// サービスの戻り値を判定する.
		assertNotNull("戻り値あり", outDto);
		List<TicketStatusDto> statusList = outDto.getStatusList();
		assertNotNull("ステータスデータあり", statusList);
		assertEquals("ステータス一覧件数", statusList.size(), 3);
		// +0
		TicketStatusDto statusDto = statusList.get(0);
		assertEquals("+0:プロジェクトID", statusDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+0:ステータスID", statusDto.getId(), Long.valueOf(1L));
		assertEquals("+0:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(111));
		assertEquals("+0:ステータス名称", statusDto.getName(), "未着手");
		assertEquals("+0:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(11));
		// +1
		statusDto = statusList.get(1);
		assertEquals("+1:プロジェクトID", statusDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+1:ステータスID", statusDto.getId(), Long.valueOf(2L));
		assertEquals("+1:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(112));
		assertEquals("+1:ステータス名称", statusDto.getName(), "作業中");
		assertEquals("+1:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(12));
		// +2
		statusDto = statusList.get(2);
		assertEquals("+2:プロジェクトID", statusDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+2:ステータスID", statusDto.getId(), Long.valueOf(3L));
		assertEquals("+2:ステータス表示順", statusDto.getDisp_seq(), Integer.valueOf(113));
		assertEquals("+2:ステータス名称", statusDto.getName(), "完了");
		assertEquals("+2:ステータスデータバージョン", statusDto.getVersionNo(), Integer.valueOf(13));

		List<TicketProgressDto> progressList = outDto.getProgressList();
		assertNotNull("進捗データあり", progressList);
		assertEquals("進捗一覧件数", progressList.size(), 3);
		// +0
		TicketProgressDto progressDto = progressList.get(0);
		assertEquals("+0:プロジェクトID", progressDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+0:進捗ID", progressDto.getId(), Long.valueOf(11L));
		assertEquals("+0:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(1));
		assertEquals("+0:進捗名称", progressDto.getName(), "10%");
		assertEquals("+0:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(111));
		// +1
		progressDto = progressList.get(1);
		assertEquals("+1:プロジェクトID", progressDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+1:進捗ID", progressDto.getId(), Long.valueOf(12L));
		assertEquals("+1:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(2));
		assertEquals("+1:進捗名称", progressDto.getName(), "50%");
		assertEquals("+1:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(112));
		// +2
		progressDto = progressList.get(2);
		assertEquals("+2:プロジェクトID", progressDto.getProject_id(), Long.valueOf(2L));
		assertEquals("+2:進捗ID", progressDto.getId(), Long.valueOf(13L));
		assertEquals("+2:進捗表示順", progressDto.getDisp_seq(), Integer.valueOf(3));
		assertEquals("+2:進捗名称", progressDto.getName(), "100%");
		assertEquals("+2:進捗データバージョン", progressDto.getVersionNo(), Integer.valueOf(113));
	}
}