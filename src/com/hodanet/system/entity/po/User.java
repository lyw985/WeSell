package com.hodanet.system.entity.po;

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
 * @author lance.lengcs
 * @version 2012-7-24 3:35:25
 * 
 * <pre>
 *    ûʵ.
 * </pre>
 */
@Entity
@Table(name = "auth_user")
public class User {

    /** id. */
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private String     id;

    /** ½id. */
    // @Pattern(regexp = "[A-Za-z0-9_]{6,12}")
    @Column(name = "login_id")
    private String     loginId;

    /** . */
    private String     name;

    /** . */
    private String     password;

    /** . */
    @Column(name = "job_number")
    private String     jobNumber;

    /** ̶绰. */
    private String     phone;

    /** ֻ. */
    private String     mobile;

    /** . */
    private String     email;

    /** . */
    @Column(name = "post_code")
    private String     postCode;

    /** ַ. */
    private String     address;

    /** ״̬. */
    private Integer    status;

    /** ʱ. */
    @Column(name = "create_time")
    private Date       createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date       modifiedTime;

    /** . */
    private Integer    ordering;

    /**  */
    @ManyToOne
    @JoinColumn(name = "department_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Department department;

    /** . */
    private String     description;

    public User(){
    }

    public User(String id){
        this.id = id;
    }

    public User(String id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
