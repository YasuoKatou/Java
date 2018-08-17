package yks.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ImportReader {
  private String repoPath = null;
  public ImportReader (String mvnSetup) throws Exception {
    String repoPath = this.readRepositoryPath(mvnSetup);
    this.repoPath = this.checkRepositoryPath(repoPath);
    System.out.println("repository path : " + this.repoPath);
  }

  /**
   * 指定の設定ファイルからリポジトリパスを取得する
   * @param mvnSetup maven settings.xmlのパス
   * @return リポジトリパス
   */
  private String readRepositoryPath(String mvnSetup) throws Exception {
    System.out.println("maven settings : " + mvnSetup);
    MavenSttringsReader reader = new MavenSttringsReader(mvnSetup);
    return reader.getLocalRepository();
  }

  private List<File> findJar() {
    List<File> jarFiles = new ArrayList<>();
    Stack<File> stack = new Stack<>();
    stack.push(new File(this.repoPath));
    while (!stack.isEmpty()) {
      File item = stack.pop();
      if (item.isFile() && item.getName().toLowerCase().endsWith(".jar")) {
//        System.out.println(item);
        jarFiles.add(item);
      } else if (item.isDirectory()) {
        for (File child : item.listFiles()) stack.push(child);
      }
    }
    return jarFiles;
  }

  private final Pattern includePtn1 = Pattern.compile("\\.class$");
  private final Pattern excludePtn1 = Pattern.compile("\\$\\d+\\.class$");

  private void makeDB(List<File> jarFiles, String dbPath) throws Exception {
    try (Connection conn = DriverManager.getConnection("jdbc:derby:" + dbPath)) {
      System.out.println("connected " + dbPath);
      for (File file : jarFiles) {
//        file = new File("/home/pi/.m2/repository/jdom/jdom/1.0/jdom-1.0.jar");
        System.out.println(file);
        int jarId = this.getJarId(conn, file.toString().substring(this.repoPath.length()+1));
        this.cleanClassList(conn, jarId);
        try (JarFile jarFile = new JarFile(file)) {
          for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
            String path = e.nextElement().getName();
            // ファイルの拡張子が「.class」で無い時、無視する
            if (!this.includePtn1.matcher(path).find()) continue;
            // privateのインナークラスを無視する
            if (this.excludePtn1.matcher(path).find()) continue;
            path = path.replace("$", "/");		//インナークラス対応
            System.out.println(path);
            this.saveClassList(conn, jarId, path);
          }
        }
//        break;		//ループを１回で抜けたい時、有効にする
      }
    }
  }

  /**
   * パッケージ情報を登録する.
   * @param conn DBの接続情報
   * @param jarId jarファイルID
   * @param packageFqcn パッケージ(FQCN)
   * @throws Exception DBアクセス異常
   */
  private void saveClassList(Connection conn, int jarId, String packageFqcn)
  throws Exception {
    File file = new File(packageFqcn);
    String packageName = file.getParent();
    if (packageName == null) {
      packageName = "";
    } else {
      packageName = packageName.replaceAll("/", ".");
    }
    String className   = file.getName().replace(".class", "");
//    System.out.println(packageName + ", " + className);
    String sql = "insert into java.class_list (jar_id, class_name, package_name)"
               + "values(" + Integer.toString(jarId) + ", '"
               + className + "', '" + packageName + "')";
    try (Statement statement = conn.createStatement()) {
        int num = statement.executeUpdate(sql);
        if (num != 1) {
            throw new Exception("SQL Error : " + sql);
          }
      }
  }

  /**
   * クラスリストテーブルをクリアする.
   * @param conn DBの接続情報
   * @param jarId クリアするjarファイルID
   * @throws Exception DBアクセス異常
   */
  private void cleanClassList(Connection conn, int jarId) throws Exception {
    String sql = "delete from java.class_list where jar_id = "
               + Integer.toString(jarId);
    try (Statement statement = conn.createStatement()) {
      int num = statement.executeUpdate(sql);
      System.out.println("cleaned class_list " + Integer.toString(num));
    }
  }

  /**
   * jarファイルのIDを取得する.
   * @param conn DBの接続情報
   * @param jarPath jarファイル（パスを含む）
   * @return jarファイルID
   * @throws Exception DBアクセス異常
   */
  private int getJarId(Connection conn, String jarPath) throws Exception {
    String sql;
    int jarId = -1;
    File file = new File(jarPath);
    // 既にパスとファイル名が登録されている場合、jar_idを戻す
    sql = "select jar_id from java.jar_list where jar_file_name = '"
        + file.getName() + "' and repo_path = '"
        + file.getParent() + "'";
    try (Statement statement = conn.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(sql)) {
        if (resultSet.next()) {
          return resultSet.getInt("jar_id");
        }
      }
      // 新規のjar_idを決定し、パスとファイル名を登録する。
      sql = "select max(jar_id) jar_id from java.jar_list";
      try (ResultSet resultSet = statement.executeQuery(sql)) {
        if (resultSet.next()) {
          Integer val = (Integer)resultSet.getObject("jar_id");
          if (val == null) {
            jarId = 1;
          } else {
            jarId = val.intValue() + 1;
          }
        }
      }
      sql = "insert into java.jar_list (jar_id, jar_file_name, repo_path)"
          + "values(" + Integer.toString(jarId)
          + ", '" + file.getName() + "', '" + file.getParent() + "')";
      int num = statement.executeUpdate(sql);
      if (num != 1) {
        throw new Exception("SQL Error : " + sql);
      }
    }
    return jarId;
  }

  /**
   * 
   * @param
   *  args[0] : DBパス
   *  args[1] : maven settins.xmlへのパス
   */
  public static void main(String[] args) {
    try {
      ImportReader reader = new ImportReader(args[1]);
      List<File> jarFiles = reader.findJar();
      reader.makeDB(jarFiles, args[0]);
      System.out.println("bye bye ...");
    } catch (Exception ex) {
      System.out.println("Error : " + ex.toString());
    }
  }

  private String checkRepositoryPath(String path) throws Exception {
    if (path != null) return path;
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.startsWith("linux")) {
      String userHome = System.getProperty("user.home");
      return userHome + "/.m2/repository";
    }
    throw new Exception("no local repository");
  }

  /**
   * mavenのsetteings.xmlファイルを読込む.
   * xmlの構造は確認しない.localRepositoryタグの内容のみを取得する.
   */
  public class MavenSttringsReader extends DefaultHandler {
    private boolean inLocalRepositoryTag = false;
    private String localRepositoryTag = null;
    private static final String TAG_LOCAL_REPOSITORY = "localRepository";
    public MavenSttringsReader(String path) throws Exception {
      System.out.println("start MavenSttringsReader");
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxParserFactory.newSAXParser();
      saxParser.parse(new File(path), this);
    }
//    @Override
//    public void startDocument() throws SAXException {
//      System.out.println("startDocument");
//    }
    @Override
    public void endDocument() throws SAXException {
//      System.out.println("endDocument");
      if (this.localRepositoryTag == null) {
        System.out.println("no " + TAG_LOCAL_REPOSITORY);
      } else {
        System.out.println(TAG_LOCAL_REPOSITORY + " : "
        + this.localRepositoryTag);
      }
    }
    @Override
    public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException{
//      System.out.println("startElement : " + qName);
      this.inLocalRepositoryTag = TAG_LOCAL_REPOSITORY.equals(qName);
    }
    @Override
    public void endElement (String uri, String localName, String qName) {
//      System.out.println("endElement : " + qName);
      this.inLocalRepositoryTag = false;
    }
    @Override
    public void characters (char ch[], int start, int length)
    throws SAXException {
      if (!inLocalRepositoryTag) return;
      String value = new String(ch, start, length);
      System.out.println("characters : " + value);
      this.localRepositoryTag = value;
    }
    public String getLocalRepository() {
      return this.localRepositoryTag;
    }
  }
}