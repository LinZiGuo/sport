package cn.itcast.web.action.order;

import java.io.Serializable;

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

import cn.itcast.bean.order.DeliverWay;
import cn.itcast.bean.order.Message;
import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.bean.order.OrderDeliverInfo;
import cn.itcast.bean.order.OrderItem;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.bean.user.Gender;
import cn.itcast.service.order.MessageService;
import cn.itcast.service.order.OrderContactInfoService;
import cn.itcast.service.order.OrderItemService;
import cn.itcast.service.order.OrderService;
import cn.itcast.service.order.OrderdeliverInfoService;
import cn.itcast.utils.WebUtil;
@Controller("/control/order/manage")
@ParentPackage("struts-default")
@Namespace("/control/order/manage")
@Results({
	@Result(name="modifyContactInfoUI",type="dispatcher",location="/WEB-INF/page/book/modifyContactInfo.jsp"),
	@Result(name="modifyPaymentWayUI",type="dispatcher",location="/WEB-INF/page/book/modifyPaymentWay.jsp"),
	@Result(name="modifyDeliverInfoUI",type="dispatcher",location="/WEB-INF/page/book/modifyDeliverInfo.jsp"),
	@Result(name="modifyDeliverWayUI",type="dispatcher",location="/WEB-INF/page/book/modifyDeliverWay.jsp"),
	@Result(name="modifyProductAmountUI",type="dispatcher",location="/WEB-INF/page/book/modifyProductAmount.jsp"),
	@Result(name="modifyDeliverFeeUI",type="dispatcher",location="/WEB-INF/page/book/modifydeliverFee.jsp"),
	@Result(name="print",type="dispatcher",location="/WEB-INF/page/book/print.jsp"),
	@Result(name="list",type="redirect",location="/control/order/list"),
	@Result(name="addMessageUI",type="dispatcher",location="/WEB-INF/page/book/ordermessage.jsp"),
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp")
})
public class OrderManageAction extends ActionSupport implements ModelDriven<Order> {
	private Order order = new Order();
	@Resource
	private OrderService orderService;
	@Resource
	private OrderContactInfoService contactInfoService;
	@Resource
	private OrderdeliverInfoService deliverInfoService;
	@Resource
	private OrderItemService itemService;
	@Resource
	private MessageService messageService;
	@Override
	public Order getModel() {
		return order;
	}
	
