/**
 * 
 */
package com.redstoneinfo.platform.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;


/**
 * @author zhangxiaorong
 *
 * 2014-2-26
 * 
 * 这个ZipOutputStream用的是apache的，它可以设定编码，
 * java.util.zip的ZipOutputStream只能默认utf-8的编码，而一些工具可能不支持utf-8的解码
 */
public class ZipFileUtil {
	static Log log = LogFactory.getLog(ZipFileUtil.class);

	/**
	 * packagePath 制作的zip文件所在的文件夹的地址
	 * zipFileName 制作的zip文件名
	 * originFileList 用来制作的源文件File集合
	 */
	public static void makeZipFile(String packagePath, String zipFileName, List<File> originFileList, String encoding){
		try {
			if (!zipFileName.endsWith(".zip"))
				zipFileName += ".zip";
			
			File file = new File(packagePath + "\\" + zipFileName);
			if (file.exists())
				return;
			
			FileOutputStream fout = new FileOutputStream(file);
			ZipOutputStream zipStream = new ZipOutputStream(fout);
			zipStream.setEncoding(encoding);
			
			for (int i = 0; originFileList != null && i < originFileList.size(); i++){
				writeToZipFile(originFileList.get(i), zipStream, "");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
		
	
	/**
	 * 
	 * @param originFileList 源文件
	 * @return 返回一个压缩包的流
	 */
	public static ByteArrayOutputStream makeZipFileStream(List<File> originFileList, String encoding){
		if (originFileList == null || originFileList.size() == 0)
			return null;
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zipStream = new ZipOutputStream(bout);
		zipStream.setEncoding(encoding);
		
		for (int i = 0; i < originFileList.size(); i++){
			writeToZipFile(originFileList.get(i), zipStream, "");
		}
		return bout;
	}

	
	/**
	 * 
	 * @param innerPath 压缩包内的文件路径
	 * @param rightType 要求的文件类型,如果是文件夹，则不起作用
	 * @return
	 */
	public static List<byte[]> readFromZipFile(String innerPath, String[] rightType){
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			ZipFile file = new ZipFile(new File(innerPath));
			InputStream is = null;
			Enumeration<? extends ZipEntry> enumer = file.getEntries();
			
			while( enumer.hasMoreElements()){
				byte[] bytes = null;
				ZipEntry entry = enumer.nextElement();
				is = file.getInputStream(entry);
				String fileName = entry.getName().toLowerCase();
				
				if (entry.isDirectory()){
					bytes = StreamUtil.bufferedReadToByteArray(is);
					list.add(bytes);
				}
				
				else{
					for (int i = 0; i < rightType.length; i++){
						if (fileName.endsWith(rightType[0])){
							bytes = StreamUtil.bufferedReadToByteArray(is);
							list.add(bytes);	
						}
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

	 /**
     * 功能:将一个指定目录压缩
     * @param srcfile：要压缩文件目录
     * @param zipfile：压缩之后的文件路径
     */
    public static void zipFiles(String srcPath,File zipFile){
        
       File srcdir = new File(srcPath);
        if (!srcdir.exists()) throw new RuntimeException(srcPath + "不存在！");
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcdir);
        zip.addFileset(fileSet); 
        zip.execute();
    }
    
	private static void writeToZipFile(File file, ZipOutputStream zipStream, String parentEntryName) {
		
		if (file == null)
			return;
		
		String entryName = parentEntryName + "\\" + file.getName();
		if (file.isFile()){
			try {
				ZipEntry entry = new ZipEntry(entryName);
				zipStream.putNextEntry(entry);
				InputStream in = StreamUtil.getInputStream(file);
				byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
				zipStream.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (file.isDirectory()){
			File[] files = file.listFiles();
			for (File f : files){
				writeToZipFile(f, zipStream, entryName);
			}
		}
	}


	public static void main(String[] s){

		List<File> originFileList = new ArrayList<File>();
		originFileList.add(new File("E:\\work1\\rsplatform\\WebContent\\uploadFile\\applyDocument\\马鞍街道\\renewTest1\\2014-03-01"));
		
		ByteArrayOutputStream bout = ZipFileUtil.makeZipFileStream(originFileList, "gbk");
    	StreamUtil.bufferedWriteByteArray(bout.toByteArray(), "c:\\aaaaa.zip");

		makeZipFile("c:\\", "aa", originFileList, "gbk");
		
		zipFiles("E:\\work1\\rsplatform\\WebContent\\uploadFile\\applyDocument" +
				"\\马鞍街道\\renewTest1\\2014-03-01", new File("c:\\cc.zip"));

		System.out.println('\u0000');
	}
	
	
	  public static void zipFile(String intfileName,String outFileUrl,String outFileName) throws Exception{
	    	File f = new File(intfileName);
	        FileInputStream fis = new FileInputStream(f);
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        byte[] buf = new byte[1024];
	        int len;
	        File folder = new File(MimeUtility.decodeText(outFileUrl));
			if (!folder.exists()) {
				folder.mkdirs();
			}
	        FileOutputStream fos = new FileOutputStream(outFileUrl+"/"+outFileName);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        ZipOutputStream zos = new ZipOutputStream(bos);
	        ZipEntry ze = new ZipEntry(outFileName);
	        zos.putNextEntry(ze);
	        while((len=bis.read(buf))!=-1)
	        {
	           zos.write(buf,0,len);
	           zos.flush();
	        }
	        bis.close();
	        zos.close(); 
	    }
	    /**//*
	     * inputFileName 输入一个文件夹
	     * zipFileName 输出一个压缩文件夹
	     */
	    public static void zipFolder(String inputFileFolder,String outFileFolder,String fileName) throws Exception {
	    	log.info("获取需要压缩的文件夹路径为："+inputFileFolder+";压缩后的路径为:"+outFileFolder+"/"+fileName);
	    	      	File folder = new File(MimeUtility.decodeText(outFileFolder));
			if (!folder.exists()) {
				folder.mkdirs();
			}
	        zip(outFileFolder+"/"+fileName, new File(inputFileFolder));
	    }

	    private static void zip(String zipFileName, File inputFile) throws Exception {
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
	        zip(out, inputFile, "");
	        out.close();
	        log.info("压缩完成");
	         }

	    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
	        if (f.isDirectory()) {
	           File[] fl = f.listFiles();
	          // out.putNextEntry(new ZipEntry(base + "/"));
	           base = base.length() == 0 ? "" : base + "/";
	           for (int i = 0; i < fl.length; i++) {
	           zip(out, fl[i], base + fl[i].getName());
	         }
	        }else {
	           out.putNextEntry(new ZipEntry(base));
	           FileInputStream in = new FileInputStream(f);
	           int b;
	           log.info("压缩的文件为:"+base);
	          while ( (b = in.read()) != -1) {
	            out.write(b);
	         }
	         in.close();
	       }
	    }


}
