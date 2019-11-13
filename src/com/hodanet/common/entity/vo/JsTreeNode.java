package com.hodanet.common.entity.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-8-1 2:52:48
 * 
 * <pre>
 * JSTreeJSONṹ
 * </pre>
 */
public class JsTreeNode {

    /** ڵ״̬-չ. */
    public static final String STATE_OPEN  = "open";
    /** ڵ״̬-۵. */
    public static final String STATE_CLOSE = "close";

    /**
     * JsTreeMAPתΪṹظڵ.
     * 
     * @param map keyΪڵIDvalueΪJsTreeڵ
     * @return ڵ㼯
     */
    public static List<JsTreeNode> buildTree(Map<String, JsTreeNode> map) {
        List<JsTreeNode> roots = new ArrayList<JsTreeNode>();
        for (Map.Entry<String, JsTreeNode> entry : map.entrySet()) {
            String parentId = entry.getValue().getParentId();
            if (StringUtil.isBlank(parentId) || "-1".equals(parentId)) {
                roots.add(entry.getValue());
            } else {
                if (map.containsKey(parentId)) {
                    JsTreeNode parent = map.get(parentId);
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<JsTreeNode>());
                    }
                    parent.getChildren().add(entry.getValue());
                }
            }
        }
        return roots;
    }

    /** node_title. */
    private String              data     = "";
    /** . */
    private Map<String, String> attr     = new HashMap<String, String>();
    /** "closed" or "open", defaults to "closed". */
    private String              state    = "closed";
    /** an array of child nodes objects . */
    private List<JsTreeNode>    children = null;
    /** icon. */
    private String              icon     = "";
    /** Ƿʾѡ. */
    private boolean             checkbox = false;
    /** ڵID. */
    @JsonIgnore
    private String              parentId = "";

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the attr
     */
    public Map<String, String> getAttr() {
        return attr;
    }

    /**
     * @param attr the attr to set
     */
    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the children
     */
    public List<JsTreeNode> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<JsTreeNode> children) {
        this.children = children;
    }

    /**
     * attr.
     * 
     * @param key .
     * @param value .
     */
    public void addAttribute(String key, String value) {
        this.attr.put(key, value);
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the showCheckbox
     */
    public boolean getCheckbox() {
        return checkbox;
    }

    /**
     * @param showCheckbox the showCheckbox to set
     */
    public void setCheckbox(boolean showCheckbox) {
        this.checkbox = showCheckbox;
    }
}
