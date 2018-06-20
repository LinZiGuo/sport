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

import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.utils.WebUtil;
@Controller("/product/switch")
@ParentPackage("struts-default")
@Namespace("/product/switch")
@Results({
	@Result(name="topsell",type="dispatcher",location="/WEB-INF/page/product/frontpage/topsell.jsp"),
	@Result(name="getViewHistory",type="dispatcher",location="/WEB-INF/page/product/frontpage/viewHistory.jsp")
})
public class ProductSwitchAction extends ActionSupport implements ModelDriven<ProductInfo> {
	ProductInfo product = new ProductInfo();
	@Override
	public ProductInfo getModel() {
		return product;
	}
	@Resource
	private ProductInfoService productInfoService;
	
	@Action("topsell")
	public String topsell() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		Integer id = typeid==null?0:Integer.valueOf(typeid);
		request.setAttribute("topsellproducts", productInfoService.getTopSell(id, 10));
		return "topsell";
	}
	
	@Action("getViewHistory")
	public String getViewHistory() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		if(cookieValue!=null && !"".equals(cookieValue.trim())){			
			String[] ids = cookieValue.split("-");
			Integer[] productids = new Integer[ids.length];
			for(int i=0 ;i<ids.length; i++){
				productids[i]=new Integer(ids[i].trim());
			}
			request.setAttribute("viewHistory", productInfoService.getViewHistory(productids, 10));
		}
		return "getViewHistory";
	}
}
