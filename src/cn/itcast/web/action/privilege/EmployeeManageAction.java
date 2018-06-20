package cn.itcast.web.action.privilege;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import cn.itcast.bean.privilege.Employee;
import cn.itcast.bean.privilege.IDCard;
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.service.privilege.DepartmentService;
import cn.itcast.service.privilege.EmployeeService;
import cn.itcast.service.privilege.PrivilegeGroupService;
import cn.itcast.utils.UploadFile;
@Controller("/control/employee/manage")
@ParentPackage("struts-default")
@Namespace("/control/employee/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="query",type="dispatcher",location="/WEB-INF/page/department/query.jsp"),
	@Result(name="add",type="dispatcher",location="/WEB-INF/page/department/addemployee.jsp"),
	@Result(name="edit",type="dispatcher",location="/WEB-INF/page/department/editemployee.jsp"),
	@Result(name="checkResult",type="dispatcher",location="/WEB-INF/page/department/usernameIsExsit.jsp"),
	@Result(name="privilegeSet",type="dispatcher",location="/WEB-INF/page/department/privilegeSet.jsp")
})
public class EmployeeManageAction extends ActionSupport implements ModelDriven<Employee> {
	private Employee employee = new Employee();
	private List<Department> departments;
	private File picture;
	private String pictureContentType;
	private String pictureFileName;
	private String[] groupids;
	@Resource
	EmployeeService employeeService;
	@Resource
	DepartmentService departmentService;
	@Resource
	PrivilegeGroupService groupService;
	@Override
	public Employee getModel() {
		return employee;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public String getPictureContentType() {
		return pictureContentType;
	}
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public String[] getGroupids() {
		return groupids;
	}
	public void setGroupids(String[] groupids) {
		this.groupids = groupids;
	}
	/**
	 * 员工查询界面
	 * @return
	 */
	@Permission(module="employee", privilege="view")
	@Action("query")
	public String query() {
		HttpServletRequest request = ServletActionContext.getRequest();
		departments = departmentService.getScrollData().getResultlist();
		request.setAttribute("departments", departments);
		return "query";
	}
	
	/**
	 * 员工添加界面
	 * @return
	 */
	@Permission(module="employee", privilege="insert")
	@Action("regEmployeeUI")
	public String regEmployeeUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("departments", departmentService.getScrollData().getResultlist());
		return "add";
	}
	
	/**
	 * 添加员工
	 * @return
	 * @throws Exception 
	 */
	@Permission(module="employee", privilege="insert")
	@Action("regEmployee")
	public String regEmployee() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cardno = request.getParameter("cardno");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String departmentid = request.getParameter("departmentid");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(birthday);
		IDCard idCard = new IDCard(cardno, address, date);
		employee.setIdCard(idCard);
		//判断是否图片类型
		if (!UploadFile.validateImageFileType(pictureContentType, pictureFileName)) {
			request.setAttribute("message", "图片格式不正确");
			return SUCCESS;
		}
		if (picture.length()>204800) {
			request.setAttribute("message", "图片不能大于200K");
			return SUCCESS;
		}
		if (picture != null && picture.length() > 0) {
			//构建原始图片保存的目录
			String pathdir = "/images/employee/" + employee.getUsername();
			//得到原始图片保存目录的真实路径
			String realpathdir = request.getSession().getServletContext().getRealPath(pathdir);
			//构建文件名称
			String ext = UploadFile.getExt(pictureFileName);
			String imagename = UUID.randomUUID().toString() + "." + ext;
			UploadFile.saveFile(realpathdir, picture, imagename);
			employee.setImageName(imagename);
		}
		if(departmentid!=null && !"".equals(departmentid))
			employee.setDepartment(new Department(departmentid));
		
