package cn.itcast.web.action.product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.utils.WebUtil;
@Controller("/product")
@ParentPackage("struts-default")
@Namespace("/product")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="view",type="dispatcher",location="/WEB-INF/page/product/frontpage/productview.jsp")
})
public class ViewProductAction extends ActionSupport implements ModelDriven<ProductInfo> {
	private ProductInfo productInfo = new ProductInfo();
	@Resource
	private ProductInfoService productInfoService;
	
	@Override
	public ProductInfo getModel() {
		return productInfo;
	}
	
	@Action("view")
	public String view() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String productid = request.getParameter("productid");
		Integer id = productid==null?0:Integer.valueOf(productid);
		ProductInfo product = productInfoService.find(id);
		if(product==null){
			request.setAttribute("message", "获取不到你需要浏览的产品");
			request.setAttribute("urladdress", "/");
			return SUCCESS;
		}
		WebUtil.addCookie(response, "productViewHistory", 
				buildViewHistory(request, id), 30*24*60*60);
		List<ProductType> stypes = new ArrayList<ProductType>();
		ProductType parent = product.getType();
		while(parent!=null){
			stypes.add(parent);
			parent = parent.getParent();
		}
		request.setAttribute("product", product);
		request.setAttribute("stypes", stypes);
		return "view";
	}

	public String buildViewHistory(HttpServletRequest request, Integer currentProductId) {
		//23-2-6-5
		//1.如果当前浏览的id已经在浏览历史里了,我们要把移到最前面
		//2.如果浏览历史里已经达到了10个产品了,我们需要把最选进入的元素删除
		String cookieValue = WebUtil.getCookieByName(request, "productViewHistory");
		LinkedList<Integer> productids = new LinkedList<Integer>();
		if(cookieValue!=null && !"".equals(cookieValue.trim())){
			String[] ids = cookieValue.split("-");			
			for(String id : ids){
				productids.offer(new Integer(id.trim()));
			}
			if(productids.contains(currentProductId)) productids.remove(currentProductId);
			if(productids.size()>=10) productids.poll();
		}
		productids.offer(currentProductId);
		StringBuffer out = new StringBuffer();
		for(Integer id : productids){
			out.append(id).append('-');
		}
		out.deleteCharAt(out.length()-1);
		return out.toString();
	}
}
