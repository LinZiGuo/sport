package cn.itcast.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * 文件上传工具类
 * @author 郭子灵
 *
 */
public class UploadFile {

	/**
	 * 校验图片文件类型
	 * @param fileContentType	文件类型
	 * @param fileName			文件名称
	 * @return
	 */
	public static boolean validateImageFileType(String fileContentType,String fileName) {
		if(fileContentType!=null){
			List<String> arrowType = Arrays.asList("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");
			List<String> arrowExtension = Arrays.asList("gif","jpg","bmp","png");
			String ext = getExt(fileName);
			return arrowType.contains(fileContentType.toLowerCase()) && arrowExtension.contains(ext);
		}
		return true;
	}

	/**
	 * 获取文件后缀名
	 * @param fileName	文件名称
	 * @return			后缀名
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();
	}
	
	/**
	 * 保存文件
	 * @param realpathdir	存放目录
	 * @param file			上传的文件
	 * @param fileName		文件名称
	 * @return				保存的文件
	 * @throws Exception
	 */
	public static File saveFile(String realpathdir, File file,String fileName) throws Exception{
		File savedir = new File(realpathdir);
		//如果目录不存在就创建
		if(!savedir.exists()) savedir.mkdirs();
		FileInputStream fileInputStream = new FileInputStream(file);
		FileOutputStream fileOutputStream = new FileOutputStream(new File(realpathdir,fileName));
		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			fileOutputStream.write(bs, 0, len);
		}
		fileInputStream.close();
		fileOutputStream.close();
		return file;
	}
}
