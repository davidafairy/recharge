/**
 * 
 */
package com.redstoneinfo.platform.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author zhangxiaorong
 *
 * 2014-2-27
 */
public class FileUtil {

	// 验证字符串是否为正确路径名的正则表达式  
	private static String pathMatches = "[A-Za-z]:\\\\[^:?\"><*]*";  
	
	static final public int MAGIC = 0xCAFEBABE;
	
	
	public static String getFullyQualifiedName(File file)
			throws IOException {

		String name = null;

		try {

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			if (in.readInt() != MAGIC) 
				throw new IOException("Not a class file");

			in.readUnsignedShort();// 次版本号
			in.readUnsignedShort();// 主版本号
			in.readUnsignedShort();// 长度
			in.readByte();// CLASS=7
			in.readUnsignedShort();// 忽略这个地方
			in.readByte();// UTF8=1
			name = in.readUTF();// 类的名字!!!

			in.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return name;
	}
	
	
	/**
	 * 层层往上检查，如果文件夹是空就删除，一直到文件夹不为空，或者删不掉为止
	 * @param pfile 文件夹对象
	 */
	public static void deleteEmptyFolder(File pfile){
		if (pfile.list().length == 0){
			boolean b = delete(pfile);
			if (!b)
				return;
			deleteEmptyFolder(pfile.getParentFile());
		}
	}
	
	
	/** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	public static boolean delete(File file) {  
	    
	    // 判断目录或文件是否存在  
	    if (!file.exists())   
	        return false;  
	    
	    else {  
	        // 判断是否为文件  
	        if (file.isFile()) 
	            return deleteFile(file);  
	        
	         else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(file);  
	        }  
	    }  
	}
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(File file) {  
	    
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        return true;  
	    }  
	    return false;  
	}
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(File dirFile) {  
	      	    
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory())  
	        return false;  
	    
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	    	
	        //删除子文件  
	        if (files[i].isFile()) {  
	            if (!deleteFile(files[i])) 
	    	        return false;  
	        }   
	        
	        else {  //删除子目录
	            if (!deleteDirectory(files[i])) 
	    	        return false;  
	        }  
	    }  
	    
	    //删除当前目录  
	    if (dirFile.delete())  
	        return true;  
	    else   
	        return false;  
	} 
}
