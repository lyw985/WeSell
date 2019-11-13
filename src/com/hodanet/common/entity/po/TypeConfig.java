package com.hodanet.common.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lance.lengcs
 * @version 2012-8-21 10:52:31
 * 
 * <pre>
 * 	 ʵࣨϵͳһЩԵֵã
 * </pre>
 */
@Entity
@Table(name = "common_type_config")
public class TypeConfig {

    /** . */
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private String id;

    /** . */
    private String name;

    /** ״̬ */
    private int    status;

    /** ʱ. */
    @Column(name = "create_time")
    private Date   createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date   modifiedTime;

    /** . */
    private String module;

    /** ͱ. */
    private String code;

    /** ע. */
    private String remark;

    /**  */
    private int    ordering;

    public TypeConfig(){
    }

    public TypeConfig(String id){
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

}
