package cn.itcast.web.action.shopping;

import java.util.Arrays;
import java.util.List;

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
import cn.itcast.bean.order.DeliverWay;
import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.bean.order.OrderDeliverInfo;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.bean.user.Buyer;
import cn.itcast.bean.user.Gender;
import cn.itcast.utils.WebUtil;
@Controller("/customer/shopping")
@ParentPackage("struts-default")
@Namespace("/customer/shopping")
@Results({
	@Result(name="directUrl",type="dispatcher",location="/WEB-INF/page/share/directUrl.jsp"),
	@Result(name="deliver",type="dispatcher",location="/WEB-INF/page/shopping/deliverInfo.jsp"),
	@Result(name="paymentway",type="dispatcher",location="/WEB-INF/page/shopping/paymentway.jsp"),
	@Result(name="confirm",type="dispatcher",location="/WEB-INF/page/shopping/confirm.jsp")
})
public class DeliverInfoAction extends ActionSupport implements ModelDriven<OrderDeliverInfo> {
	private OrderDeliverInfo orderDeliverInfo = new OrderDeliverInfo();
	private Order order = new Order();
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public OrderDeliverInfo getModel() {
		return orderDeliverInfo;
	}
	
	/**
	 * 显示配送信息填写界面
	 */
	@Action("deliver")
	public String deliver() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String recipients="";
		Gender gender=Gender.MAN;
		String address="";
		String email=WebUtil.getBuyer(request).getEmail();
		String postalcode="";
		String tel="";
		String mobile="";
		Boolean buyerIsrecipients=true;
		String buyer="";
		Gender buyer_gender=Gender.MAN;
		String buyer_address="";
		String buyer_postalcode="";
		String buyer_mobile="";
		String buyer_tel="";
		
		BuyCart cart = WebUtil.getBuyCart(request);
		Buyer b = WebUtil.getBuyer(request);
		if(cart.getDeliverInfo()!=null){
			recipients=cart.getDeliverInfo().getRecipients();
			gender=cart.getDeliverInfo().getGender();
			address=cart.getDeliverInfo().getAddress();
			postalcode=cart.getDeliverInfo().getPostalcode();
			tel=cart.getDeliverInfo().getTel();
			mobile=cart.getDeliverInfo().getMobile();
			email=cart.getDeliverInfo().getEmail();
		}else{
			if(b.getContactInfo()!=null){
				recipients=b.getRealname();
				gender=b.getGender();
				address=b.getContactInfo().getAddress();
				postalcode=b.getContactInfo().getPostalcode();
				tel=b.getContactInfo().getPhone();
				mobile=b.getContactInfo().getMobile();
			}
		}
		if(cart.getBuyerIsrecipients()!=null) buyerIsrecipients=cart.getBuyerIsrecipients();
		if(cart.getContactInfo()!=null){
			buyer=cart.getContactInfo().getBuyerName();
			buyer_gender=cart.getContactInfo().getGender();
			buyer_address=cart.getContactInfo().getAddress();
			buyer_postalcode=cart.getContactInfo().getPostalcode();
			buyer_tel=cart.getContactInfo().getTel();
			buyer_mobile=cart.getContactInfo().getMobile();
		}else{
			if(b.getContactInfo()!=null){
				buyer=b.getRealname();
				buyer_gender=b.getGender();
				buyer_address=b.getContactInfo().getAddress();
				buyer_postalcode=b.getContactInfo().getPostalcode();
				buyer_tel=b.getContactInfo().getPhone();
				buyer_mobile=b.getContactInfo().getMobile();
			}
		}
		request.setAttribute("recipients", recipients);
		request.setAttribute("gender", gender);
		request.setAttribute("address", address);
		request.setAttribute("email", email);
		request.setAttribute("postalcode", postalcode);
		request.setAttribute("tel", tel);
		request.setAttribute("mobile", mobile);
		request.setAttribute("buyerIsrecipients", buyerIsrecipients);
		request.setAttribute("buyer", buyer);
		request.setAttribute("buyer_gender", buyer_gender);
		request.setAttribute("buyer_address", buyer_address);
		request.setAttribute("buyer_postalcode", buyer_postalcode);
		request.setAttribute("buyer_mobile", buyer_mobile);
		request.setAttribute("buyer_tel", buyer_tel);
		
		return "deliver";
	}
	
	/**
	 * 送货方式与支付方式显示界面
	 */
	@Action("paymentway")
	public String paymentway() {
		HttpServletRequest request = ServletActionContext.getRequest();
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart.getDeliverInfo()==null){
			request.setAttribute("directUrl", "/customer/shopping/deliver");
			return "directUrl";
		}
		DeliverWay deliverWay=DeliverWay.EXPRESSDELIVERY;
		PaymentWay paymentWay=PaymentWay.NET;
		String requirement="";
		
		if(cart.getPaymentWay()!=null){
			paymentWay=cart.getPaymentWay();
		}
		if(cart.getDeliverInfo().getDeliverWay()!=null){
			deliverWay=cart.getDeliverInfo().getDeliverWay();
		}
		if(cart.getDeliverInfo().getRequirement()!=null){//为了实现时间要求数据回显
			List<String> contents = Arrays.asList("工作日、双休日与假日均可送货","只双休日、假日送货",
					"只工作日送货(双休日、假日不用送)","学校地址/地址白天没人，请尽量安排其他时间送货");
			if(contents.contains(cart.getDeliverInfo().getRequirement())){
				requirement=cart.getDeliverInfo().getRequirement();
			}else{
				requirement="other";
				String note=cart.getDeliverInfo().getRequirement();
				request.setAttribute("note", note);
			}
		}
		request.setAttribute("deliverway", deliverWay);
		request.setAttribute("paymentway", paymentWay);
		request.setAttribute("requirement", requirement);
		return "paymentway";
	}
	
	@Action("confirm")
	public String confirm() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = "/customer/shopping/confirm";
		request.setAttribute("directUrl", new String(Base64.encodeBase64(url.getBytes())));
		return "confirm";
	}
}
