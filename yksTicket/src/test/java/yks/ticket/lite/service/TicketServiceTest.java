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
		assertEquals("チケットID", ticket.getId(), Long.valueOf(2L));
		assertEquals("タイトル", ticket.getTitle(), "ccc");
		assertEquals("説明", ticket.getDescription(), "ddd");
		assertEquals("ステータスID", ticket.getStatus_id(), Long.valueOf(202L));
		assertEquals("開始日", sdfDate.format(ticket.getStart_date()), "2018-09-25");
		assertEquals("終了日", sdfDate.format(ticket.getFinish_date()), "2018-09-26");
		assertEquals("進捗ID", ticket.getProgress_id(), Long.valueOf(1L));
		assertEquals("プロジェクトID", ticket.getProject_id(), Long.valueOf(2L));
		assertEquals("バージョンNo", ticket.getVersionNo(), Integer.valueOf(2));
		// +1
		ticket = list.get(1);
		assertEquals("チケットID", ticket.getId(), Long.valueOf(3L));
		assertEquals("タイトル", ticket.getTitle(), "eee");
		assertEquals("説明", ticket.getDescription(), "fff");
		assertEquals("ステータスID", ticket.getStatus_id(), Long.valueOf(203L));
		assertNull("開始日", ticket.getStart_date());
		assertNull("終了日", ticket.getFinish_date());
		assertEquals("進捗ID", ticket.getProgress_id(), Long.valueOf(5L));
		assertEquals("プロジェクトID", ticket.getProject_id(), Long.valueOf(2L));
		assertEquals("バージョンNo", ticket.getVersionNo(), Integer.valueOf(6));
	}
}