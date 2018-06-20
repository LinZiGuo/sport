package cn.itcast.web.action.shopping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.utils.WebUtil;
@Controller("/shopping/cart/manage")
@ParentPackage("struts-default")
@Namespace("/shopping/cart/manage")
@Results({
	@Result(name="directUrl",type="dispatcher",location="/WEB-INF/page/share/directUrl.jsp")
})
public class BuyCartManageAction extends ActionSupport implements ModelDriven<BuyCart> {
	BuyCart buyCart = new BuyCart();
	
	@Resource
	ProductInfoService productInfoService;
	
	@Override
	public BuyCart getModel() {
		return buyCart;
	}
	
	/**
	 * 清空购物车
	 * @return
	 */
	@Action("deleteAll")
	public String deleteAll() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String directUrl = request.getParameter("directUrl");
		BuyCart cart = WebUtil.getBuyCart(request);
		cart.deleteAll();
		String param = directUrl!=null && !"".equals(directUrl) ? "?directUrl=" + directUrl : ""; 
		request.setAttribute("directUrl", "/shopping/cart"+ param);
		return "directUrl";
	}
	
	/**
	 * 删除购物项
	 * @return
	 */
	@Action("delete")
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String directUrl = request.getParameter("directUrl");
		String buyitemid = request.getParameter("buyitemid");
		String[] t = buyitemid.split("-");
		Integer pid = t[0]==null?0:Integer.valueOf(t[0]);
		Integer sid = t[1]==null?0:Integer.valueOf(t[1]);
		BuyCart cart = WebUtil.getBuyCart(request);
		ProductInfo product = new ProductInfo(pid);
		product.addProductStyle(new ProductStyle(sid));
		BuyItem item = new BuyItem(product);
		cart.deleteBuyItem(item);
		String param = directUrl!=null && !"".equals(directUrl) ? "?directUrl=" + directUrl : ""; 
		request.setAttribute("directUrl", "/shopping/cart"+ param);
		return "directUrl";
	}
	
	/**
	 * 更新数量
	 * @return
	 */
	@Action("updateAmount")
	public String updateAmount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String directUrl = request.getParameter("directUrl");
		setAmount(request);
		String param = directUrl!=null && !"".equals(directUrl) ? "?directUrl=" + directUrl : ""; 
		request.setAttribute("directUrl", "/shopping/cartUI"+ param);
		return "directUrl";
	}
	
	/**
	 * 设置数量
	 * @param request
	 */
	private void setAmount(HttpServletRequest request) {
		BuyCart cart = WebUtil.getBuyCart(request);
		for(BuyItem item : cart.getItems()){
			String paramName = "amount_"+ item.getProduct().getId()+" "+
								item.getProduct().getStyles().iterator().next().getId();
			Integer amount = new Integer(request.getParameter(paramName));
			item.setAmount(amount);
		}
	}
	
	/**
	 * 结算
	 * @return
	 */
	@Action("settleAccounts")
	public String settleAccounts() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String directUrl = request.getParameter("directUrl");
		String url = "/customer/shopping/deliver";
		if(directUrl!=null && !"".equals(directUrl)){
			url = new String(Base64.decodeBase64(directUrl.trim().getBytes()));//获取解码后的url
		}
		request.setAttribute("directUrl", url);
		return "directUrl";
	}
}
