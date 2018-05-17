package junit.test;

import java.util.LinkedHashMap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.QueryResult;
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
	public void exsit() {
		System.out.println(buyerService.exsit("1"));
	}
	
	@Test
	public void getScrolldata() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		orderby.put("email", "asc");
		orderby.put("password", "desc");
		QueryResult<Buyer> qr = buyerService.getScrollData(0,5,"o.email=?1",new Object[]{"666"},orderby);
		for (Buyer buyer : qr.getResultlist()) {
			System.out.println(buyer.getEmail());
		}
		System.out.println("总记录数：" + qr.getRecordtotal());
	}
	
	@Test
	public void save() {
		for(int i=1;i<30;i++) {
			Buyer buyer = new Buyer();
			buyer.setUsername("lin"+i);
			buyer.setPassword("123"+(30-i));
			buyer.setEmail("lin@ss.com"+i);
			buyerService.save(buyer);
		}
	}
	
	@Test
	public void find() {
		Buyer buyer = buyerService.find("zhangming");
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
