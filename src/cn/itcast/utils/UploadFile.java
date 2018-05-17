package cn.itcast.utils;

import java.io.File;
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

	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();
	}
}
