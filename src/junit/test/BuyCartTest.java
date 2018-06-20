package junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;

public class BuyCartTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void save() {
		BuyCart cart = new BuyCart();
		BuyItem item = new BuyItem();
		ProductInfo product = new ProductInfo(8);
		product.addProductStyle(new ProductStyle(35));
		item.setProduct(product);
		cart.addBuyItem(item);
		
		for (BuyItem buyItem : cart.getItems()) {
			System.out.println(buyItem.getProduct().getId()+","+
		buyItem.getProduct().getStyles().iterator().next().getId());
		}
	}

	@Test
	public void save2() {
		BuyCart cart = new BuyCart();
		BuyItem item = new BuyItem();
		ProductInfo product = new ProductInfo(8);
		product.addProductStyle(new ProductStyle(35));
		item.setProduct(product);
		cart.addBuyItem(item);
		
		BuyItem item2 = new BuyItem();
		ProductInfo product2 = new ProductInfo(8);
		product2.addProductStyle(new ProductStyle(38));
		item2.setProduct(product2);
		cart.addBuyItem(item2);
		
		for (BuyItem buyItem : cart.getItems()) {
			System.out.println(buyItem.getProduct().getId()+","+
		buyItem.getProduct().getStyles().iterator().next().getId()+
		",amount="+buyItem.getAmount());
		}
	}
}
