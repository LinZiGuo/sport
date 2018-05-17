package cn.itcast.web.action.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bean.product.Brand;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductType;
import cn.itcast.bean.product.Sex;
import cn.itcast.service.product.BrandService;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductTypeService;
@Controller("/control/product/manage")
@ParentPackage("struts-default")
@Namespace("/control/product/manage")
@Results({
	@Result(name="success",type="dispatcher",location="/WEB-INF/page/share/message.jsp"),
	@Result(name="queryUI",type="dispatcher",location="/WEB-INF/page/product/query_product.jsp"),
	@Result(name="addUI",type="dispatcher",location="/WEB-INF/page/product/add_product.jsp"),
	@Result(name="editUI",type="dispatcher",location="/WEB-INF/page/product/edit_product.jsp"),
	@Result(name="selectUI",type="dispatcher",location="/WEB-INF/page/product/productTypeSelect.jsp")	
})
public class ProductInfoManageAction extends ActionSupport implements ModelDriven<ProductInfo> {

	private ProductInfo productInfo = new ProductInfo();
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private BrandService brandService;
	@Resource
	private ProductTypeService productTypeService;
	private List<Brand> brands;
	
	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	@Override
	public ProductInfo getModel() {
		return productInfo;
	}
	
	@Action("queryUI")
	public String queryUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		brands = brandService.getScrollData().getResultlist();
		request.setAttribute("brands", brands);
		return "queryUI";
	}
	
	/**
	 * 获取添加商品页面
	 * @return
	 */
	@Action("addUI")
	public String addUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		brands = brandService.getScrollData().getResultlist();
		request.setAttribute("brands", brands);
		return "addUI";
	}
	
	/**
	 * 添加商品
	 * @return
	 */
	@Action("add")
	public String add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		String sex = request.getParameter("sex");
		String brandid = request.getParameter("brandid");
		if (typeid!=null&&!typeid.equals("")) {
			Integer id = Integer.valueOf(typeid);
			if (id>0) {
				productInfo.setType(productTypeService.find(id));
			}
		}
		if (brandid!=null&&!brandid.equals("")) {
			productInfo.setBrand(brandService.find(brandid));
		}
		productInfo.setSexrequest(Sex.valueOf(sex));
		productInfoService.save(productInfo);
		request.setAttribute("message", "添加类别成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
	
	/**
	 * 获取修改商品页面
	 * @return
	 */
	@Action("editUI")
	public String editUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String productid = request.getParameter("productid");
		brands = brandService.getScrollData().getResultlist();
		if (productid!=null&&!productid.equals("")) {
			Integer id = Integer.valueOf(productid);
			if (id>0) {
				productInfo = productInfoService.find(id);
			}
		}
		request.setAttribute("product", productInfo);
		request.setAttribute("brands", brands);
		return "editUI";
	}
	
	/**
	 * 修改商品
	 * @return
	 */
	@Action("edit")
	public String edit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String productid = request.getParameter("productid");
		String typeid = request.getParameter("typeid");
		String sex = request.getParameter("sex");
		String brandid = request.getParameter("brandid");
		ProductInfo newProduct = new ProductInfo();
		if (productid!=null&&!productid.equals("")) {
			Integer id = Integer.valueOf(productid);
			if (id>0) {
				newProduct = productInfoService.find(id);
			}
		}
		if (typeid!=null&&!typeid.equals("")) {
			Integer id = Integer.valueOf(typeid);
			if (id>0) {
				newProduct.setType(productTypeService.find(id));
			}
		}
		if (brandid!=null&&!brandid.equals("")) {
			newProduct.setBrand(brandService.find(brandid));
		}
		newProduct.setSexrequest(Sex.valueOf(sex));
		newProduct.setName(productInfo.getName());
		newProduct.setBaseprice(productInfo.getBaseprice());
		newProduct.setMarketprice(productInfo.getMarketprice());
		newProduct.setSellprice(productInfo.getSellprice());
		newProduct.setCode(productInfo.getCode());
		newProduct.setModel(productInfo.getModel());
		newProduct.setWeight(productInfo.getWeight());
		newProduct.setBuyexplain(productInfo.getBuyexplain());
		newProduct.setDescription(productInfo.getDescription());
		productInfoService.update(newProduct);
		request.setAttribute("message", "修改类别成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
	
	/**
	 * 获取选择类别页面
	 * @return
	 */
	@Action("selectUI")
	public String selectUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeid = request.getParameter("typeid");
		String jpql;
		List<ProductType> list = new ArrayList<>();;
		Object[] params = new Object[0];
		if (typeid!=null && Integer.valueOf(typeid)>0) {
			Integer id = Integer.valueOf(typeid);
			ProductType type = productTypeService.find(id);
			list.addAll(type.getChildtypes());
			ProductType parent = type.getParent();
			List<ProductType> types = new ArrayList<>();
			types.add(type);
			while (parent!=null) {
				types.add(parent);
				parent = parent.getParent();
			}
			request.setAttribute("menutypes", types);
		} else {
			jpql = "o.parent is null and o.visible=true";
			list = productTypeService.getScrollData(-1, -1, jpql, params).getResultlist();
		}
		request.setAttribute("types", list);
		return "selectUI";
	}
	
	@Action("visible")
	public String visible() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] productids = request.getParameterValues("productids");
		productInfoService.setVisibleStatu(productids,true);
		request.setAttribute("message", "上架成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
	
	@Action("disable")
	public String disable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] productids = request.getParameterValues("productids");
		productInfoService.setVisibleStatu(productids,false);
		request.setAttribute("message", "下架成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
	
	@Action("commend")
	public String commend() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] productids = request.getParameterValues("productids");
		productInfoService.setCommendStatu(productids,true);
		request.setAttribute("message", "推荐成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
	
	@Action("uncommend")
	public String uncommend() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] productids = request.getParameterValues("productids");
		productInfoService.setCommendStatu(productids,false);
		request.setAttribute("message", "取消推荐成功");
		request.setAttribute("urladdress", "/control/product/list");
		return SUCCESS;
	}
}
