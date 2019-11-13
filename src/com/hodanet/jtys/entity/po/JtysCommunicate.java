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

/**
 * <pre>
 * 家庭医生问题
 * </pre>
 */
@Entity
@Table(name = "jtys_communicate")
public class JtysCommunicate {

    /** 主键 */
    @Id
    @GeneratedValue(generator = "incrementGenerator")
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
    private Integer    id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private JtysUser   user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private JtysDoctor doctor;

    @Column(name = "problem_id")
    private String     problemId;

    @Column(name = "is_first")
    private Integer    isFirst;     // 是否是第一条 0.不是 1.是

    @Column(name = "is_finish")
    private Integer    isFinish;    // 是否结束 0.未结束 1.已结束

    private Integer    type;        // 0.问题 1.答案

    private String     text;

    @Column(name = "image_url")
    private String     imageUrl;

    @Column(name = "audio_url")
    private String     audioUrl;

    /** 状态 */
    private Integer    status;

    /** 创建时间. */
    @Column(name = "create_time")
    private Date       createTime;

    /** 修改时间. */
    @Column(name = "modified_time")
    private Date       modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JtysUser getUser() {
        return user;
    }

    public void setUser(JtysUser user) {
        this.user = user;
    }

    public JtysDoctor getDoctor() {
        return doctor;
    }

    public void setDoctor(JtysDoctor doctor) {
        this.doctor = doctor;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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

}
