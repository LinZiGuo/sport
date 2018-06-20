package cn.itcast.web.action.order;

import java.util.LinkedHashMap;

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
import cn.itcast.bean.order.Order;
import cn.itcast.service.order.OrderService;
@Controller("/contorl/lockorder")
@ParentPackage("struts-default")
@Namespace("/contorl/lockorder")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/book/orderlist.jsp")
})
public class LockOrderListAction extends ActionSupport implements ModelDriven<Order> {
	private Order order = new Order();
	@Resource
	private OrderService orderService;
	@Override
	public Order getModel() {
		return order;
	}
	
	/**
	 * 锁定订单列表
	 * @return
	 */
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		int currentPage = (page==null?1:Integer.valueOf(page));
		PageView<Order> pageView = new PageView<Order>(12 , currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "asc");
		pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(),
				"o.lockuser is not null", null, orderby));
		request.setAttribute("pageView", pageView);
		request.setAttribute("showButton", true);
		return "list";
	}
}
