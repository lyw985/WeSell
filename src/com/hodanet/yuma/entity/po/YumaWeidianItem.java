package com.hodanet.yuma.entity.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	/**
	 * 0.not done 1.done
	 */
	@Column(name = "done_status")
	private Integer doneStatus;

//	@OneToMany(targetEntity = YumaWeidianItemModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	@JoinColumn(name = "weidian_item_id", updatable = false)
	@Transient
	private List<YumaWeidianItemModel> yumaWeidianItemModels;

	@Transient
	private boolean showModels = false;

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

	@Transient
	private boolean showShadows;

	/*
	 * MAPPING_SHOW_ONE_MATCH = "1"; MAPPING_SHOW_MANY_MATCH = "2";
	 * MAPPING_SHOW_NO_MATCH = "3";
	 */
	@Transient
	private Integer mappingShowType;

	@Transient
	private boolean mappingDone;

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

	public boolean isShowModels() {
		return showModels;
	}

	public void setShowModels(boolean showModels) {
		this.showModels = showModels;
	}

	public Integer getMappingShowType() {
		return mappingShowType;
	}

	public void setMappingShowType(Integer mappingShowType) {
		this.mappingShowType = mappingShowType;
	}

	public void setDoneStatus(Integer doneStatus) {
		this.doneStatus = doneStatus;
	}

	public boolean isShowShadows() {
		return showShadows;
	}

	public void setShowShadows(boolean showShadows) {
		this.showShadows = showShadows;
	}

	public boolean isMappingDone() {
		return mappingDone;
	}

	public void setMappingDone(boolean mappingDone) {
		this.mappingDone = mappingDone;
	}

	public Integer getDoneStatus() {
		return doneStatus;
	}

	public void setBody(boolean isBody) {
		this.isBody = isBody;
	}

}
