package cn.itcast.web.action.user;

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

import cn.itcast.bean.user.Buyer;
import cn.itcast.service.user.BuyerService;
@Controller("/control/user/manage")
@ParentPackage("struts-default")
@Namespace("/control/user/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp")
})
public class BuyerManageAction extends ActionSupport implements ModelDriven<Buyer> {
	private Buyer buyer = new Buyer();
	@Resource
	BuyerService buyerService;
	
	@Override
	public Buyer getModel() {
		return buyer;
	}

	@Action("delete")
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] usernames = request.getParameterValues("usernames");
		buyerService.delete((Serializable[])usernames);
		request.setAttribute("message", "账号禁用成功");
		request.setAttribute("urladdress", "/control/user/list");
		return SUCCESS;
	}
	
	@Action("enable")
	public String enable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] usernames = request.getParameterValues("usernames");
		buyerService.enable((Serializable[])usernames);
		request.setAttribute("message", "账号启用成功");
		request.setAttribute("urladdress", "/control/user/list");
		return SUCCESS;
	}
}
