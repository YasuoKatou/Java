package yks.ticket.lite.dto;

import lombok.Data;

@Data
public class RequestHeaderDto {
	@Data
	public static class RequestHeader {
		/** セッションID. */
		private String session_id;
	}
	private RequestHeader header;
}