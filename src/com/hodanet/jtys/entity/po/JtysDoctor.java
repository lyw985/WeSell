package com.hodanet.jtys.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

/**
 * @anthor lyw
 * @version 2014-8-27 4:26:57
 */
@Entity
@Table(name = "jtys_doctor")
public class JtysDoctor {

    @Id
    private String id;

    private String name;

    private String title;

    private String image;

    private String clinic;

    private String hospital;

    /** ʱ. */
    @Column(name = "create_time")
    private Date   createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date   modifiedTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public static JtysDoctor parseFromJson(String jsonStr) {
        JtysDoctor doctor = new JtysDoctor();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        doctor.setId(jsonObject.getString("id"));
        doctor.setName(jsonObject.getString("name"));
        doctor.setTitle(jsonObject.getString("title"));
        doctor.setImage(jsonObject.getString("image"));
        doctor.setClinic(jsonObject.getString("clinic"));
        doctor.setHospital(jsonObject.getString("hospital"));
        return doctor;
    }
}
