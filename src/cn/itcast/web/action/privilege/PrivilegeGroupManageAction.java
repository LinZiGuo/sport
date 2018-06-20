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
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.bean.privilege.SystemPrivilege;
import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.service.privilege.PrivilegeGroupService;
import cn.itcast.service.privilege.SystemPrivilegeService;
@Controller("/control/privilegegroup/manage")
@ParentPackage("struts-default")
@Namespace("/control/privilegegroup/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/department/addprivilegegroup.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/department/editprivilegegroup.jsp")	
})
public class PrivilegeGroupManageAction extends ActionSupport implements ModelDriven<PrivilegeGroup> {
	private PrivilegeGroup privilegeGroup = new PrivilegeGroup();
	private String[] ps;
	@Resource
	PrivilegeGroupService groupService;
	@Resource
	SystemPrivilegeService privilegeService;
	@Override
	public PrivilegeGroup getModel() {
		return privilegeGroup;
	}

	public String[] getPs() {
		return ps;
	}

	public void setPs(String[] ps) {
		this.ps = ps;
	}

	/**
	 * 权限组添加界面
	 * @return
	 */
	@Action("addUI")
	public String addUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		return "addUI";
	}
	
	/**
	 * 添加权限组
	 * @return
	 * @throws Exception 
	 */
	@Action("add")
	public String add() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (ps!=null && ps.length>0) {
			for (String privilege : ps) {
				String[] ids = privilege.split(",");
				SystemPrivilegePK SPK = new SystemPrivilegePK(ids[0], ids[1]);
				privilegeGroup.addSystemPrivilege(new SystemPrivilege(SPK));
			}
		}
		groupService.save(privilegeGroup);
		request.setAttribute("message", "权限组添加成功");
		request.setAttribute("urladdress", "/control/privilegegroup/list");
		return SUCCESS;
	}
	
	/**
	 * 权限组修改界面
	 * @return
	 */
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String groupid = request.getParameter("groupid");
		privilegeGroup = groupService.find(groupid);
		request.setAttribute("name",privilegeGroup.getName());
		request.setAttribute("selectprivileges",privilegeGroup.getPrivileges());
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		return "editUI";
	}
	
	/**
	 * 修改权限组
	 * @return
	 */
	@Action("edit")
	public String edit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String groupid = request.getParameter("groupid");
		PrivilegeGroup newGroup = groupService.find(groupid);
		newGroup.setName(privilegeGroup.getName());
		newGroup.getPrivileges().clear();
		if (ps!=null && ps.length>0) {
			for (String privilege : ps) {
				String[] ids = privilege.split(",");
				SystemPrivilegePK SPK = new SystemPrivilegePK(ids[0], ids[1]);
				newGroup.addSystemPrivilege(new SystemPrivilege(SPK));
			}
		}
		groupService.update(newGroup);
		request.setAttribute("message", "权限组修改成功");
		request.setAttribute("urladdress", "/control/privilegegroup/list");
		return SUCCESS;
	}
	
	/**
	 * 删除权限组
	 * @return
	 */
	@Action("delete")
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String groupid = request.getParameter("groupid");
		groupService.delete(groupid);
		request.setAttribute("message", "权限组删除成功");
		request.setAttribute("urladdress", "/control/privilegegroup/list");
		return SUCCESS;
	}
}
