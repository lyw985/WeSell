package com.hodanet.system.entity.po;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author lance.lengcs
 * @version 2012-8-4 6:56:55
 * 
 * <pre>
 * ˵ʵ
 * </pre>
 */
@Entity
@Table(name = "auth_menu")
public class Menu {

    /** id. */
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private String     id;

    /** . */
    private String     name;

    /** ״̬. */
    private Integer    status;

    /** ʱ. */
    @Column(name = "create_time")
    private Date       createTime;

    /** ޸ʱ. */
    @Column(name = "modified_time")
    private Date       modifiedTime;

    /** ˵ַ. */
    private String     address;

    /** . */
    private String     description;

    /** ˵ID. */
    @Column(name = "parent_id")
    private String     parentId;

    /** ϵͳ */
    @ManyToOne
    @JoinColumn(name = "module_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Module     module;

    /** . */
    private Integer    ordering;

    /** Ӳ˵. */
    @Transient
    private List<Menu> childMenus = null;

    public Menu(){
    }

    public Menu(String id){
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }

}
