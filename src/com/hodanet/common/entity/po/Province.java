package com.hodanet.common.entity.po;

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
 * @anthor Administrator
 * @version 2016-11-13 10:47:32
 */
@Entity
@Table(name = "province")
public class Province {

	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "body_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Province body;

	@OneToMany(targetEntity = Province.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "body_id", updatable = false)
	private List<Province> shadows;

	@OneToMany(targetEntity = City.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "province_id", updatable = false)
	private List<City> citys;

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

	public Province getBody() {
		return body;
	}

	public void setBody(Province body) {
		this.body = body;
	}

	public List<Province> getShadows() {
		return shadows;
	}

	public void setShadows(List<Province> shadows) {
		this.shadows = shadows;
	}

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

}
