package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ情報取得リクエストDto.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationRequestDto extends RequestHeaderDto {
	/** ユーザ識別子 */
	private Long id;
}