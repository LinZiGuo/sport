package cn.itcast.service.privilege.impl;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.itcast.bean.privilege.Department;
import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.privilege.DepartmentService;
@Service
public class DepartmentServiceBean extends DaoSupport<Department> implements DepartmentService {

	@Override
	public void save(Department entity) {
		entity.setDepartmentid(UUID.randomUUID().toString());
		super.save(entity);
	}

	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			Department department = this.find(id);
			for(Employee employee : department.getEmployees()){
				employee.setDepartment(null);
			}
			em.remove(department);
		}
	}

}
