package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 処理結果を戻すDto.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class StatusResponseDto {
	/** 正常終了 : OK */
	public static final String SUCCESS = "OK";
	/** 異常終了 : NG */
	public static final String FAIL    = "NG";
	/** 処理結果 */
	private String status;
}