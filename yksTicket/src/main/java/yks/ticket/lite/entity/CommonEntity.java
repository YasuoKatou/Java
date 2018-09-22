package yks.ticket.lite.entity;

import java.util.Date;

import lombok.Data;

/**
 * テーブルの共通項目
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Data
public class CommonEntity {
	/** 作成日時 */
	private Date createDate;
	/** 作成者ID */
	private long createUserId;
	/** 更新日時 */
	private Date updateDate;
	/** 更新者ID */
	private long updateUserId;
	/** バージョンNo */
	private Integer versionNo;
}