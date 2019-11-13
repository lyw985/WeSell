package com.hodanet.jtys.service;

import com.hodanet.common.entity.vo.PageData;
import com.hodanet.jtys.entity.po.JtysDoctor;

public interface JtysDoctorService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public JtysDoctor getJtysDoctorById(String id);

    /**
     * ҳѯ
     * 
     * @param pageData
     * @return
     */
    public PageData<JtysDoctor> getJtysDoctorByPage(PageData<JtysDoctor> pageData, JtysDoctor jtysDoctor);

    /**
     * 
     * 
     * @param ResDept
     * @return
     */
    public JtysDoctor saveJtysDoctor(JtysDoctor jtysDoctor);

    /**
     * ɾ
     * 
     * @param ids
     */
    public void deleteJtysDoctor(Integer[] ids);


}
