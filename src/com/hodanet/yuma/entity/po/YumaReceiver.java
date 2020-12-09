package com.hodanet.yuma.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.jtys.constant.JtysUserStatus;

/**
 * @anthor Administrator
 * @version 2016-11-13 10:11:04
 */
@Entity
@Table(name = "yuma_receiver")
public class YumaReceiver {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	private String name;

	private String phone;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaUser yumaUser;

	@OneToOne
	@JoinColumn(name = "province_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Province province;

	@OneToOne
	@JoinColumn(name = "city_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private City city;

	@OneToOne
	@JoinColumn(name = "area_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Area area;

	@Column(name = "address_detail")
	private String addressDetail;

	private String lat;

	private String lng;

	private Integer status;

	@Column(name = "sync_status")
	private Integer syncStatus;

	@Column(name = "default_address")
	private Integer defaultAddress;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public YumaUser getYumaUser() {
		return yumaUser;
	}

	public void setYumaUser(YumaUser yumaUser) {
		this.yumaUser = yumaUser;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getStatus() {
		return status;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Integer defaultAddress) {
		this.defaultAddress = defaultAddress;
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

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDetail() {
		return this.getProvince().getName() + this.getArea().getName() + this.getCity().getName()
				+ this.getAddressDetail();
	}

	public String getStatusStr() {
		return JtysUserStatus.getJtysUserStatus(status).toString();
	}
}
