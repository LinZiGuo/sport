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

import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.privilege.EmployeeService;
@Controller("/employee")
@ParentPackage("struts-default")
@Namespace("/employee")
@Results({
	@Result(name="main",type="redirect",location="/control/center/main"),
	@Result(name="logon",type="dispatcher",location="/WEB-INF/page/department/logon.jsp")
})
public class EmployeeAction extends ActionSupport implements ModelDriven<Employee> {
	private Employee employee = new Employee();

	@Resource
	EmployeeService employeeService;
	@Override
	public Employee getModel() {
		return employee;
	}
	
	/**
	 * 员工登录
	 * @return
	 */
	@Action("logon")
	public String logon() {
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean flag = employeeService.validate(employee.getUsername().trim(), employee.getPassword().trim());
		if(employee.getUsername()!=null && !"".equals(employee.getUsername().trim())
				&& employee.getPassword()!=null && !"".equals(employee.getPassword().trim())){
			if(employeeService.validate(employee.getUsername().trim(), employee.getPassword().trim())){
				request.getSession().setAttribute("employee", employeeService.find(employee.getUsername().trim()));
				return "main";
			}else{
				request.setAttribute("message", "用户名或密码有误");
			}
		}
		return "logon";
	}//1>显示登录界面(当用户没有提供用户名及密码的时候) 2>实现用户名及密码的校验
	
	/**
	 * 员工退出登录
	 * @return
	 */
	@Action("logout")
	public String logout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute("employee");
		return "logon";
	}
}
