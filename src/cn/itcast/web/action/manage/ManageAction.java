package cn.itcast.web.action.manage;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
@Controller("/control/center")
@ParentPackage("struts-default")
@Namespace("/control/center")
@Results({
	@Result(name="main",type="dispatcher",location="/WEB-INF/page/controlcenter/default.jsp"),
	@Result(name="top",type="dispatcher",location="/WEB-INF/page/controlcenter/top.jsp"),
	@Result(name="left",type="dispatcher",location="/WEB-INF/page/controlcenter/menu.jsp"),
	@Result(name="right",type="dispatcher",location="/WEB-INF/page/controlcenter/right.jsp"),
	@Result(name="end",type="dispatcher",location="/WEB-INF/page/controlcenter/end.jsp")
})
public class ManageAction extends ActionSupport {
	@Action("main")
	public String main() {
		return "main";
	}
	
	@Action("top")
	public String top() {
		return "top";
	}
	
	@Action("left")
	public String left() {
		return "left";
	}
	
	@Action("right")
	public String right() {
		return "right";
	}
	
	@Action("end")
	public String end() {
		return "end";
	}

}
