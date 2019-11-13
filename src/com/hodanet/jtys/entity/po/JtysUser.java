package com.hodanet.jtys.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hodanet.jtys.constant.JtysUserStatus;

/**
 * @anthor lyw
 * @version 2014-8-27 4:26:57
 */
@Entity
@Table(name = "jtys_user")
public class JtysUser {

    /**  */
    @Id
    @GeneratedValue(generator = "incrementGenerator")
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
    private Integer id;

    @Column(name = "open_id")
    private String  openId;

    private String  phone;

    private String  code;

    @Column(name = "can_free")
    private Integer canFree;

    @Column(name = "chunyu_regs")
    private Integer chunyuRegs;  // Ƿڴҽע 0.δע 1.ע

    private Integer status;

    /** ʱ. */
    @Column(name = "expire_time")
    private Date    expireTime;

    /** ʱ. */
    @Column(name = "create_time")
    private Date    createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date    modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCanFree() {
        return canFree;
    }

    public void setCanFree(Integer canFree) {
        this.canFree = canFree;
    }

    public Integer getChunyuRegs() {
        return chunyuRegs;
    }

    public void setChunyuRegs(Integer chunyuRegs) {
        this.chunyuRegs = chunyuRegs;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
        return JtysUserStatus.getJtysUserStatus(status).toString();
    }

}
