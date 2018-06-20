package cn.itcast.web.action.product;

import java.util.LinkedHashMap;
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
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.product.ProductStyleService;
@Controller("/control/product/style/list")
@ParentPackage("struts-default")
@Namespace("/control/product/style")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/product/productstylelist.jsp")
})
public class ProductStyleAction extends ActionSupport implements ModelDriven<ProductStyle> {
	private ProductStyle productStyle = new ProductStyle();
	@Resource
	private ProductStyleService productStyleService;

	@Override
	public ProductStyle getModel() {
		return productStyle;
	}
	
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		Integer productid = Integer.valueOf(request.getParameter("productid"));
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<ProductStyle> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("id", "asc");
		orderby.put("visible", "desc");
		QueryResult<ProductStyle> qr;
		qr = productStyleService.getScrollData(firstResult, maxResult, "o.product.id=?1", new Object[] {productid}, orderby);
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
