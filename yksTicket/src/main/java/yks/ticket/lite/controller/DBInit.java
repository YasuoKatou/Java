package yks.ticket.lite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yks.ticket.lite.dto.StatusResponseDto;
import yks.ticket.lite.service.DBInitService;

@Controller
@RequestMapping(value="/yksticket/admin")
public class DBInit {
	@Autowired private DBInitService dbInitService;

	@GetMapping(value="/dbinit")
	@ResponseBody
	public StatusResponseDto dbinit() {
		dbInitService.doInit();
		// 正常終了を戻す
		return StatusResponseDto.builder()
				.status(StatusResponseDto.SUCCESS)
				.build();
	}
}