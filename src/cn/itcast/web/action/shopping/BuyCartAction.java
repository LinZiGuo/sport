package cn.itcast.web.action.shopping;

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

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.utils.WebUtil;
@Controller("/shopping")
@ParentPackage("struts-default")
@Namespace("/shopping")
@Results({
	@Result(name="cartUI",type="dispatcher",location="/WEB-INF/page/shopping/cart.jsp"),
	@Result(name="cart",type="redirect",location="cartUI"),
	@Result(name="postofficeremittance",type="dispatcher",location="/WEB-INF/page/shopping/finish_postofficeremittance.jsp"),
	@Result(name="cod",type="dispatcher",location="/WEB-INF/page/shopping/finish_cod.jsp"),
	@Result(name="net",type="dispatcher",location="/WEB-INF/page/shopping/finish_net.jsp"),
	@Result(name="bankremittance",type="dispatcher",location="/WEB-INF/page/shopping/finish_bankremittance.jsp")
})
public class BuyCartAction extends ActionSupport implements ModelDriven<BuyCart> {
	BuyCart buyCart = new BuyCart();
	
	@Resource
	ProductInfoService productInfoService;
	
	@Override
	public BuyCart getModel() {
		return buyCart;
	}

	@Action("cartUI")
	public String cartUI() {
		return "cartUI";
	}
	
	@Action("cart")
	public String cart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String productid = request.getParameter("productid");
		String styleid = request.getParameter("styleid");
		Integer pid = productid==null?0:Integer.valueOf(productid);
		Integer sid = styleid==null?0:Integer.valueOf(styleid);
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart == null ){
			cart = new BuyCart();
			request.getSession().setAttribute("buyCart", cart);
		}
		WebUtil.addCookie(response, "JSESSIONID", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
		if(pid!=null && pid>0){
			ProductInfo product = productInfoService.find(pid);
			if(product!=null){//下面处理产品的样式,只保留用户选择的样式
				ProductStyle currentStyle = null;
				for(ProductStyle style : product.getStyles()){
					if(style.getId().equals(sid)){
						currentStyle = style;
						break;
					}
				}
				product.getStyles().clear();
				product.addProductStyle(currentStyle);
			}
			BuyItem item = new BuyItem(product);//把商品添加进购物车
			cart.addBuyItem(item);
		}
		return "cart";
	}
	
	/**
	 * 订单提交结果界面
	 */
	@Action("finish")
	public String finish() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String paymentWay=request.getParameter("paymentway");
		String forwardName = "postofficeremittance";
		if(PaymentWay.COD.equals(PaymentWay.valueOf(paymentWay))){
			forwardName = "cod";
		}else if(PaymentWay.NET.equals(PaymentWay.valueOf(paymentWay))){
			forwardName = "net";
		}else if(PaymentWay.BANKREMITTANCE.equals(PaymentWay.valueOf(paymentWay))){
			forwardName = "bankremittance";
		}
		return forwardName;
	}
}
