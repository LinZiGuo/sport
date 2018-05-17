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
import cn.itcast.bean.product.Brand;
import cn.itcast.service.product.BrandService;
@Controller("/control/brand/list")
@ParentPackage("struts-default")
@Namespace("/control/brand")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/product/brandlist.jsp")
})
public class BrandAction extends ActionSupport implements ModelDriven<Brand> {
	private Brand brand = new Brand();
	@Resource
	private BrandService brandService;
	@Override
	public Brand getModel() {
		return brand;
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
		PageView<Brand> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("code", "asc");
		QueryResult<Brand> qr;
		if ("true".equals(query)) {
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<>();
			if (name!=null&&!"".equals(name.trim())) {
				params.add("%"+name.trim()+"%");
				sb.append("o.name like ?").append(params.size());
			}
			 qr = brandService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
			 request.setAttribute("query", true);
			 request.setAttribute("name", brand.getName());
		} else {
			 qr = brandService.getScrollData(firstResult, maxResult, orderby);
		}
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}

}
