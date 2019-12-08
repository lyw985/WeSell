package com.hodanet.yuma.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @anthor lyw
 * @version 2019-10-16
 */
@Entity
@Table(name = "yuma_weidian_item_model_mapping")
public class YumaWeidianItemModelMapping {

	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "weidian_item_model_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaWeidianItemModel yumaWeidianItemModel;

	@ManyToOne
	@JoinColumn(name = "item_model_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaItemModel yumaItemModel;

	private Float count;

	@Column(name = "price_percent")
	private Float pricePercent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public YumaWeidianItemModel getYumaWeidianItemModel() {
		return yumaWeidianItemModel;
	}

	public void setYumaWeidianItemModel(YumaWeidianItemModel yumaWeidianItemModel) {
		this.yumaWeidianItemModel = yumaWeidianItemModel;
	}

	public YumaItemModel getYumaItemModel() {
		return yumaItemModel;
	}

	public void setYumaItemModel(YumaItemModel yumaItemModel) {
		this.yumaItemModel = yumaItemModel;
	}

	public Float getCount() {
		return count;
	}

	public void setCount(Float count) {
		this.count = count;
	}

	public Float getPricePercent() {
		return pricePercent;
	}

	public void setPricePercent(Float pricePercent) {
		this.pricePercent = pricePercent;
	}

}
