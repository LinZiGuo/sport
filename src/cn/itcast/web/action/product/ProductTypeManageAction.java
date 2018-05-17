package cn.itcast.web.action.product;

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

import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductTypeService;
@Controller("/control/product/type/manage")
@ParentPackage("struts-default")
@Namespace("/control/product/type/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="queryUI",type="dispatcher",location="/WEB-INF/page/product/query_productType.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/product/add_productType.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/product/edit_productType.jsp")
})
public class ProductTypeManageAction extends ActionSupport implements ModelDriven<ProductType> {

	private ProductType productType = new ProductType();
	@Resource
	private ProductTypeService productTypeService;
	@Override
	public ProductType getModel() {
		return productType;
	}
	
	@Action("queryUI")
	public String queryUI() {
		return "queryUI";
	}
	
	/**
	 * 获取添加类别页面
	 * @return
	 */
	@Action("addUI")
	public String addUI() {
		return "addUI";
	}
	
	@Action("add")
	public String add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String parentid = request.getParameter("parentid");
		if (parentid!=null&&!parentid.equals("")) {
			Integer id = Integer.valueOf(parentid);
			if (id>0) {
				productType.setParent(productTypeService.find(id));
			}
		}
		productTypeService.save(productType);
		request.setAttribute("message", "添加类别成功");
		request.setAttribute("urladdress", "/control/product/type/list");
		return SUCCESS;
	}
	
	/**
	 * 获取修改类别页面
	 * @return
	 */
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		if (typeid!=null&&!typeid.equals("")) {
			Integer id = Integer.valueOf(typeid);
			if (id>0) {
				productType = productTypeService.find(id);
			}
		}
		request.setAttribute("type", productType);
		return "editUI";
	}
	
	/**
	 * 修改类别
	 * @return
	 */
	@Action("edit")
	public String edit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		ProductType newType = new ProductType();
		if (typeid!=null&&!typeid.equals("")) {
			Integer id = Integer.valueOf(typeid);
			if (id>0) {
				newType = productTypeService.find(id);
			}
		}
		newType.setName(productType.getName());
		newType.setNote(productType.getNote());
		productTypeService.update(newType);
		request.setAttribute("message", "修改类别成功");
		request.setAttribute("urladdress", "/control/product/type/list");
		return SUCCESS;
	}
}
