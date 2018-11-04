package yks.ticket.lite.common;

/**
 * 定数定義
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public class TicketApConstatnt {
	/**
	 * ファイルパスに関する定義
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	public static class FilePath {
		/** システムインストール時に初期投入するデータ定義ファイル */
		public static final String AP_INIT_JSON = "./ap_init_data.json";
	}

	/**
	 * チケットに関するメッセージの定義
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	public static class TicketMessage {
		public static final String NEW_TICKET_MESSAGE = "新規チケット登録";
	}
}