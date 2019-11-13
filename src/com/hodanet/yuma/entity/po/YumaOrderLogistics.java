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
@Table(name = "yuma_order_logistics")
public class YumaOrderLogistics {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaOrder yumaOrder;

	private Integer status;

	/** ʱ. */
	@Column(name = "create_time")
	private Date createTime;

	/** ޸ʱ. */
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
		return YumaOrderLogisticsStatus.getYumaOrderLogisticsStatus(status)
				.toString();
	}

}
