package cn.itcast.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductTypeService;
@Controller("/control/product/type/list")
@ParentPackage("struts-default")
@Namespace("/control/product/type")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/product/producttypelist.jsp")
})
public class ProductTypeAction extends ActionSupport implements ModelDriven<ProductType> {
	private ProductType productType = new ProductType();
	@Resource
	private ProductTypeService productTypeService;
	@Override
	public ProductType getModel() {
		return productType;
	}
	
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String query = request.getParameter("query");
		String name = request.getParameter("name");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<ProductType> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("typeid", "desc");
		QueryResult<ProductType> qr;
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if ("true".equals(query)) {
			if (name!=null&&!"".equals(name.trim())) {
				params.add("%"+name.trim()+"%");
				sb.append("o.name like ?").append(params.size());
			}
			request.setAttribute("name", name);
			request.setAttribute("query", "true");
		} else {
			System.out.println("111");
		}
		qr = productTypeService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
