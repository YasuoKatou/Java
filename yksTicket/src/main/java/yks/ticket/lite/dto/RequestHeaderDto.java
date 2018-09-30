package yks.ticket.lite.dto;

import lombok.Data;

/**
 * リクエストヘッダーDto.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Data
public class RequestHeaderDto {
	@Data
	public static class RequestHeader {
		/** セッションID. */
		private String session_id;
	}
	private RequestHeader header;
}