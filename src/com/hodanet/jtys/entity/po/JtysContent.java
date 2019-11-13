package com.hodanet.jtys.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.hodanet.jtys.constant.JtysContentStatus;
import com.hodanet.jtys.constant.JtysContentType;
import com.hodanet.system.entity.po.User;

/**
 * <pre>
 * 家庭医生健康咨询
 * </pre>
 */
@Entity
@Table(name = "jtys_content")
public class JtysContent {

    /** 主键 */
    @Id
    @GeneratedValue(generator = "incrementGenerator")
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
    private Integer id;

    /** 消息类型 1.文字消息 2.图文消息 **/
    private Integer msgType;

    /** 文本内容 */
    private String  content;

    /** 图文内容 **/
    private String  title;

    private String  description;

    private String  picUrl;

    private String  url;

    /** 类型 */
    private Integer type;

    /** 状态 */
    private Integer status;

    /** 创建时间. */
    @Column(name = "create_time")
    private Date    createTime;

    /** 修改时间. */
    @Column(name = "modified_time")
    private Date    modifiedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User    user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTypeStr() {
        return JtysContentType.getJtysContentType(type).getTip();
    }

    public String getStatusStr() {
        return JtysContentStatus.getJtysContentStatus(status).toString();
    }

}
