package com.hodanet.system.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lance.lengcs
 * @version 2012-8-5 9:34:20
 * 
 * <pre>
 * û¼Ϣʵ
 * </pre>
 */
@Entity
@Table(name = "auth_user_login_info")
public class UserLoginInfo {

    /** id. */
    @Id
    @Column(name = "login_id")
    private String  loginId;

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

    /** ϴε¼ʱ. */
    @Column(name = "last_login_time")
    private Date    lastLoginTime;

    /** . */
    private String  description;

    public UserLoginInfo(){
    }

    public UserLoginInfo(String loginId){
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
