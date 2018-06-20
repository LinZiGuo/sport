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
import cn.itcast.bean.privilege.Department;
import cn.itcast.service.privilege.DepartmentService;
@Controller("/control/department")
@ParentPackage("struts-default")
@Namespace("/control/department")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/department/departmentlist.jsp")
})
public class DepartmentListAction extends ActionSupport implements ModelDriven<Department> {
	private Department department = new Department();
	@Resource
	private DepartmentService departmentService;
	@Override
	public Department getModel() {
		return department;
	}
	@Permission(module="department", privilege="view")
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<Department> pageView = new PageView<>(maxResult, currentPage);
		QueryResult<Department> qr;
		qr = departmentService.getScrollData(firstResult, maxResult);
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
