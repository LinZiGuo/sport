package cn.itcast.web.action.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bean.user.Buyer;
import cn.itcast.service.user.BuyerService;
@Controller("/user/reg")
@ParentPackage("struts-default")
@Namespace("/user")
@Results({
	@Result(name="regUI",type="dispatcher",location="/WEB-INF/page/user/userReg.jsp"),
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="checkResult",type="dispatcher",location="/WEB-INF/page/user/checkuser.jsp")
})
public class BuyerRegAction extends ActionSupport implements ModelDriven<Buyer> {
	private Buyer buyer = new Buyer();
	@Resource
	BuyerService buyerService;
	
	@Override
	public Buyer getModel() {
		return buyer;
	}

	/**
	 * 获取用户注册页面
	 * @return
	 */
	@Action("regUI")
	public String regUI() {
		return "regUI";
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@Action("reg")
	public String reg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (buyerService.exsit(buyer.getUsername().trim())) {
			request.setAttribute("error", "该用户名已存在");
			return "regUI";
		} else {
			Buyer b = new Buyer();
			b.setUsername(buyer.getUsername().trim());
			b.setPassword(buyer.getPassword().trim());
			b.setEmail(buyer.getEmail().trim());
			buyerService.save(b);
			request.setAttribute("message", "用户注册成功");
			request.setAttribute("urladdress", "/");
			return SUCCESS;
		}
	}
	
	/**
	 * 校验用户名是否存在
	 * @return
	 */
	@Action("isUserExist")
	public String isUserExist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("exist", buyerService.exsit(buyer.getUsername().trim()));
		return "checkResult";
	}

}
