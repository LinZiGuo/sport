package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.privilege.Department;
import cn.itcast.service.privilege.DepartmentService;

public class PrivilegeTest {
	private static DepartmentService departmentService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			departmentService = (DepartmentService) applicationContext.getBean("departmentServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		Department department = new Department();
		department.setName("财务部");
		departmentService.save(department);
	}

}
