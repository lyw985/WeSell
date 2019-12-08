package com.hodanet.yuma.entity.po;

import java.util.Date;

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

import com.hodanet.yuma.constant.YumaOrderLogisticsStatus;

/**
 * @anthor lyw
 * @version 2016-11-11 10:26:57
 */
@Entity
@Table(name = "yuma_order_item")
public class YumaOrderItem {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaOrder yumaOrder;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaReceiver yumaReceiver;

	@ManyToOne
	@JoinColumn(name = "weidian_item_model_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaWeidianItemModel yumaWeidianItemModel;

	private Integer count;

	@Column(name = "pay_time")
	private Date payTime;

	@Column(name = "pay_price")
	private Float payPrice;

	@Column(name = "original_price")
	private Float originalPrice;

	private Integer status;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "modified_time")
	private Date modifiedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public YumaOrder getYumaOrder() {
		return yumaOrder;
	}

	public void setYumaOrder(YumaOrder yumaOrder) {
		this.yumaOrder = yumaOrder;
	}

	public YumaReceiver getYumaReceiver() {
		return yumaReceiver;
	}

	public void setYumaReceiver(YumaReceiver yumaReceiver) {
		this.yumaReceiver = yumaReceiver;
	}

	public YumaWeidianItemModel getYumaWeidianItemModel() {
		return yumaWeidianItemModel;
	}

	public void setYumaWeidianItemModel(YumaWeidianItemModel yumaWeidianItemModel) {
		this.yumaWeidianItemModel = yumaWeidianItemModel;
	}

	public YumaWeidianItemModel getWeidianItemModel() {
		return yumaWeidianItemModel;
	}

	public void setWeidianItemModel(YumaWeidianItemModel yumaWeidianItemModel) {
		this.yumaWeidianItemModel = yumaWeidianItemModel;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Float payPrice) {
		this.payPrice = payPrice;
	}

	public Float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getStatusStr() {
		return YumaOrderLogisticsStatus.getYumaOrderLogisticsStatus(status).toString();
	}

}
