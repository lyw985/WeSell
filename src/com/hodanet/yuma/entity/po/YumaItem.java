package com.hodanet.yuma.entity.po;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.hodanet.yuma.constant.YumaItemStatus;
import com.hodanet.yuma.constant.YumaItemType;

/**
 * @anthor lyw
 * @version 2016-11-17 10:26:57
 */
@Entity
@Table(name = "yuma_item")
public class YumaItem {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	private String name;

	private Integer status;
	
	private Integer type;

	@OneToMany(targetEntity = YumaItemModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "item_id", updatable = false)
	private List<YumaItemModel> yumaItemModels;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<YumaItemModel> getYumaItemModels() {
		return yumaItemModels;
	}

	public void setYumaItemModels(List<YumaItemModel> yumaItemModels) {
		this.yumaItemModels = yumaItemModels;
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
		return YumaItemStatus.getYumaItemStatus(status).toString();
	}
	
	public String getTypeStr() {
		return YumaItemType.getYumaItemType(type).toString();
	}

}
