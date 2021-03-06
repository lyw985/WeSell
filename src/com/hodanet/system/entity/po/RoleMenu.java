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
 * @version 2012-8-5 9:40:37
 * 
 * <pre>
 * ɫ˵ʵ
 * </pre>
 */
@Entity
@Table(name = "auth_role_menu")
public class RoleMenu {

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

    /** ɫid. */
    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Role    role;

    /** ˵id. */
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Menu    menu;

    /** . */
    private String  description;

    public RoleMenu(){
    }

    public RoleMenu(String id){
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
