package com.hodanet.common.entity.vo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lance.lengcs
 * @version 2012-7-23 11:45:32
 * 
 * <pre>
 *    װҳѯ.
 * </pre>
 */
public class PageData<T> {

    /**
     * ҳݶ.
     * 
     * @param <T> 
     * @param pageSize ÿҳ
     * @param pageNumber ҳ
     * @return ҳݶ
     */
    public static <T> PageData<T> createPagerData(int pageSize, int pageNumber) {
        PageData<T> data = new PageData<T>();
        data.setPageSize(pageSize);
        data.setPageNumber(pageNumber);
        return data;
    }

    /**
     * ҳݶ.
     * 
     * @param <T> 
     * @param request HTTP
     * @return ҳݶ
     */
    public static <T> PageData<T> createPagerData(HttpServletRequest request) {
        PageData<T> data = new PageData<T>();
        if (request.getParameter("pageSize") != null) {
            data.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
        }
        if (request.getParameter("pageNumber") != null) {
            data.setPageNumber(Integer.valueOf(request.getParameter("pageNumber")));
        }
        return null;
    }

    /**
     * ܼ¼.
     */
    private long    total      = 0;
    /**
     * ÿҳ.
     */
    private int     pageSize   = 20;
    /**
     * ҳ,1ʼ.
     */
    private int     pageNumber = 1;
    /**
     * ݼ.
     */
    private List<T> data       = null;

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * ȡ.
     * 
     * @return 
     */
    public int getEndIndex() {
        return getPageNumber() * getPageSize() - 1;
    }

    /**
     * ȡʼ.
     * 
     * @return ʼ
     */
    public int getBeginIndex() {
        return getEndIndex() - getPageSize() + 1;
    }
}
