package com.hodanet.jtys.entity.po;

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
@Table(name = "jtys_user_content")
public class JtysUserContent {

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
    private JtysContent jtysContent;

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

    public JtysContent getJtysContent() {
        return jtysContent;
    }

    public void setJtysContent(JtysContent jtysContent) {
        this.jtysContent = jtysContent;
    }

}
