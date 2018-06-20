package cn.itcast.bean.product;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
@Entity
@Searchable
public class ProductInfo implements Serializable {

	private static final long serialVersionUID = -2945451827237255352L;
	//产品Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SearchableId
	private Integer id;
	//货号
	@Column(length=30)
	private String code;
	//产品名称
	@Column(length=50,nullable=false) @SearchableProperty(boost=2,name="productName")
	private String name;
	//品牌
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="brandid")
	@SearchableComponent
	private Brand brand;
	//型号
	@Column(length=20)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private String model;
	//底价 采购价
	@Column(nullable=false)
	private Float baseprice;
	//市场价
	@Column(nullable=false)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private Float marketprice;
	//销售价
	@Column(nullable=false)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private Float sellprice;
	//重量 单位：克
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private Integer weight;
	//产品简介
	@Column(nullable=false)
	@SearchableProperty
	private String description;
	//购买说明
	@Column(length=30)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private String buyexplain;
	//是否可见
	@Column(nullable=false)
	private Boolean visible = true;
	//产品类型
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="typeid")
	@SearchableComponent
	private ProductType type;
	//上架日期
	@Temporal(TemporalType.DATE)
	private Date createdate = new Date();
	//人气指数
	@Column(nullable=false)
	private Integer clickcount = 1;
	//销售量
	@Column(nullable=false)
	private Integer sellcount = 0;
	//是否推荐
	@Column(nullable=false)
	private Boolean commend = false;
	//性别要求
	@Enumerated(EnumType.STRING)
	@Column(length=5,nullable=false)
	private Sex sexrequest = Sex.NONE;
	//产品样式
	@OneToMany(cascade={CascadeType.REMOVE,CascadeType.PERSIST}, mappedBy="product", fetch=FetchType.EAGER)
	@OrderBy("visible desc, id asc")
	@SearchableComponent
	private Set<ProductStyle> styles = new HashSet<>();
	
	public ProductInfo() {	}
	
	public ProductInfo(Integer id) {
		super();
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Float getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(Float baseprice) {
		this.baseprice = baseprice;
	}
	public Float getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(Float marketprice) {
		this.marketprice = marketprice;
	}
	public Float getSellprice() {
		return sellprice;
	}
	public void setSellprice(Float sellprice) {
		this.sellprice = sellprice;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBuyexplain() {
		return buyexplain;
	}
	public void setBuyexplain(String buyexplain) {
		this.buyexplain = buyexplain;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Integer getClickcount() {
		return clickcount;
	}
	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}
	public Integer getSellcount() {
		return sellcount;
	}
	public void setSellcount(Integer sellcount) {
		this.sellcount = sellcount;
	}
	public Boolean getCommend() {
		return commend;
	}
	public void setCommend(Boolean commend) {
		this.commend = commend;
	}
	public Sex getSexrequest() {
		return sexrequest;
	}
	public void setSexrequest(Sex sexrequest) {
		this.sexrequest = sexrequest;
	}
	public Set<ProductStyle> getStyles() {
		return styles;
	}
	public void setStyles(Set<ProductStyle> styles) {
		this.styles = styles;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInfo other = (ProductInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/**
	 * 从样式集合中删除指定样式
	 * @param style
	 */
	public void removeProductStyle(ProductStyle style){
		if(this.styles.contains(style)){
			this.styles.remove(style);
			style.setProduct(null);
		}
	}	
	/**
	 * 添加样式到样式集合
	 * @param style
	 */
	public void addProductStyle(ProductStyle style){
		if(!this.styles.contains(style)){
			this.styles.add(style);
			style.setProduct(this);
		}
	}
	
}
