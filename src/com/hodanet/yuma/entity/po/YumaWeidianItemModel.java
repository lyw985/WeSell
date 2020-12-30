package com.hodanet.yuma.entity.po;

import java.util.Set;

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
@Table(name = "yuma_weidian_item_model")
public class YumaWeidianItemModel {

	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "weidian_item_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaWeidianItem yumaWeidianItem;

//	@OneToMany(targetEntity = YumaWeidianItemModelMapping.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name = "weidian_item_model_id", updatable = false)
	@Transient
	private Set<YumaWeidianItemModelMapping> yumaWeidianItemModelMappings;

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

	public YumaWeidianItem getWeidianItem() {
		return yumaWeidianItem;
	}

	public void setWeidianItem(YumaWeidianItem yumaWeidianItem) {
		this.yumaWeidianItem = yumaWeidianItem;
	}

	public YumaWeidianItem getYumaWeidianItem() {
		return yumaWeidianItem;
	}

	public void setYumaWeidianItem(YumaWeidianItem yumaWeidianItem) {
		this.yumaWeidianItem = yumaWeidianItem;
	}

	public Set<YumaWeidianItemModelMapping> getYumaWeidianItemModelMappings() {
		return yumaWeidianItemModelMappings;
	}

	public void setYumaWeidianItemModelMappings(Set<YumaWeidianItemModelMapping> yumaWeidianItemModelMappings) {
		this.yumaWeidianItemModelMappings = yumaWeidianItemModelMappings;
	}


}
