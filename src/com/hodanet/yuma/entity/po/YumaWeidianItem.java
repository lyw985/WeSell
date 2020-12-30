package com.hodanet.yuma.entity.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @anthor lyw
 * @version 2019-10-16
 */
@Entity
@Table(name = "yuma_weidian_item")
public class YumaWeidianItem {

	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	private String name;

//	@OneToMany(targetEntity = YumaWeidianItemModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name = "weidian_item_id", updatable = false)
	@Transient
	private List<YumaWeidianItemModel> yumaWeidianItemModels;
	
//	@OneToMany(targetEntity = YumaWeidianItem.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name = "body_id", updatable = false)
	@Transient
	private List<YumaWeidianItem> shadows;

	@ManyToOne
	@JoinColumn(name = "body_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaWeidianItem body;

	@Transient
	private Boolean isBody;

	public Boolean getIsBody() {
		return isBody;
	}

	public void setIsBody(Boolean isBody) {
		this.isBody = isBody;
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

	public List<YumaWeidianItemModel> getYumaWeidianItemModels() {
		return yumaWeidianItemModels;
	}

	public void setYumaWeidianItemModels(List<YumaWeidianItemModel> yumaWeidianItemModels) {
		this.yumaWeidianItemModels = yumaWeidianItemModels;
	}
	
	public List<YumaWeidianItem> getShadows() {
		return shadows;
	}

	public void setShadows(List<YumaWeidianItem> shadows) {
		this.shadows = shadows;
	}

	public YumaWeidianItem getBody() {
		return body;
	}

	public void setBody(YumaWeidianItem body) {
		this.body = body;
	}

}
