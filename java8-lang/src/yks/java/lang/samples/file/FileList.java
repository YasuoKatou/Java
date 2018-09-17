package yks.java.lang.samples.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * ファイルの一覧を作成する
 * 
 * @author yasuokatou(YKS)
 */
public class FileList {

	public static List<File> makeFileList(String rootPath, String fileExtension, String withSubDir)
	throws Exception {
		List<File> pathList = new ArrayList<>();
		int maxDepth = "true".equals(withSubDir) ? Integer.MAX_VALUE : 1;
		Path rootDir = Paths.get(rootPath);
		try (final Stream<Path> pathStream = Files.walk(rootDir, maxDepth)) {
			pathStream
				.map(path -> path.toFile())
				.filter(file -> !file.isDirectory())
				.filter(file -> file.getName().toLowerCase().endsWith(fileExtension))
				.forEach(pathList::add);
		}
		return pathList;
	}

	/**
	 * ファイルの一覧を作成する
	 * @param args
	 * [0]	検索を行うルートパス
	 * [1]	ファイルの拡張子
	 * [2]	サブディレクトリを検索する場合、"true"
	 */
	public static void main(String[] args) {
		try {
			List<File> list = FileList.makeFileList(args[0], args[1], args[2]);
			list.stream().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}