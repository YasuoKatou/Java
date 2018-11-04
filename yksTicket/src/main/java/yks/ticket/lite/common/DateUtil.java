package yks.ticket.lite.common;

/**
 * 日付ユーティリティ.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public class DateUtil {

	/**
	 * 日付クラスの変換.
	 *
	 * @param date java.util.Date
	 * @return java.sql.Date
	 * @since 0.0.1
	 */
	public static java.sql.Date toSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 日付を文字列に変換.
	 *
	 * @param date 変換する日付
	 * @return yyyy-mm-dd
	 * @since 0.0.1
	 */
	public static String toDateString(java.sql.Date date) {
		if (date != null) {
			return date.toString();
		}
		return null;
	}

	/**
	 * 文字列の日付を日付型に変換する.
	 *
	 * @param date 変換する日付文字列
	 * @return 日付オブジェクト
	 * @since 0.0.1
	 */
	public static java.sql.Date toSqlDate(String date) {
		if (date != null) {
			return java.sql.Date.valueOf(date);
		}
		return null;
	}
}