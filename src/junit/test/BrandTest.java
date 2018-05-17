package junit.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.product.Brand;
import cn.itcast.service.product.BrandService;
import cn.itcast.utils.UploadFile;

public class BrandTest {

	private static BrandService brandService;
	
	@BeforeClass
	public static void test() {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			brandService = (BrandService) applicationContext.getBean("brandServiceBean");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		for (int i = 0; i < 30; i++) {
			Brand brand = new Brand("name"+i,"logopath"+i);
			brand.setCode("20180513"+i);
			brandService.save(brand);
		}
	}
	
	@Test
	public void branks() {
		List<Brand> list = brandService.getScrollData().getResultlist();
		System.out.println(list.toString());
	}
	
	@Test
	public void valildateImage() {
		System.out.println(UploadFile.validateImageFileType("adf", "abc.jpg"));
	}
}
