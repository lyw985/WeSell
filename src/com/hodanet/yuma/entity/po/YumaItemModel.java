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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.hodanet.yuma.constant.YumaItemModelStatus;

/**
 * @anthor lyw
 * @version 2016-11-27 10:26:57
 */
@Entity
@Table(name = "yuma_item_model")
public class YumaItemModel {

	/**  */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private YumaItem yumaItem;

	private String name;

	private Integer status;

//	@OneToMany(targetEntity = YumaWeidianItemModelMapping.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name = "item_model_id", updatable = false)
	@Transient
	private List<YumaWeidianItemModelMapping> yumaWeidianItemModelMappings;

	@Transient
	private boolean showModelMappings = false;

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

	public YumaItem getYumaItem() {
		return yumaItem;
	}

	public void setYumaItem(YumaItem yumaItem) {
		this.yumaItem = yumaItem;
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
		return YumaItemModelStatus.getYumaItemModelStatus(status).toString();
	}

	public List<YumaWeidianItemModelMapping> getYumaWeidianItemModelMappings() {
		return yumaWeidianItemModelMappings;
	}

	public void setYumaWeidianItemModelMappings(List<YumaWeidianItemModelMapping> yumaWeidianItemModelMappings) {
		this.yumaWeidianItemModelMappings = yumaWeidianItemModelMappings;
	}

	public boolean isShowModelMappings() {
		return showModelMappings;
	}

	public void setShowModelMappings(boolean showModelMappings) {
		this.showModelMappings = showModelMappings;
	}

}
