package cn.itcast.bean.product;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;
import org.compass.annotations.Store;
@Entity
@Searchable(root=false)
public class ProductStyle implements Serializable {

	private static final long serialVersionUID = 2976685372549131379L;
	//样式id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private Integer id;
	//样式的名称
	@Column(length=30,nullable=false)
	@SearchableProperty(index=Index.NO,store=Store.YES,name="styleName")
	private String name;
	//图片
	@Column(length=40,nullable=false)
	@SearchableProperty(index=Index.NO,store=Store.YES)
	private String imagename;
	//是否可见
	@Column(nullable=false)
	private Boolean visible = true;
	//对应的产品
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="productid")
	@SearchableReference
	private ProductInfo product;
	public ProductStyle() {
		super();
	}
	public ProductStyle(Integer id) {
		super();
		this.id = id;
	}
	public ProductStyle(String name, String imagename) {
		super();
		this.name = name;
		this.imagename = imagename;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
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
		ProductStyle other = (ProductStyle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
