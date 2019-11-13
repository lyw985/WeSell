package com.hodanet.blsh.entity.po;

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
 * 便利生活微信关注用户和内容关联
 * </pre>
 */
@Entity
@Table(name = "blsh_user_content")
public class BlshUserContent {

    /** 主键 */
    @Id
    @GeneratedValue(generator = "incrementGenerator")
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
    private Integer     id;

    /** 名称 */
    private String      open_id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private BlshContent blshContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public BlshContent getBlshContent() {
        return blshContent;
    }

    public void setBlshContent(BlshContent blshContent) {
        this.blshContent = blshContent;
    }

}
