package cn.itcast.web.action.privilege;

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
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.service.privilege.PrivilegeGroupService;
@Controller("/control/privilegegroup")
@ParentPackage("struts-default")
@Namespace("/control/privilegegroup")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/department/privilegegrouplist.jsp")
})
public class PrivilegeGroupListAction extends ActionSupport implements ModelDriven<PrivilegeGroup> {
	private PrivilegeGroup privilegeGroup = new PrivilegeGroup();
	@Resource
	PrivilegeGroupService groupService;
	@Override
	public PrivilegeGroup getModel() {
		return privilegeGroup;
	}

	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<PrivilegeGroup> pageView = new PageView<>(maxResult, currentPage);
		QueryResult<PrivilegeGroup> qr;
		qr = groupService.getScrollData(firstResult, maxResult);
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
