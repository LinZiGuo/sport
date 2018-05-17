package cn.itcast.bean.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
@Entity
@Searchable(root=false)
public class ProductType implements Serializable {

	private static final long serialVersionUID = -2002504086759470456L;
	//类别Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private Integer typeid;
	//类别名称
	@Column(length=36,nullable=false)
	@SearchableProperty(index=Index.NOT_ANALYZED,store=Store.YES,name="typeName")
	private String name;
	//备注，用于Google搜索页面描述
	@Column(length=200)
	private String note;
	//是否可见
	@Column(nullable=false)
	private Boolean visible = true;
	//子类别
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent",fetch=FetchType.EAGER)
	private Set<ProductType> childtypes = new HashSet<>();
	//所属父类
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentid")
	private ProductType parent;
	//对应的产品
	@OneToMany(mappedBy="type", cascade=CascadeType.REMOVE)
	private Set<ProductInfo> products = new HashSet<>();

	public ProductType() {
		super();
	}

	public ProductType(String name, String note) {
		super();
		this.name = name;
		this.note = note;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<ProductType> getChildtypes() {
		return childtypes;
	}

	public void setChildtypes(Set<ProductType> childtypes) {
		this.childtypes = childtypes;
	}

	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	public Set<ProductInfo> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductInfo> products) {
		this.products = products;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
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
		ProductType other = (ProductType) obj;
		if (typeid == null) {
			if (other.typeid != null)
				return false;
		} else if (!typeid.equals(other.typeid))
			return false;
		return true;
	}

}
