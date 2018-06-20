package cn.itcast.web.action.privilege;

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
import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.privilege.EmployeeService;
@Controller("/control/employee")
@ParentPackage("struts-default")
@Namespace("/control/employee")
@Results({
	@Result(name="list",type="dispatcher",location="/WEB-INF/page/department/employeelist.jsp")
})
public class EmployeeListAction extends ActionSupport implements ModelDriven<Employee> {
	private Employee employee = new Employee();
	@Resource
	EmployeeService employeeService;
	@Override
	public Employee getModel() {
		return employee;
	}
	
	@Permission(module="employee", privilege="view")
	@Action("list")
	public String list() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String query = request.getParameter("query");
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<Employee> pageView = new PageView<>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("realname", "desc");
		QueryResult<Employee> qr;
		if ("true".equals(query)) {
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<>();
			if (employee.getUsername()!=null&&!"".equals(employee.getUsername().trim())) {
				params.add("%"+employee.getUsername().trim()+"%");
				sb.append("o.username like ?").append(params.size());
			}
			if (employee.getRealname()!=null&&!"".equals(employee.getRealname().trim())) {
				params.add("%"+employee.getRealname().trim()+"%");
				sb.append("o.realname like ?").append(params.size());
			}
			if (employee.getDepartment().getDepartmentid()!=null&&!"".equals(employee.getDepartment().getDepartmentid().trim())) {
				params.add(employee.getDepartment().getDepartmentid().trim());
				sb.append("o.department.departmentid = ?").append(params.size());
			}
			 qr = employeeService.getScrollData(firstResult, maxResult, sb.toString(), params.toArray(), orderby);
			 request.setAttribute("query", true);
		} else {
			 qr = employeeService.getScrollData(firstResult, maxResult, orderby);
		}
		pageView.setQueryResult(qr);
		request.setAttribute("pageView", pageView);
		return "list";
	}
}
