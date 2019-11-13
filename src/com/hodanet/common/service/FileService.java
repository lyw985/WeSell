package com.hodanet.common.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.hodanet.common.entity.po.HodanetFile;
import com.hodanet.common.entity.vo.PageData;

/**
 * @author lance.lengcs
 * @version 2012-8-20 5:36:29
 * 
 * <pre>
 * ļӿ
 * </pre>
 */
public interface FileService {

    /**
     * ļ.
     * 
     * @param buffer 
     * @param fileName ļ
     * @param remark ע
     * @return ID
     */
    public String saveFile(byte[] buffer, String fileName, String remark);

    /**
     * ļ.
     * 
     * @param inputStream 
     * @param fileName ļ
     * @param remark ע
     * @return ID
     */
    public String saveFile(InputStream inputStream, String fileName, String remark);

    /**
     * ļ.
     * 
     * @param file ļ
     * @param remark ע
     * @return ID
     */
    public String saveFile(File file, String remark);

    /**
     * ļ.
     * 
     * @param filePath ļ·
     * @param remark ע
     * @return ID
     */
    public String saveFile(String filePath, String remark);

    /**
     * ɾļ.
     * 
     * @param id ļID
     */
    public void deleteFile(String id);

    /**
     * ޸ļϢ.
     * 
     * @param file ļʵ
     */
    public void updateFile(HodanetFile file);

    /**
     * ޸ļ.
     * 
     * @param id ID
     * @param buffer 
     * @param fileName ļ
     * @param remark ע
     */
    public void updateFile(String id, byte[] buffer, String fileName, String remark);

    /**
     * ޸ļ.
     * 
     * @param id ID
     * @param inputStream 
     * @param fileName ļ
     * @param remark ע
     */
    public void updateFile(String id, InputStream inputStream, String fileName, String remark);

    /**
     * ޸ļ.
     * 
     * @param id ID
     * @param file ļ
     * @param remark ע
     */
    public void updateFile(String id, File file, String remark);

    /**
     * ޸ļ.
     * 
     * @param id ID
     * @param filePath ļ·
     * @param remark ע
     */
    public void updateFile(String id, String filePath, String remark);

    /**
     * ȡļϢ.
     * 
     * @param id ID
     * @return FPIļ
     */
    public HodanetFile getFile(String id);

    /**
     * ȡļϢ.
     * 
     * @param ids IDs
     * @return FPIļб
     */
    public List<HodanetFile> getFile(String... ids);

    /**
     * ļȡļб(ģѯ).
     * 
     * @param fileName ļ
     * @return ļб
     */
    public List<HodanetFile> getFileByName(String fileName);

    /**
     * ļҳȡļб(ģѯ).
     * 
     * @param fileName ļ
     * @param pageData ҳϢ
     * @return ҳ
     */
    public PageData<HodanetFile> getFileByName(String fileName, PageData<HodanetFile> pageData);

    /**
     * ҳȡļ.
     * 
     * @param pageData ҳϢ
     * @return ҳ
     */
    public PageData<HodanetFile> getFiles(PageData<HodanetFile> pageData);

    /**
     * ȡļ.
     * 
     * @return ҳ
     */
    public List<HodanetFile> getFiles();

    /**
     * ȡļֽ.
     * 
     * @param id ID
     * @return ļֽ
     */
    public byte[] getFileByte(String id);

    /**
     * ȡļ.
     * 
     * @param id ID
     * @return java.io.File
     */
    public File getFileOnDisk(String id);

    /**
     * ȡļ.
     * 
     * @param id ID
     * @return ļ
     */
    public InputStream getFileStream(String id);

    /**
     * Զļ
     * 
     * @param buffer 
     * @param fileName ļ
     * @param remark ע
     * @return ID
     */
    public String saveRemoteFile(byte[] buffer, String fileName, String remark, String userId);

}
