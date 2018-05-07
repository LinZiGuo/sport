package junit.test;

import javax.persistence.ExcludeSuperclassListeners;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.user.Buyer;
import cn.itcast.service.user.BuyerService;

public class BuyerTest {
	private static BuyerService buyerService;

	@BeforeClass
	public static void test() {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			buyerService = (BuyerService) applicationContext.getBean("buyerServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void save() {
		Buyer buyer = new Buyer();
		buyer.setUsername("lin");
		buyer.setPassword("123");
		buyer.setEmail("lin@ss.com");
		buyerService.save(buyer);
	}
	
	@Test
	public void find() {
		Buyer buyer = buyerService.find("lin");
		System.out.println(buyer.getEmail());
	}

	@Test
	public void update() {
		Buyer buyer = buyerService.find("lin");
		buyer.setEmail("666@sss.com");
		buyerService.update(buyer);
	}
	
	@Test
	public void delete() {
		buyerService.delete("lin");
	}
	
	@Test
	public void count() {
		System.out.println(buyerService.getCount());
	}
}
