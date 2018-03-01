package com.qingyii.hxt.util;

import android.content.Context;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件处理工具类
 * 
 * @author shelia
 * 
 */
public class ZipUtil {
	/**
	 * 文件重命名
	 * 
	 * @param filePath
	 *            文件目录
	 * @param olderName
	 *            旧文件名
	 * @param newName
	 *            新文件名
	 * @return
	 */
	public static boolean renameFile(String filePath, String olderName,
			String newName) {
		boolean flag = false;
		try {
			File file = new File(filePath + "/" + olderName);
			if (file.exists()) {
				file.renameTo(new File(filePath + "/" + newName));
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 解压ZIP文件
	 * 
	 * @param filePath
	 *            zip文件路径
	 * @param unzipDir
	 *            解压存放目录
	 * @return
	 */
	public static boolean unZip(String filePath, String unzipDir) {
		boolean flag = false;
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(filePath);
			// Extracts all files to the path specified
			zipFile.extractAll(unzipDir);
			flag = true;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		boolean isok = true;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
			} else {
				isok = false;
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
			isok = false;
		}
		return isok;

	}

	/**
	 * assets文件复制到指定目录下
	 * 
	 * @param context
	 *            上下文
	 * @param assetsFileName
	 * @param fileName
	 *            保存路径
	 * @throws IOException
	 */
	public static boolean assetsDataToSD(Context context,
			String assetsFileName, String fileName) throws IOException {
		boolean flag = false;
		try {
			InputStream myInput;
			OutputStream myOutput = new FileOutputStream(fileName);
			myInput = context.getAssets().open(assetsFileName);
			byte[] buffer = new byte[1024];
			int length = myInput.read(buffer);
			while (length > 0) {
				myOutput.write(buffer, 0, length);
				length = myInput.read(buffer);
			}

			myOutput.flush();
			myInput.close();
			myOutput.close();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * String写入文件
	 * 
	 * @param fileName
	 *            文件保存路径
	 * @param write_str
	 * @throws IOException
	 */
	public static boolean writeFileSdcardFile(String fileName, String write_str) {
		boolean flag = true;
		try {

			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = write_str.getBytes();

			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if(!"HXT".equals(myFilePath.getName())){
				//HXT根目录不能删除
				myFilePath.delete(); // 删除空文件夹
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}
