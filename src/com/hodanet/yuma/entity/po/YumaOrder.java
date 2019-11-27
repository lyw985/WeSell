package com.hodanet.yuma.entity.po;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.hodanet.yuma.constant.YumaOrderPayStatus;
import com.hodanet.yuma.constant.YumaOrderStatus;

/**
 * @anthor lyw
 * @version 2016-11-11 10:26:57
 */
@Entity
@Table(name = "yuma_order")
public class YumaOrder {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaUser yumaUser;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaReceiver yumaReceiver;

	@OneToMany(targetEntity = YumaOrderItem.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "order_id", updatable = false)
	private List<YumaOrderItem> yumaOrderItems;

	/**  **/
	@Column(name = "pay_date")
	private Date payDate;
	
	@Column(name = "pay_price")
	private Float payPrice;
	
	@Column(name = "original_price")
	private Float originalPrice;
	
	@Column(name = "item_number")
	private Integer itemNumber;

	private Integer status;

	/** ʱ. */
	@Column(name = "create_time")
	private Date createTime;

	/** ޸ʱ. */
	@Column(name = "modified_time")
	private Date modifiedTime;

	@Transient
	private Date startDate;

	@Transient
	private Date endDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public YumaUser getYumaUser() {
		return yumaUser;
	}

	public void setYumaUser(YumaUser yumaUser) {
		this.yumaUser = yumaUser;
	}

	public YumaReceiver getYumaReceiver() {
		return yumaReceiver;
	}

	public void setYumaReceiver(YumaReceiver yumaReceiver) {
		this.yumaReceiver = yumaReceiver;
	}

	public List<YumaOrderItem> getYumaOrderItems() {
		return yumaOrderItems;
	}

	public void setYumaOrderItems(List<YumaOrderItem> yumaOrderItems) {
		this.yumaOrderItems = yumaOrderItems;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
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

	public String getPayStatusStr() {
		return YumaOrderPayStatus.getYumaOrderPayStatus(status).toString();
	}

	public String getStatusStr() {
		return YumaOrderStatus.getYumaOrderStatus(status).toString();
	}

	public String getDeliverDateStr() {
		if (payDate == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(payDate);
	}

	public void setDeliverDateStr(String deliverDateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.payDate = sdf.parse(deliverDateStr);
		} catch (Exception e) {
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
