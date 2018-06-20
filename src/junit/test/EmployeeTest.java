package junit.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.privilege.Department;
import cn.itcast.bean.privilege.Employee;
import cn.itcast.bean.privilege.IDCard;
import cn.itcast.bean.user.Gender;
import cn.itcast.service.order.GeneratedOrderidService;
import cn.itcast.service.order.OrderContactInfoService;
import cn.itcast.service.order.OrderItemService;
import cn.itcast.service.order.OrderService;
import cn.itcast.service.privilege.EmployeeService;

public class EmployeeTest {
	private static EmployeeService employeeService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			employeeService = (EmployeeService) applicationContext.getBean("employeeServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() throws Exception {
		Employee employee = new Employee();
		employee.setUsername("tomcat2");
		employee.setDegree("本科");
		employee.setEmail("750398747@qq.com");
		employee.setGender(Gender.WOMEN);
		employee.setImageName("aa.jpg");
		employee.setPassword("123456");
		employee.setPhone("18369874563");
		employee.setRealname("tomddddd");
		employee.setSchool("广东工业大学");
		employee.setDepartment(new Department("97df5f85-a7f3-4079-a28a-00c86da75713"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		employee.setIdCard(new IDCard("181199208036548", "WWee", sdf.parse("2018-03-08")));
		employeeService.save(employee);
	}

}
