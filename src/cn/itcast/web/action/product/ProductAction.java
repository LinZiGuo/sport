package cn.itcast.web.action.product;

import java.io.Serializable;
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
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.BrandService;
import cn.itcast.service.product.ProductInfoService;
@Controller("/control/product/list")
@ParentPackage("struts-default")
@Namespace("/control/product")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/product/productlist.jsp")
})
public class ProductAction extends ActionSupport implements ModelDriven<ProductInfo> {
	private ProductInfo productInfo = new ProductInfo();
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private BrandService brandService;
	
	@Override
	public ProductInfo getModel() {
		return productInfo;
	}

	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String query = request.getParameter("query");
		String name = request.getParameter("name");
		String typeid = request.getParameter("typeid");
		String startsellprice = request.getParameter("startsellprice");
		String endsellprice = request.getParameter("endsellprice");
		String startbaseprice = request.getParameter("startbaseprice");
		String endbaseprice = request.getParameter("endbaseprice");
		String code = request.getParameter("code");
		String brandid = request.getParameter("brandid");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<ProductInfo> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("id", "desc");
		QueryResult<ProductInfo> qr;
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if ("true".equals(query)) {
			if (name!=null&&!"".equals(name.trim())) {
				params.add("%"+name.trim()+"%");
				sb.append("o.name like ?").append(params.size());
			}
			if (typeid!=null&&!"".equals(typeid.trim())) {
				params.add(Integer.valueOf(typeid));
				sb.append("o.type.typeid = ?").append(params.size());
			}
			if (startsellprice!=null&&!"".equals(startsellprice.trim()) &&
					endsellprice!=null&&!"".equals(endsellprice.trim())) {
				params.add(Float.valueOf(startsellprice.trim()));
				sb.append("o.sellprice between ?").append(params.size());
				params.add(Float.valueOf(endsellprice.trim()));
				sb.append(" and ?").append(params.size());
			}
			if (startbaseprice!=null&&!"".equals(startbaseprice.trim()) &&
					endbaseprice!=null&&!"".equals(endbaseprice.trim())) {
				params.add(Float.valueOf(startbaseprice.trim()));
				sb.append("o.baseprice between ?").append(params.size());
				params.add(Float.valueOf(endbaseprice.trim()));
				sb.append(" and ?").append(params.size());
			}
			if (code!=null&&!"".equals(code.trim())) {
				params.add("%"+code.trim()+"%");
				sb.append("o.code like ?").append(params.size());
			}
			if (brandid!=null&&!"".equals(brandid.trim())) {
				params.add(brandService.find((Serializable)brandid));
				sb.append("o.brand = ?").append(params.size());
			}
			request.setAttribute("query", "true");
			request.setAttribute("name", name);
			request.setAttribute("typeid", typeid);
			request.setAttribute("startsellprice", startsellprice);
			request.setAttribute("endsellprice", endsellprice);
			request.setAttribute("startbaseprice", startbaseprice);
			request.setAttribute("endbaseprice", endbaseprice);
			request.setAttribute("code", code);
			request.setAttribute("brandid", brandid);
		} else {
			System.out.println("111");
		}
		qr = productInfoService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
