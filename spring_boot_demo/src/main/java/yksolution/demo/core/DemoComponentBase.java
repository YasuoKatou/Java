package yksolution.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoComponentBase {
  /**
   * サブクラスで使用可能なロガー
   * サブクラス分のロガーインスタンスが生成されるため、サブクラスが大量に生成
   * される場合、logger2の定義で示すstaticロガーを生成することを勧める
   */
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * @Deprecated logger変数をコメントアウト後、logger2をloggerにリネームして使用して下さい。
   * 但し、ログに出力されるパッケージ＋クラス名は、このクラスのものになります。
   */
  @Deprecated
  protected static Logger logger2 = LoggerFactory.getLogger(DemoComponentBase.class);
}