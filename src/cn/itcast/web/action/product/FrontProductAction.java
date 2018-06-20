package cn.itcast.web.action.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

import cn.itcast.bean.PageView;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.bean.product.ProductType;
import cn.itcast.bean.product.Sex;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductTypeService;
@Controller("/product/list")
@ParentPackage("struts-default")
@Namespace("/product/list")
@Results({
	@Result(name="display",type="dispatcher",location="/WEB-INF/page/product/frontpage/productlist.jsp")
})
public class FrontProductAction extends ActionSupport implements ModelDriven<ProductInfo> {
	private ProductInfo productInfo = new ProductInfo();
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private ProductTypeService productTypeService;
	@Override
	public ProductInfo getModel() {
		return productInfo;
	}
	
	/**
	 * 获取展示前台商品页面
	 * @return
	 */
	@Action("display")
	public String display() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String typeid = request.getParameter("typeid");
		String brandid = request.getParameter("brandid");
		String sex = request.getParameter("sex");
		Integer id = typeid==null?0:Integer.valueOf(typeid);
		//获取分页信息
		int currentPage = (page==null?1:Integer.valueOf(page));
		int maxResult = 10;
		int firstResult = (currentPage-1)*maxResult;
		PageView<ProductInfo> pageView = new PageView<ProductInfo>(maxResult, currentPage);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<>();
		StringBuilder jpql = new StringBuilder("o.visible=?1");
		List<Object> params = new ArrayList<>();
		params.add(true);
		
		//获取下级分类信息
		List<Integer> typeids = new ArrayList<>();
		typeids.add(id);
		getTypeids(typeids, new Integer[] {id});
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < typeids.size(); i++) {
			ids.append("?").append(i+2).append(",");
		}
		ids.deleteCharAt(ids.length()-1);
		jpql.append(" and o.type.typeid in("+ids.toString()+")");
		params.addAll(typeids);
		
		//获取品牌信息
		if (brandid!=null&&!brandid.trim().equals("")) {
			jpql.append(" and o.brand.code=?").append(params.size()+1);
			params.add(productInfo.getBrand().getCode());
		}
		
		//获取性别倾向
		if (sex!=null) {
			if (sex.equalsIgnoreCase("NONE") || sex.equalsIgnoreCase("MAN") || sex.equalsIgnoreCase("WOMEN")) {
				jpql.append(" and o.sexrequest=?").append(params.size()+1);
				params.add(Sex.valueOf(sex));
			}
		}
		
		pageView.setQueryResult(productInfoService.getScrollData(firstResult,
				maxResult, jpql.toString(), params.toArray(), orderby));
		
		//添加商品样式
		for (ProductInfo product : pageView.getRecords()) {
			Set<ProductStyle> styles = new HashSet<>();
			for (ProductStyle style : product.getStyles()) {
				if (style.getVisible()) {
					styles.add(style);
					break;
				}
			}
			product.setStyles(styles);
			//注意:执行此句代码会把修改后的数据同步回数据库,如果不想把数据同步回数据库,请在其后调用productInfoService.clear();
			product.setDescription(product.getDescription());
		}
		//让托管状态的实体成为游离状态
		productInfoService.clear();
		request.setAttribute("pageView", pageView);
		Integer[] tids = new Integer[typeids.size()];
		for(int i=0;i<typeids.size();i++){
			tids[i]=typeids.get(i);
		}
		request.setAttribute("brands", productInfoService.getBrandsByProductTypeid(tids));	
		if(id!=null && id>0){
			ProductType type = productTypeService.find(id);
			if(type!=null){
				List<ProductType> types = new ArrayList<ProductType>();
				types.add(type);
				ProductType parent = type.getParent();
				while(parent!=null){
					types.add(parent);
					parent = parent.getParent();
				}
				request.setAttribute("producttype", type);
				request.setAttribute("types", types);
			}
		}
		return "display";
	}

	/**
	 * 获取类别下所有子类的id（注意：子类及其子类的id都会获取到）
	 * @param outtypeids
	 * @param typeids
	 */
	private void getTypeids(List<Integer> outtypeids, Integer[] typeids) {
		List<Integer> subtypeids =productTypeService.getSubTypeId(typeids);
		if(subtypeids!=null && subtypeids.size()>0){
			outtypeids.addAll(subtypeids);
			Integer[] ids = new Integer[subtypeids.size()];
			for(int i=0;i<subtypeids.size();i++){
				ids[i]=subtypeids.get(i);
			}
			getTypeids(outtypeids, ids);
		}
	}
}
