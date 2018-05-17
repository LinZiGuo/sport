package cn.itcast.web.action.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.tomcat.jni.OS;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itcast.bean.product.Brand;
import cn.itcast.service.product.BrandService;
import cn.itcast.utils.UploadFile;
@Controller("/control/brand/manage")
@ParentPackage("struts-default")
@Namespace("/control/brand/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="queryUI",type="dispatcher",location="/WEB-INF/page/product/query_brand.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/product/add_brand.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/product/edit_brand.jsp")
})
public class BrandManageAction extends ActionSupport implements ModelDriven<Brand> {
	private Brand brand = new Brand();
	private File logofile;
	private String logofileContentType;
	private String logofileFileName;
	private String destPath;

	public File getLogofile() {
		return logofile;
	}

	public void setLogofile(File logofile) {
		this.logofile = logofile;
	}

	public String getLogofileContentType() {
		return logofileContentType;
	}

	public void setLogofileContentType(String logofileContentType) {
		this.logofileContentType = logofileContentType;
	}

	public String getLogofileFileName() {
		return logofileFileName;
	}

	public void setLogofileFileName(String logofileFileName) {
		this.logofileFileName = logofileFileName;
	}

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	@Resource
	private BrandService brandService;
	@Override
	public Brand getModel() {
		return brand;
	}
	
	@Action("queryUI")
	public String queryUI() {
		return "queryUI";
	}
	
	/**
	 * 获取添加品牌页面
	 * @return
	 */
	@Action("addUI")
	public String addUI() {
		return "addUI";
	}
	
	/**
	 * 添加品牌
	 * @return
	 * @throws Exception 
	 */
	@Action("add")
	public String add() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//判断是否图片类型
		if (!UploadFile.validateImageFileType(logofileContentType, logofileFileName)) {
			request.setAttribute("message", "图片格式不正确");
			return SUCCESS;
		}
		if (logofile != null && logofile.length() > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
			//构建图片保存的目录
			String logopathdir = "/images/brand/" + dateFormat.format(new Date());
			//得到图片保存目录的真实路径
			String logorealpathdir = request.getSession().getServletContext().getRealPath(logopathdir);
			File logosavedir = new File(logorealpathdir);
			//如果目录不存在就创建
			if (!logosavedir.exists()) {
				logosavedir.mkdirs();
			}
			//构建文件名称
			String ext = UploadFile.getExt(logofileFileName);
			String imagename = UUID.randomUUID().toString() + "." + ext;
			brand.setLogopath(logopathdir+"/"+imagename);
			brand.setCode(imagename.substring(0, 36));
			FileInputStream fileInputStream = new FileInputStream(logofile);
			FileOutputStream fileOutputStream = new FileOutputStream(new File(logorealpathdir, imagename));
			byte[] bs = new byte[1024];
			int len;
			while ((len = fileInputStream.read(bs)) != -1) {
				fileOutputStream.write(bs, 0, len);
			}
			fileInputStream.close();
			fileOutputStream.close();
		}
		brandService.save(brand);
		request.setAttribute("message", "添加品牌成功");
		request.setAttribute("urladdress", "/control/brand/list");
		return SUCCESS;
	}
	
	/**
	 * 获取修改品牌页面
	 * @return
	 */
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String code = request.getParameter("code");
		brand = brandService.find(code);
		request.setAttribute("brand", brand);
		return "editUI";
	}
	
	/**
	 * 修改品牌
	 * @return
	 * @throws Exception 
	 */
	@Action("edit")
	public String edit() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Brand newBrand = brandService.find(request.getParameter("code"));
		newBrand.setName(brand.getName());
		//判断是否图片类型
		if (!UploadFile.validateImageFileType(logofileContentType, logofileFileName)) {
			request.setAttribute("message", "图片格式不正确");
			return SUCCESS;
		}
		if (logofile != null && logofile.length() > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
			//构建图片保存的目录
			String logopathdir = "/images/brand/" + dateFormat.format(new Date());
			//得到图片保存目录的真实路径
			String logorealpathdir = request.getSession().getServletContext().getRealPath(logopathdir);
			File logosavedir = new File(logorealpathdir);
			//如果目录不存在就创建
			if (!logosavedir.exists()) {
				logosavedir.mkdirs();
			}
			//构建文件名称
			String ext = UploadFile.getExt(logofileFileName);
			String imagename = UUID.randomUUID().toString() + "." + ext;
			newBrand.setLogopath(logopathdir+"/"+imagename);
			FileInputStream fileInputStream = new FileInputStream(logofile);
			FileOutputStream fileOutputStream = new FileOutputStream(new File(logorealpathdir, imagename));
			byte[] bs = new byte[1024];
			int len;
			while ((len = fileInputStream.read(bs)) != -1) {
				fileOutputStream.write(bs, 0, len);
			}
			fileInputStream.close();
			fileOutputStream.close();
		}
		brandService.update(newBrand);
		request.setAttribute("message", "修改品牌成功");
		request.setAttribute("urladdress", "/control/brand/list");
		return SUCCESS;
	}
}
