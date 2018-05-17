package junit.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.product.Brand;
import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductTypeService;

public class ProductTypeTest {
	private static ProductTypeService productTypeService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			productTypeService = (ProductTypeService) applicationContext.getBean("productTypeServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void save() {
		for (int i = 0; i < 30; i++) {
			ProductType productType = new ProductType("name"+i,"note"+i);
			productTypeService.save(productType);
		}
	}

	@Test
	public void getSubTypeId() {
		Integer[] parentids = new Integer[] {1,2};
		List<Integer> list = productTypeService.getSubTypeId(parentids);
		System.out.println(list.toString());
	}
	
	@Test
	public void getSub() {
		String jpql="o.prent.typeid = ?1";
		Object[] params = new Object[] {1};
		productTypeService.getScrollData(-1, -1, jpql, params);
	}
}
