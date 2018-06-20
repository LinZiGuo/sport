package junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductStyleService;
import cn.itcast.service.product.ProductTypeService;

public class ProductStyleTest {
	private static ProductStyleService productStyleService;
	private static ProductInfoService productInfoService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			productStyleService = (ProductStyleService) applicationContext.getBean("productStyleServiceBean");
			productInfoService = (ProductInfoService) applicationContext.getBean("productInfoServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		for (int i = 0; i < 30; i++) {
			ProductStyle productStyle = new ProductStyle("name"+i, i+"abc.jpg");
			ProductInfo productInfo = productInfoService.find(i+2);
			productStyle.setProduct(productInfo);
			productStyleService.save(productStyle);
		}
	}

}
