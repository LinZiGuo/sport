package cn.itcast.web.action.order;

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
import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderState;
import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.order.OrderService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.utils.WebUtil;
@Controller("/control/order")
@ParentPackage("struts-default")
@Namespace("/control/order")
@Results({
	@Result(name="query",type="dispatcher",location="/WEB-INF/page/book/queryorder.jsp"),
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/book/orderlist.jsp"),
	@Result(name="order",type="dispatcher",location="/WEB-INF/page/book/orderview.jsp"),
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp")
})
public class OrderListAction extends ActionSupport implements ModelDriven<Order> {
	private Order order = new Order();
	@Resource
	private OrderService orderService;
	@Override
	public Order getModel() {
		return order;
	}
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String query = request.getParameter("query");
		String orderid = request.getParameter("orderid");
		String orderState = request.getParameter("state");
		String username = request.getParameter("username");
		String recipients = request.getParameter("recipients");
		String buyer = request.getParameter("buyer");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		OrderState state = (orderState!=null&&!orderState.equals("")?OrderState.valueOf(orderState):OrderState.WAITCONFIRM);
		PageView<Order> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("createDate", "asc");
		QueryResult<Order> qr;
		if ("true".equals(query)) {
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<>();
			if (orderid!=null&&!"".equals(orderid.trim())) {
				params.add("%"+orderid.trim()+"%");
				sb.append("o.orderid like ?").append(params.size());
			}
			if (state!=null) {
				params.add(state);
				sb.append("o.state = ?").append(params.size());
			}
			if (username!=null&&!"".equals(username.trim())) {
				params.add("%"+username.trim()+"%");
				sb.append("o.buyer.username like ?").append(params.size());
			}
			if (recipients!=null&&!"".equals(recipients.trim())) {
				params.add("%"+recipients.trim()+"%");
				sb.append("o.orderDeliverInfo.recipients like ?").append(params.size());
			}
			if (buyer!=null&&!"".equals(buyer.trim())) {
				params.add("%"+buyer.trim()+"%");
				sb.append("o.orderContactInfo.buyerName like ?").append(params.size());
			}
			 qr = orderService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
			 request.setAttribute("query", true);
			 request.setAttribute("orderid", orderid);
			 request.setAttribute("state", state);
			 request.setAttribute("username", username);
			 request.setAttribute("recipients", recipients);
			 request.setAttribute("buyer", buyer);
		} else {
			 qr = orderService.getScrollData(firstResult, maxResult, "o.state=?1", new Object[]{state}, orderby);
		}
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
	
	/**
	 * 订单信息查看
	 */
	@Action("view")
	public String view() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String orderid=request.getParameter("orderid");
		Employee employee = WebUtil.getEmployee(request);
		String username = employee.getUsername();
		Order order = orderService.addLock(orderid, username);
		if(order.getLockuser()!=null && !order.getLockuser().equals(username)){
			request.setAttribute("message", "订单已经被"+ order.getLockuser() + "加锁");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
			return SUCCESS;
		}
		request.setAttribute("order", order);
		return "order";
	}
	
	@Action("query")
	public String query() {
		return "query";
	}
}
