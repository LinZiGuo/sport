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

import cn.itcast.bean.privilege.Department;
import cn.itcast.service.privilege.DepartmentService;
@Controller("/control/department/manage")
@ParentPackage("struts-default")
@Namespace("/control/department/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/department/adddepartment.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/department/editdepartment.jsp")	
})
public class DepartmentManageAction extends ActionSupport implements ModelDriven<Department> {

	private Department department = new Department();
	@Resource
	private DepartmentService departmentService;

	@Override
	public Department getModel() {
		return department;
	}
	
	/**
	 * 部门添加界面
	 * @return
	 */
	@Permission(module="department", privilege="insert")
	@Action("addUI")
	public String addUI() {
		return "addUI";
	}
	
	/**
	 * 添加商品
	 * @return
	 * @throws Exception 
	 */
	@Permission(module="department", privilege="insert")
	@Action("add")
	public String add() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		departmentService.save(department);
		request.setAttribute("message", "部门添加成功");
		request.setAttribute("urladdress", "/control/department/list");
		return SUCCESS;
	}
	
	/**
	 * 部门修改界面
	 * @return
	 */
	@Permission(module="department", privilege="update")
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String departmentid = request.getParameter("departmentid");
		department=departmentService.find(departmentid);
		request.setAttribute("name", department.getName());
		return "editUI";
	}
	
	/**
	 * 修改部门信息
	 * @return
	 */
	@Permission(module="department", privilege="update")
	@Action("edit")
	public String edit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String departmentid = request.getParameter("departmentid");
		Department d = departmentService.find(departmentid);
		d.setName(department.getName());
		departmentService.update(d);
		request.setAttribute("message", "部门修改成功");
		request.setAttribute("urladdress", "/control/department/list");
		return SUCCESS;
	}
	
	/**
	 * 删除部门
	 * @return
	 */
	@Permission(module="department", privilege="delete")
	@Action("delete")
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String departmentid = request.getParameter("departmentid");
		departmentService.delete(departmentid);
		request.setAttribute("message", "部门删除成功");
		request.setAttribute("urladdress", "/control/department/list");
		return SUCCESS;
	}
}
