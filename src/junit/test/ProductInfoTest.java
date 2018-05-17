package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductType;
import cn.itcast.bean.product.Sex;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductTypeService;

public class ProductInfoTest {
	private static ProductInfoService productInfoService;
	private static ProductTypeService productTypeService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			productInfoService = (ProductInfoService) applicationContext.getBean("productInfoServiceBean");
			productTypeService = (ProductTypeService) applicationContext.getBean("productTypeServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		for(int i=1;i<30;i++) {
			ProductInfo productInfo = new ProductInfo();
			ProductType type = productTypeService.find(i+1);
			productInfo.setName("name"+i);
			productInfo.setCode("20180514----"+i);
			productInfo.setBaseprice((float) i);
			productInfo.setSellprice((float) i);
			productInfo.setMarketprice((float) i);
			productInfo.setDescription("20180501400*****"+i);
			productInfo.setType(type);
			productInfo.setClickcount(i);
			productInfo.setDescription("description"+i);
			productInfo.setSellcount(i);
			productInfoService.save(productInfo);
		}
	}

}
