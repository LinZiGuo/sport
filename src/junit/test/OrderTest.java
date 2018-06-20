package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.order.DeliverWay;
import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.bean.order.OrderItem;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.service.order.GeneratedOrderidService;
import cn.itcast.service.order.OrderContactInfoService;
import cn.itcast.service.order.OrderItemService;
import cn.itcast.service.order.OrderService;

public class OrderTest {
	private static OrderService orderService;
	private static GeneratedOrderidService generatedOrderidService;
	private static OrderContactInfoService orderContactInfoService;
	private static OrderItemService orderItemService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			orderService = (OrderService) applicationContext.getBean("orderServiceBean");
			generatedOrderidService = (GeneratedOrderidService) applicationContext.getBean("generatedOrderidServiceBean");
			orderContactInfoService = (OrderContactInfoService) applicationContext.getBean("orderContactInfoServiceBean");
			orderItemService = (OrderItemService) applicationContext.getBean("orderItemServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		Order order = orderService.createOrder(null, null);
		System.out.println(order.getOrderid());
	}

	@Test
	public void findOrderContactInfo() {
		OrderContactInfo contact = orderContactInfoService.find(1);
		System.out.println("联系人姓名："+contact.getBuyerName());
	}
	
	@Test
	public void updatePaymentWay() {
		orderService.updatePaymentWay("18060600000006", PaymentWay.COD);
		Order order = orderService.find("18060600000006");
		System.out.println("18060600000006的支付方式："+order.getPaymentWay().toString());
	}
	
	@Test
	public void updateDeliverWay() {
		orderService.updateDeliverWay("18060600000006", DeliverWay.GENERALPOST);
		Order order = orderService.find("18060600000006");
		System.out.println("18060600000006的支付方式："+order.getOrderDeliverInfo().getDeliverWay().toString());
	}
	
	@Test
	public void updateAmount() {
		orderItemService.updateAmount(6, 8);
		OrderItem orderItem = orderItemService.find(6);
		System.out.println( orderItem.getProductName()+"的数量："+orderItem.getAmount());			
	}
	
	@Test
	public void updateDeliverFee() {
		orderService.updateDeliverFee("18060600000006", 5.0f);
		Order order = orderService.find("18060600000006");
		System.out.println("18060600000006的配送费："+order.getDeliverFee());
	}
	
	@Test
	public void updateContactInfo() {
		OrderContactInfo contact = orderContactInfoService.find(5);
		System.out.println("修改前购买者姓名："+contact.getBuyerName());
		contact.setBuyerName("test");
		orderContactInfoService.update(contact);
		OrderContactInfo contact2 = orderContactInfoService.find(5);
		System.out.println("修改后购买者姓名："+contact2.getBuyerName());
	}
	
	@Test
	public void deleteOrderItem() {
		orderItemService.delete(1);
	}
}
