package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResponseDto {
	/** ユーザ識別子 */
	private Long id;
	/** 言語名称 */
	private String name;
	/** 国名 */
	private String country;
	/** 備考 */
	private String remarks;
	/** バージョンNo */
	private Integer versionNo;
}