	/**
	 * 订购者信息修改界面
	 * @return
	 */
	@Action("modifyContactInfoUI")
	public String modifyContactInfoUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String contactid = request.getParameter("contactid");
		Integer id = (contactid==null?0:Integer.valueOf(contactid));
		OrderContactInfo contact = contactInfoService.find(id);
		request.setAttribute("buyer_name", contact.getBuyerName());
		request.setAttribute("buyer_gender", contact.getGender());
		request.setAttribute("buyer_address", contact.getAddress());
		request.setAttribute("buyer_postalcode", contact.getPostalcode());
		request.setAttribute("buyer_tel", contact.getTel());
		request.setAttribute("buyer_mobile", contact.getMobile());
		return "modifyContactInfoUI";
	}
	
	/**
	 * 订购者信息修改
	 * @return
	 */
	@Action("modifyContactInfo")
	public String modifyContactInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String contactid = request.getParameter("contactid");
		String buyer_name = request.getParameter("buyer_name");
		String buyer_gender = request.getParameter("buyer_gender");
		String buyer_address = request.getParameter("buyer_address");
		String buyer_postalcode = request.getParameter("buyer_postalcode");
		String buyer_tel = request.getParameter("buyer_tel");
		String buyer_mobile = request.getParameter("buyer_mobile");
		Integer id = (contactid==null?0:Integer.valueOf(contactid));
		OrderContactInfo contact = contactInfoService.find(id);
		contact.setBuyerName(buyer_name);
		contact.setGender(Gender.valueOf(buyer_gender));
		contact.setAddress(buyer_address);
		contact.setMobile(buyer_mobile);
		contact.setPostalcode(buyer_postalcode);
		contact.setTel(buyer_tel);
		contactInfoService.update(contact);
		request.setAttribute("message", "订购者信息修改成功");
		
		request.setAttribute("urladdress", "/control/order/view?orderid="+ contact.getOrder().getOrderid());
		return SUCCESS;
	}
	
	/**
	 * 支付修改界面
	 * @return
	 */
	@Action("modifyPaymentWayUI")
	public String modifyPaymentWayUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		Order order = orderService.find(orderid);
		request.setAttribute("deliverWay", order.getOrderDeliverInfo().getDeliverWay());
		request.setAttribute("paymentWay", order.getPaymentWay());
		return "modifyPaymentWayUI";
	}
	
	@Action("modifyPaymentWay")
	public String modifyPaymentWay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String paymentWay = request.getParameter("paymentWay");
		orderService.updatePaymentWay(orderid,PaymentWay.valueOf(paymentWay));
		request.setAttribute("message", "支付方式修改成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 配送信息修改界面
	 * @return
	 */
	@Action("modifyDeliverInfoUI")
	public String modifyDeliverInfoUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String deliverid = request.getParameter("deliverid");
		Integer id = (deliverid==null?0:Integer.valueOf(deliverid));
		OrderDeliverInfo deliverInfo = deliverInfoService.find(id);
		request.setAttribute("recipients", deliverInfo.getRecipients());
		request.setAttribute("gender", deliverInfo.getGender());
		request.setAttribute("address", deliverInfo.getAddress());
		request.setAttribute("postalcode", deliverInfo.getPostalcode());
		request.setAttribute("email", deliverInfo.getEmail());
		request.setAttribute("tel", deliverInfo.getTel());
		request.setAttribute("mobile", deliverInfo.getMobile());
		return "modifyDeliverInfoUI";
	}
	
	/**
	 * 修改配送信息
	 * @return
	 */
	@Action("modifyDeliverInfo")
	public String modifyDeliverInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String deliverid = request.getParameter("deliverid");
		String recipients = request.getParameter("recipients");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String postalcode = request.getParameter("postalcode");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String mobile = request.getParameter("mobile");
		Integer id = (deliverid==null?0:Integer.valueOf(deliverid));
		OrderDeliverInfo deliverInfo = deliverInfoService.find(id);
		deliverInfo.setRecipients(recipients);
		deliverInfo.setGender(Gender.valueOf(gender));
		deliverInfo.setAddress(address);
		deliverInfo.setPostalcode(postalcode);
		deliverInfo.setEmail(email);
		deliverInfo.setTel(tel);
		deliverInfo.setMobile(mobile);
		deliverInfoService.update(deliverInfo);
		request.setAttribute("message", "配送信息修改成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + deliverInfo.getOrder().getOrderid());
		return SUCCESS;
	}
	
	/**
	 * 配送信息修改界面
	 * @return
	 */
	@Action("modifyDeliverWayUI")
	public String modifyDeliverWayUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		Order order = orderService.find(orderid);
		request.setAttribute("deliverWay", order.getOrderDeliverInfo().getDeliverWay());
		request.setAttribute("paymentWay", order.getPaymentWay());
		return "modifyDeliverWayUI";
	}
	
	@Action("modifyDeliverWay")
	public String modifyDeliverWay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String deliverWay = request.getParameter("deliverWay");
		orderService.updateDeliverWay(orderid, DeliverWay.valueOf(deliverWay));
		request.setAttribute("message", "配送信息修改成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 商品购买数量修改界面
	 * @return
	 */
	@Action("modifyProductAmountUI")
	public String modifyProductAmountUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderitemid = request.getParameter("orderitemid");
		Integer id = (orderitemid==null?0:Integer.valueOf(orderitemid));
		OrderItem orderItem = itemService.find(id);
		request.setAttribute("amount", orderItem.getAmount());
		request.setAttribute("orderid", orderItem.getOrder().getOrderid());
		return "modifyProductAmountUI";
	}
	
	@Action("modifyProductAmount")
	public String modifyProductAmount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String orderitemid = request.getParameter("orderitemid");
		String a = request.getParameter("amount");
		Integer id = (orderitemid==null?0:Integer.valueOf(orderitemid));
		Integer amount = (a==null?0:Integer.valueOf(a));
		itemService.updateAmount(id,amount);
		request.setAttribute("message", "商品购买数量修改成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 商品购买数量修改界面
	 * @return
	 */
	@Action("modifyDeliverFeeUI")
	public String modifyDeliverFeeUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		Order order = orderService.find(orderid);
		request.setAttribute("fee", order.getDeliverFee());
		return "modifyDeliverFeeUI";
	}
	
	@Action("modifyDeliverFee")
	public String modifyDeliverFee() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String f = request.getParameter("fee");
		Float fee = (f==null?0:Float.valueOf(f));
		orderService.updateDeliverFee(orderid,fee);
		request.setAttribute("message", "配送费修改成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 删除订单项
	 * @return
	 */
	@Action("deleteOrderItem")
	public String deleteOrderItem() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String orderitemid = request.getParameter("orderitemid");
		Integer id = (orderitemid==null?0:Integer.valueOf(orderitemid));
		itemService.delete((Serializable)id);
		request.setAttribute("message", "订单项删除成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 取消订单
	 * @return
	 */
	@Action("cancelOrder")
	public String cancelOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.cannelOrder(orderid);
		request.setAttribute("message", "订单取消成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 审核通过订单
	 * @return
	 */
	@Action("confirmOrder")
	public String confirmOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.confirmOrder(orderid);
		request.setAttribute("message", "订单审核成功");
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		return SUCCESS;
	}
	
	/**
	 * 打印发货单
	 * @return
	 */
	@Action("printOrder")
	public String printOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		request.setAttribute("order", orderService.find(orderid));
		return "print";
	}
	
	/**
	 * 解锁订单
	 * @return
	 */
	@Action("employeeUnlockOrder")
	public String employeeUnlockOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.unlock(orderid);
		request.setAttribute("directUrl", "/control/order/list");
		return "list";
	}
	
	/**
	 * 财务确认已付款
	 * @return
	 */
	@Action("confirmPayment")
	public String confirmPayment() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.confirmPayment(orderid);
		request.setAttribute("message", "订单已设置为已支付");
		request.setAttribute("urladdress", "/control/order/list?state=WAITPAYMENT");
		return SUCCESS;
	}
	
	/**
	 * 把订单设置为等待发货状态
	 * @return
	 */
	@Action("turnWaitdeliver")
	public String turnWaitdeliver() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.turnWaitdeliver(orderid);
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", "/control/order/list?state=ADMEASUREPRODUCT");
		return SUCCESS;
	}
	
	/**
	 * 把订单设置为已发货状态
	 * @return
	 */
	@Action("turnDelivered")
	public String turnDelivered() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.turnDelivered(orderid);
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", "/control/order/list?state=WAITDELIVER");
		return SUCCESS;
	}
	
	/**
	 * 把订单设置为已收货状态
	 * @return
	 */
	@Action("turnReceived")
	public String turnReceived() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		orderService.turnReceived(orderid);
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", "/control/order/list?state=DELIVERED");
		return SUCCESS;
	}
	
	/**
	 * 客服留言添加界面
	 * @return
	 */
	@Action("addMessageUI")
	public String addMessageUI() {
		return "addMessageUI";
	}
	
	/**
	 * 添加客服留言
	 * @return
	 */
	@Action("addMessage")
	public String addMessage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String message = request.getParameter("message");
		Message msg = new Message();
		msg.setContent(message);
		msg.setUsername(WebUtil.getEmployee(request).getUsername());
		msg.setOrder(new Order(orderid));
		
		messageService.save(msg);

		request.setAttribute("message", "留言保存成功");		
		request.setAttribute("urladdress", "/control/order/view?orderid=" + orderid);
		
		return SUCCESS;
	}
	
	/**
	 * 强行解锁订单
	 * @return
	 */
	@Action("allUnLock")
	public String allUnLock() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid = request.getParameter("orderid");
		String[] orderids = orderid.split(",");
		orderService.unlock(orderids);
		request.setAttribute("message", "解锁成功");
		request.setAttribute("urladdress", "/control/lockorder/list");
		return SUCCESS;
	}
}
