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
import cn.itcast.bean.order.DeliverWay;
import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.bean.order.OrderDeliverInfo;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.bean.user.Gender;
import cn.itcast.service.order.OrderService;
import cn.itcast.utils.WebUtil;
@Controller("/customer/shopping/manage")
@ParentPackage("struts-default")
@Namespace("/customer/shopping/manage")
@Results({
	@Result(name="directUrl",type="dispatcher",location="/WEB-INF/page/share/directUrl.jsp")
})
public class ShoppingManageAction extends ActionSupport implements ModelDriven<OrderDeliverInfo> {
	private OrderDeliverInfo orderDeliverInfo = new OrderDeliverInfo();
	@Resource
	OrderService orderService;
	@Override
	public OrderDeliverInfo getModel() {
		return orderDeliverInfo;
	}
	
	/**
	 * 保存配送信息
	 */
	@Action("saveDeliverInfo")
	public String saveDeliverInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String gender = request.getParameter("gender");
		String recipients = request.getParameter("recipients");
		String address = request.getParameter("address");
		String postalcode = request.getParameter("postalcode");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String mobile = request.getParameter("mobile");
		String buyerIsrecipients = request.getParameter("buyerIsrecipients");
		String buyer = request.getParameter("buyer");
		String buyer_gender = request.getParameter("buyer_gender");
		String buyer_address = request.getParameter("buyer_address");
		String buyer_postalcode = request.getParameter("buyer_postalcode");
		String buyer_mobile = request.getParameter("buyer_mobile");
		String buyer_tel = request.getParameter("buyer_tel");
		String directUrl = request.getParameter("directUrl");
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart.getDeliverInfo()==null) cart.setDeliverInfo(new OrderDeliverInfo());
		cart.getDeliverInfo().setRecipients(recipients);
		cart.getDeliverInfo().setGender(Gender.valueOf(gender));
		cart.getDeliverInfo().setAddress(address);
		cart.getDeliverInfo().setPostalcode(postalcode);
		cart.getDeliverInfo().setEmail(email);
		cart.getDeliverInfo().setTel(tel);
		cart.getDeliverInfo().setMobile(mobile);
		
		cart.setBuyerIsrecipients(Boolean.valueOf(buyerIsrecipients));
		
		if(cart.getContactInfo()==null) cart.setContactInfo(new OrderContactInfo());
		if(cart.getBuyerIsrecipients()){//判断收货人与订购者是否相同
			cart.getContactInfo().setBuyerName(recipients);
			cart.getContactInfo().setGender(Gender.valueOf(gender));
			cart.getContactInfo().setAddress(address);
			cart.getContactInfo().setPostalcode(postalcode);
			cart.getContactInfo().setTel(tel);
			cart.getContactInfo().setMobile(mobile);
			cart.getContactInfo().setEmail(email);
		}else{
			cart.getContactInfo().setBuyerName(buyer);
			cart.getContactInfo().setGender(Gender.valueOf(buyer_gender));
			cart.getContactInfo().setAddress(buyer_address);
			cart.getContactInfo().setPostalcode(buyer_postalcode);
			cart.getContactInfo().setTel(buyer_tel);
			cart.getContactInfo().setMobile(buyer_mobile);
			cart.getContactInfo().setEmail(WebUtil.getBuyer(request).getEmail());
		}
		String url = "/customer/shopping/paymentway";
		if(directUrl!=null&&!directUrl.equals("")) {
			url = new String(Base64.decodeBase64(directUrl.trim().getBytes()));
		}
		request.setAttribute("directUrl", url);
		return "directUrl";
	}
	
	/**
	 * 保存用户选择的送货方式与支付方式
	 */
	@Action("savePaymentway")
	public String savePaymentway() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String deliverway = request.getParameter("deliverway");
		String paymentway = request.getParameter("paymentway");
		String requirement = request.getParameter("requirement");
		String delivernote = request.getParameter("delivernote");
		BuyCart cart = WebUtil.getBuyCart(request);
		cart.getDeliverInfo().setDeliverWay(DeliverWay.valueOf(deliverway));
		cart.setPaymentWay(PaymentWay.valueOf(paymentway));
		if(DeliverWay.EXPRESSDELIVERY.equals(DeliverWay.valueOf(deliverway)) || DeliverWay.EXIGENCEEXPRESSDELIVERY.equals(DeliverWay.valueOf(deliverway))){
			if("other".equals(requirement)){
				cart.getDeliverInfo().setRequirement(delivernote);
			}else{
				cart.getDeliverInfo().setRequirement(requirement);
			}
		}else{
			cart.getDeliverInfo().setRequirement(null);
		}
		request.setAttribute("directUrl", "/customer/shopping/confirm");
		return "directUrl";
	}
	
	/**
	 * 提交订单
	 * @return
	 */
	@Action("saveorder")
	public String saveorder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String note = request.getParameter("note");
		BuyCart cart = WebUtil.getBuyCart(request);
		cart.setNote(note);
		Order order = orderService.createOrder(cart, WebUtil.getBuyer(request).getUsername());
		WebUtil.deleteBuyCart(request);
		
		request.setAttribute("directUrl", "/shopping/finish?orderid="+ order.getOrderid()+"&paymentway="+ order.getPaymentWay()+
				"&payablefee="+ order.getPayablefee());
		return "directUrl";
	}
}
