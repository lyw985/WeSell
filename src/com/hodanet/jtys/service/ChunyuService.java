package com.hodanet.jtys.service;

import java.io.File;

import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.entity.po.JtysUser;

/**
 * @anthor lyw
 * @version 2014-9-29 2:00:51
 */
public interface ChunyuService {

    public String uploadFile(String type, File file);

    public void createVip(JtysUser jtysUser);

    public void createProblem(JtysCommunicate jtysCommunicate);
}
