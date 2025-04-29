package com.luyang.framework.base.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类，提供了一系列对文件进行操作的静态方法。
 * @author wangjixin
 */
public final class FileUtils {

	private FileUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// ================== 文件存在性检查 ================== //

	/**
	 * 检查文件是否存在。
	 * @param filePath 文件的路径
	 * @return 如果文件存在，返回 true；否则返回 false
	 */
	public static boolean exists(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		return file.exists();
	}

	// ================== 文件读取 ================== //

	/**
	 * 以文本形式读取文件内容。
	 * @param filePath 文件的路径
	 * @return 文件的文本内容，如果文件不存在或读取失败，返回空字符串
	 */
	public static String readFileAsString(String filePath) {
		if (null == filePath) {
			return "";
		}
		Path path = Paths.get(filePath);
		try {
			return Files.readString(path);
		} catch (IOException e) {
			System.err.println("读取文件失败: " + e.getMessage());
			return "";
		}
	}

	/**
	 * 按行读取文件内容，返回每行内容的列表。
	 * @param filePath 文件的路径
	 * @return 包含文件每行内容的列表，如果文件不存在或读取失败，返回空列表
	 */
	public static List<String> readFileAsLines(String filePath) {
		if (null == filePath) {
			return new ArrayList<>();
		}
		Path path = Paths.get(filePath);
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			System.err.println("读取文件失败: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	// ================== 文件写入 ================== //

	/**
	 * 将文本内容写入文件。
	 * @param filePath 文件的路径
	 * @param content 要写入的文本内容
	 * @param append 是否以追加模式写入
	 * @return 如果写入成功，返回 true；否则返回 false
	 */
	public static boolean writeStringToFile(String filePath, String content, boolean append) {
		if (null == filePath || null == content) {
			return false;
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
			writer.write(content);
			return true;
		} catch (IOException e) {
			System.err.println("写入文件失败: " + e.getMessage());
			return false;
		}
	}

	// ================== 文件复制 ================== //

	/**
	 * 复制文件。
	 * @param sourceFilePath 源文件的路径
	 * @param destFilePath 目标文件的路径
	 * @return 如果复制成功，返回 true；否则返回 false
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		if (null == sourceFilePath || null == destFilePath) {
			return false;
		}
		Path sourcePath = Paths.get(sourceFilePath);
		Path destPath = Paths.get(destFilePath);
		try {
			Files.copy(sourcePath, destPath);
			return true;
		} catch (IOException e) {
			System.err.println("复制文件失败: " + e.getMessage());
			return false;
		}
	}

	// ================== 文件删除 ================== //

	/**
	 * 删除文件。
	 * @param filePath 文件的路径
	 * @return 如果删除成功，返回 true；否则返回 false
	 */
	public static boolean deleteFile(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	// ================== 获取文件扩展名 ================== //

	/**
	 * 获取文件的扩展名。
	 * @param filePath 文件的路径
	 * @return 文件的扩展名，如果没有扩展名，返回空字符串
	 */
	public static String getFileExtension(String filePath) {
		if (null == filePath) {
			return "";
		}
		int lastIndex = filePath.lastIndexOf('.');
		if (lastIndex != -1 && lastIndex < filePath.length() - 1) {
			return filePath.substring(lastIndex + 1);
		}
		return "";
	}
}
