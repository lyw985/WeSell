package com.hodanet.system.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lance.lengcs
 * @version 2012-8-13 2:17:39
 * 
 * <pre>
 * ʵ
 * </pre>
 */
@Entity
@Table(name = "auth_department")
public class Department {

    /** id. */
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private String  id;

    /** . */
    private String  name;

    /** ״̬. */
    private Integer status;

    /** ʱ. */
    @Column(name = "create_time")
    private Date    createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date    modifiedTime;

    /** . */
    @Column(name = "charge_person")
    private String  chargePerson;

    /** ˵绰. */
    @Column(name = "charge_person_phone")
    private String  chargePersonPhone;

    /** ֻ. */
    @Column(name = "charge_person_mobile")
    private String  chargePersonMobile;

    /** . */
    private String  description;

    /** . */
    private Integer ordering;

    /** ˵ID. */
    @Column(name = "parent_id")
    private String  parentId;

    public Department(){
    }

    public Department(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargePersonPhone() {
        return chargePersonPhone;
    }

    public void setChargePersonPhone(String chargePersonPhone) {
        this.chargePersonPhone = chargePersonPhone;
    }

    public String getChargePersonMobile() {
        return chargePersonMobile;
    }

    public void setChargePersonMobile(String chargePersonMobile) {
        this.chargePersonMobile = chargePersonMobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
