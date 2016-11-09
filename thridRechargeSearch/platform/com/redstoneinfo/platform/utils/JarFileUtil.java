/**
 * 
 */
package com.redstoneinfo.platform.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhangxiaorong
 *
 * 2014-2-26
 */
public class JarFileUtil {

	/**
	 * packagePath 制作的jar文件所在的文件夹的地址
	 * jarFileName 制作的jar文件名
	 * originFileList 用来制作的源文件File集合
	 */
	public static void makeJarFile(String packagePath, String jarFileName, List<File> originFileList){
		try {
			if (!jarFileName.endsWith(".jar"))
				jarFileName += ".jar";
			
			File file = new File(packagePath + "\\" + jarFileName);
			if (file.exists())
				return;
			
			FileOutputStream fout = new FileOutputStream(file);
			JarOutputStream jarStream = new JarOutputStream(fout);
			
			for (int i = 0; originFileList != null && i < originFileList.size(); i++){
				writeToJarFile(originFileList.get(i), jarStream, "");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param originFileList 源文件
	 * @return 返回一个压缩包的流
	 */
	public static ByteArrayOutputStream makeJarFileStream(List<File> originFileList){
		if (originFileList == null || originFileList.size() == 0)
			return null;
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		try {
			JarOutputStream jarStream = new JarOutputStream(bout);
			for (int i = 0; i < originFileList.size(); i++){
				writeToJarFile(originFileList.get(i), jarStream, "");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bout;
	}
	
	
	/**
	 * 
	 * @param innerPath 压缩包内的文件路径
	 * @param rightType 要求的文件类型,如果是文件夹，则不起作用
	 * @return
	 */
	public static List<byte[]> readFromJarFile(String innerPath, String[] rightType){
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			JarFile file = new JarFile(new File(innerPath));
			InputStream is = null;
			Enumeration<JarEntry> enumer = file.entries();
			
			while( enumer.hasMoreElements()){
				byte[] bytes = null;
				JarEntry entry = enumer.nextElement();
				is = file.getInputStream(entry);
				String fileName = entry.getName().toLowerCase();
				
				for (int i = 0; i < rightType.length; i++){
					if (fileName.endsWith(rightType[0])){
						bytes = StreamUtil.bufferedReadToByteArray(is);
						list.add(bytes);	
					}
				}
				is.close();
			}
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	private static void writeToJarFile(File file, JarOutputStream jarStream, String parentEntryName) {
		
		if (file == null)
			return;
		
		String entryName = parentEntryName + "\\" + file.getName();
		if (file.isFile()){
			try {
				JarEntry entry = new JarEntry(entryName);
				jarStream.putNextEntry(entry);
				InputStream in = StreamUtil.getInputStream(file);
				byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
				jarStream.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (file.isDirectory()){
			File[] files = file.listFiles();
			for (File f : files){
				writeToJarFile(f, jarStream, entryName);
			}
		}
	}

	
	public static void main(String[] s){
		List<File> originFileList = new ArrayList<File>();
		originFileList.add(new File("G:\\working2\\ecu_client\\src\\com\\redstoneinfo\\ecu\\manager\\StepPicBiz2.java"));
		originFileList.add(new File("G:\\working2\\ecu_client\\src\\com\\redstoneinfo\\ecu\\manager"));
		
		makeJarFile("c:\\", "aa", originFileList);
	}

}
