package cn.itcast.web.action.user;

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
import cn.itcast.bean.user.Buyer;
import cn.itcast.service.user.BuyerService;
@Controller("/control/user/list")
@ParentPackage("struts-default")
@Namespace("/control/user")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/user/userlist.jsp"),
	@Result(name="query",type="dispatcher",location="/WEB-INF/page/user/query.jsp")
})
public class BuyerListAction extends ActionSupport implements ModelDriven<Buyer> {
	private Buyer buyer = new Buyer();
	@Resource
	BuyerService buyerService;
	
	@Override
	public Buyer getModel() {
		return buyer;
	}

	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String query = request.getParameter("query");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<Buyer> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("regTime", "desc");
		QueryResult<Buyer> qr;
		if ("true".equals(query)) {
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<>();
			if (buyer.getUsername()!=null&&!"".equals(buyer.getUsername().trim())) {
				params.add("%"+buyer.getUsername().trim()+"%");
				sb.append("o.username like ?").append(params.size());
			}
			if (buyer.getRealname()!=null&&!"".equals(buyer.getRealname().trim())) {
				params.add("%"+buyer.getRealname().trim()+"%");
				sb.append("o.realname like ?").append(params.size());
			}
			if (buyer.getEmail()!=null&&!"".equals(buyer.getEmail().trim())) {
				params.add("%"+buyer.getEmail().trim()+"%");
				sb.append("o.email like ?").append(params.size());
			}
			 qr = buyerService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
		} else {
			 qr = buyerService.getScrollData(firstResult, maxResult, orderby);
		}
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return SUCCESS;
	}
	
	@Action("queryUI")
	public String queryUI() {
		return "query";
	}
}
