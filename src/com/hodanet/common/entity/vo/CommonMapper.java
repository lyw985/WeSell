package com.hodanet.common.entity.vo;

/**
 * @author lance.lengcs
 * @version 2012-7-30 10:57:32
 * 
 * <pre>
 * ȫֳӳ
 * </pre>
 */
public class CommonMapper {

    /** root url */
    private String rootPath;

    /** index url */
    private String indexPath;

    private String defaultPhotoPath;

    /** title */
    private String title;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public String getDefaultPhotoPath() {
        return defaultPhotoPath;
    }

    public void setDefaultPhotoPath(String defaultPhotoPath) {
        this.defaultPhotoPath = defaultPhotoPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
