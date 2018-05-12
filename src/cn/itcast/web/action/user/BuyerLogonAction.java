package cn.itcast.web.action.user;

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
import cn.itcast.utils.MD5;

@Controller("/user/logon")
@ParentPackage("struts-default")
@Namespace("/user")
@Results({
	@Result(name="logon",type="dispatcher",location="/WEB-INF/page/user/logon.jsp"),
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp")
	
})
public class BuyerLogonAction extends ActionSupport implements ModelDriven<Buyer> {

	private Buyer buyer = new Buyer();
	@Resource
	BuyerService buyerService;
	
	@Override
	public Buyer getModel() {
		return buyer;
	}
	
	@Action("logon")
	public String logon() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (buyer.getUsername() != null && !"".equals(buyer.getUsername().trim())
				&& buyer.getPassword() != null && !"".equals(buyer.getPassword().trim())) {
			Buyer b = buyerService.find(buyer.getUsername().trim());
			if (buyerService.validate(buyer.getUsername().trim(), buyer.getPassword().trim())) {
				//校验通过
				request.getSession().setAttribute("user", buyerService.find(buyer.getUsername().trim()));
				request.setAttribute("message", "用户登录成功");
				request.setAttribute("urladdress", "/");
				return SUCCESS;
			} else {
				request.setAttribute("error", "用户名及密码有误");
			}
		}
		return "logon";
	}

}
