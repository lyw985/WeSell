package com.hodanet.common.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.hodanet.system.entity.po.User;

/**
 * @author lance.lengcs
 * @version 2012-8-20 5:28:43
 * 
 * <pre>
 * ļʵ
 * </pre>
 */
@Entity
@Table(name = "common_file")
public class HodanetFile implements Serializable {

    private static final long serialVersionUID = 1L;
    /** . */
    @Id
    private String            id;

    /** ļ. */
    private String            name;

    /** ļ. */
    private String            type;

    /** ļС. */
    private Long              length;

    /** ļ·. */
    private String            path;

    /** ϴ. */
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id")
    private User              user;

    /** ע. */
    private String            remark;

    /** ʱ. */
    @Column(name = "create_time")
    private Date              createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date              modifiedTime;

    public HodanetFile(){
    }

    public HodanetFile(String id){
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
