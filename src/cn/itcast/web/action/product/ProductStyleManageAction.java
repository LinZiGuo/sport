package cn.itcast.web.action.product;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bean.PageView;
import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductStyleService;
import cn.itcast.utils.ImageSizer;
import cn.itcast.utils.UploadFile;
@Controller("/control/product/style/manage")
@ParentPackage("struts-default")
@Namespace("/control/product/style/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/product/add_productstyle.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/product/edit_productstyle.jsp")
})
public class ProductStyleManageAction extends ActionSupport implements ModelDriven<ProductStyle> {
	private ProductStyle productStyle = new ProductStyle();
	private File imagefile;
	private String imagefileContentType;
	private String imagefileFileName;
	public File getImagefile() {
		return imagefile;
	}

	public void setImagefile(File imagefile) {
		this.imagefile = imagefile;
	}

	public String getImagefileContentType() {
		return imagefileContentType;
	}

	public void setImagefileContentType(String imagefileContentType) {
		this.imagefileContentType = imagefileContentType;
	}

	public String getImagefileFileName() {
		return imagefileFileName;
	}

	public void setImagefileFileName(String imagefileFileName) {
		this.imagefileFileName = imagefileFileName;
	}

	@Resource
	private ProductStyleService productStyleService;
	@Resource
	private ProductInfoService productInfoService;

	@Override
	public ProductStyle getModel() {
		return productStyle;
	}
	
	/**
	 * 获取添加样式页面
	 * @return
	 */
	@Action("addUI")
	public String addUI() {
		return "addUI";
	}
	
	/**
	 * 添加样式
	 * @return
	 * @throws Exception 
	 */
	@Action("add")
	public String add() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("productid");
		Integer productid = Integer.valueOf(id);
		String name = request.getParameter("stylename");
		//判断是否图片类型
		if (!UploadFile.validateImageFileType(imagefileContentType, imagefileFileName)) {
			request.setAttribute("message", "图片格式不正确");
			return SUCCESS;
		}
		if (imagefile.length()>204800) {
			request.setAttribute("message", "图片不能大于200K");
			return SUCCESS;
		}
		if (imagefile != null && imagefile.length() > 0) {
			ProductInfo productInfo = productInfoService.find((Serializable)productid);
			//构建原始图片保存的目录
			String pathdir = "/images/product/" + productInfo.getType().getTypeid() + "/" + productInfo.getId() + "/prototype";
			//得到原始图片保存目录的真实路径
			String realpathdir = request.getSession().getServletContext().getRealPath(pathdir);
			//构建压缩图片保存的目录
			String pathdir140 = "/images/product/" + productInfo.getType().getTypeid() + "/" + productInfo.getId() + "/140x";
			//得到压缩图片保存目录的真实路径
			String realpathdir140 = request.getSession().getServletContext().getRealPath(pathdir140);
			//构建文件名称
			String ext = UploadFile.getExt(imagefileFileName);
			String imagename = UUID.randomUUID().toString() + "." + ext;
			productStyle.setName(name);
			productStyle.setImagename(imagename);
			productStyle.setProduct(productInfo);
			File savedir140 = new File(realpathdir140);
			if(!savedir140.exists()) savedir140.mkdirs();
			File file140 = new File(realpathdir140, imagename);
			ImageSizer.resize(imagefile, file140, 140, ext);
			UploadFile.saveFile(realpathdir, imagefile, imagename);
		}
		productStyleService.save(productStyle);
		request.setAttribute("message", "添加样式成功");
		request.setAttribute("urladdress", "/control/product/style/list?productid="+id);
		return SUCCESS;
	}
	
	/**
	 * 获取修改样式页面
	 * @return
	 */
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer productstyleid = Integer.valueOf(request.getParameter("productstyleid"));
		productStyle = productStyleService.find(productstyleid);
		request.setAttribute("style", productStyle);
		return "editUI";
	}
	
	@Action("visible")
	public String visible() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String productid = request.getParameter("productid");
		String[] stylesids = request.getParameterValues("stylesids");
		productStyleService.setVisibleStatu(stylesids,true);
		request.setAttribute("message", "上架成功");
		request.setAttribute("urladdress", "/control/product/style/list?productid="+productid);
		return SUCCESS;
	}
	
	@Action("disable")
	public String disable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String productid = request.getParameter("productid");
		String[] stylesids = request.getParameterValues("stylesids");
		productStyleService.setVisibleStatu(stylesids,false);
		request.setAttribute("message", "下架成功");
		request.setAttribute("urladdress", "/control/product/style/list?productid="+productid);
		return SUCCESS;
	}
}