		employeeService.save(employee);
		request.setAttribute("message", "员工添加成功");
		request.setAttribute("message", "员工添加成功");		
		request.setAttribute("urladdress", "/control/employee/list");
		return SUCCESS;
	}
	
	/**
	 * 员工修改界面
	 * @return
	 */
	@Permission(module="employee", privilege="update")
	@Action("editEmployeeUI")
	public String editEmployeeUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		employee = employeeService.find(username);
		if(employee.getDepartment()!=null)
			request.setAttribute("selectdepartmentid", employee.getDepartment().getDepartmentid());
		request.setAttribute("imagePath", employee.getImagePath());
		request.setAttribute("departments", departmentService.getScrollData().getResultlist());
		request.setAttribute("employee", employee);
		return "edit";
	}
	/**
	 * 修改员工信息
	 * @return
	 * @throws Exception
	 */
	@Permission(module="employee", privilege="update")
	@Action("editEmployee")
	public String editEmployee() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String cardno = request.getParameter("cardno");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String departmentid = request.getParameter("departmentid");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(birthday);
		IDCard idCard = new IDCard(cardno, address, date);
		Employee newEmployee = employeeService.find(username);
		newEmployee.setIdCard(idCard);
		//判断是否图片类型
		if (!UploadFile.validateImageFileType(pictureContentType, pictureFileName)) {
			request.setAttribute("message", "图片格式不正确");
			return SUCCESS;
		}
		if (picture.length()>204800) {
			request.setAttribute("message", "图片不能大于200K");
			return SUCCESS;
		}
		if (picture != null && picture.length() > 0) {
			//构建原始图片保存的目录
			String pathdir = "/images/employee/" + employee.getUsername();
			//得到原始图片保存目录的真实路径
			String realpathdir = request.getSession().getServletContext().getRealPath(pathdir);
			//构建文件名称
			String ext = UploadFile.getExt(pictureFileName);
			String imagename = UUID.randomUUID().toString() + "." + ext;
			UploadFile.saveFile(realpathdir, picture, imagename);
			newEmployee.setImageName(imagename);
		}
		if(departmentid!=null && !"".equals(departmentid))
			newEmployee.setDepartment(new Department(departmentid));
		newEmployee.setDegree(employee.getDegree());
		newEmployee.setEmail(employee.getEmail());
		newEmployee.setGender(employee.getGender());
		newEmployee.setPhone(employee.getPhone());
		newEmployee.setRealname(employee.getRealname());
		newEmployee.setSchool(employee.getSchool());
		employeeService.update(newEmployee);
		request.setAttribute("message", "员工修改成功");
		request.setAttribute("urladdress", "/control/employee/list");
		return SUCCESS;
	}
	
	/**
	 * 判断员工的用户名是否存在
	 * @return
	 */
	@Action("exist")
	public String exist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		request.setAttribute("exist", employeeService.exist(username));
		return "checkResult";
	}
	
	/**
	 * 设置员工离职
	 * @return
	 */
	@Permission(module="employee", privilege="leave")
	@Action("leave")
	public String leave() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		employeeService.delete((Serializable)username);
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", "/control/employee/list");
		return SUCCESS;
	}
	
	/**
	 * 员工权限设置界面
	 * @return
	 */
	@Permission(module="employee", privilege="privilegeGroupSet")
	@Action("privilegeGroupSetUI")
	public String privilegeGroupSetUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		Employee employee = employeeService.find(username);
		request.setAttribute("usergroups", employee.getGroups());
		request.setAttribute("groups", groupService.getScrollData().getResultlist());
		return "privilegeSet";
	}
	
	/**
	 * 为员工设置权限
	 * @return
	 */
	@Permission(module="employee", privilege="privilegeGroupSet")
	@Action("privilegeGroupSet")
	public String privilegeGroupSet() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		Employee employee = employeeService.find(username);
		employee.getGroups().clear();
		for(String id : groupids){
			employee.getGroups().add(new PrivilegeGroup(id));
		}
		employeeService.update(employee);
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", "/control/employee/list");
		return SUCCESS;
	}
}
