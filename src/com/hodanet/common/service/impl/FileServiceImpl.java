package com.hodanet.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.po.HodanetFile;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.exception.HodanetFileException;
import com.hodanet.common.service.FileService;
import com.hodanet.common.spring.SpringContextUtil;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.User;

/**
 * @author lance.lengcs
 * @version 2012-8-20 5:39:43
 * 
 * <pre>
 * FileServiceʵ
 * </pre>
 */
public class FileServiceImpl extends AbstractDaoService implements FileService {

    /** ļĿ¼. */
    @Value("#{commonProperties['fileService.basePath']}")
    private String basePath = "";

    /**
     * ʼ.
     * 
     * @throws IOException .
     */
    @PostConstruct
    public void init() throws IOException {
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * UUID.
     * 
     * @return UUID
     */
    private String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * ȡļչ.
     * 
     * @param fileName ļ
     * @return ļչ
     */
    private String getFileType(String fileName) {
        int i = fileName.lastIndexOf('.');
        String fileType = fileName.substring(i);
        return fileType;
    }

    /**
     * ر.
     * 
     * @param inputStream .
     */
    private void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new HodanetFileException("رļ", e);
            }
        }
    }

    /**
     * ر.
     * 
     * @param outputStream .
     */
    private void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new HodanetFileException("رļ", e);
            }
        }
    }

    @Override
    public String saveFile(byte[] buffer, String fileName, String remark) {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(buffer);
            return saveFile(inputStream, fileName, remark);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HodanetFileException("ļʧ!", e);
        } finally {
            closeInputStream(inputStream);
        }
    }

    @Override
    public String saveFile(InputStream inputStream, String fileName, String remark) {
        String id = generateUUID();
        String fileType = getFileType(fileName);
        HodanetFile hodanetFile = new HodanetFile();
        hodanetFile.setId(id);
        hodanetFile.setName(fileName);
        hodanetFile.setRemark(remark);
        hodanetFile.setType(fileType);
        // HodanetFile.setLength(buffer.length);
        String userId = (String) SpringContextUtil.getSessionAttr(PermissionConstants.CONSTANT_PARAM_USER_ID);
        User user = getDao().get(User.class, userId);
        hodanetFile.setUser(user);
        hodanetFile.setPath(id);
        getDao().save(hodanetFile);

        FileOutputStream outputStream = null;
        try {
            File file = new File(basePath, id);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            long totalCount = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                totalCount += count;
                outputStream.write(buffer, 0, count);
                outputStream.flush();
            }
            // ôС
            hodanetFile.setLength(totalCount);
            // ´С
            getDao().update(hodanetFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HodanetFileException("ļʧ!", e);
        } finally {
            closeOutputStream(outputStream);
        }
        return id;
    }

    @Override
    public String saveFile(File file, String remark) {
        if (file == null || !file.exists()) {
            throw new HodanetFileException("ļ");
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String id = saveFile(inputStream, file.getName(), remark);
            return id;
        } catch (FileNotFoundException e) {
            throw new HodanetFileException("ļ", e);
        } finally {
            closeInputStream(inputStream);
        }
    }

    @Override
    public String saveFile(String filePath, String remark) {
        if (StringUtil.isBlank(filePath)) {
            throw new HodanetFileException("ļַΪ");
        }
        return saveFile(new File(filePath), remark);
    }

    @Override
    public void deleteFile(String id) {
        getDao().delete(HodanetFile.class, id);
        File file = new File(basePath, id);
        if (file.exists()) {
            if (!file.delete()) {
                throw new HodanetFileException("ɾļʧ");
            }
        }
    }

    @Override
    public void updateFile(HodanetFile file) {
        getDao().update(file);
    }

    @Override
    public void updateFile(String id, byte[] buffer, String fileName, String remark) {
        String fileType = getFileType(fileName);
        HodanetFile hodanetFile = getFile(id);
        hodanetFile.setName(fileName);
        hodanetFile.setRemark(remark);
        hodanetFile.setType(fileType);
        hodanetFile.setLength(Long.valueOf(buffer.length));
        hodanetFile.setUser(getDao().get(User.class,
                                         (String) SpringContextUtil.getSessionAttr(PermissionConstants.CONSTANT_PARAM_USER_ID)));
        getDao().save(hodanetFile);

        FileOutputStream outputStream = null;
        try {
            File file = new File(basePath, id);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.flush();
        } catch (Exception e) {
            throw new HodanetFileException("ļʧ!", e);
        } finally {
            closeOutputStream(outputStream);
        }
    }

    @Override
    public void updateFile(String id, InputStream inputStream, String fileName, String remark) {
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, count);
            }
            updateFile(id, outputStream.toByteArray(), fileName, remark);
        } catch (IOException e) {
            throw new HodanetFileException("ȡļ", e);
        } finally {
            closeOutputStream(outputStream);
        }
    }

    @Override
    public void updateFile(String id, File file, String remark) {
        if (file == null || !file.exists()) {
            throw new HodanetFileException("ļ");
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            updateFile(id, inputStream, file.getName(), remark);
        } catch (FileNotFoundException e) {
            throw new HodanetFileException("ļ", e);
        } finally {
            closeInputStream(inputStream);
        }
    }

    @Override
    public void updateFile(String id, String filePath, String remark) {
        if (StringUtil.isBlank(filePath)) {
            throw new HodanetFileException("ļַΪ");
        }
        updateFile(id, new File(filePath), remark);
    }

    @Override
    public HodanetFile getFile(String id) {
        return getDao().get(HodanetFile.class, id);
    }

    @Override
    public List<HodanetFile> getFile(String... ids) {
        List<HodanetFile> HodanetFiles = new ArrayList<HodanetFile>();
        for (String id : ids) {
            HodanetFiles.add(getFile(id));
        }
        return HodanetFiles;
    }

    @Override
    public List<HodanetFile> getFileByName(String fileName) {
        return getDao().queryHql("from HodanetFile where name like '%" + fileName + "%' order by createTime desc");
    }

    @Override
    public PageData<HodanetFile> getFileByName(String fileName, PageData<HodanetFile> pageData) {
        return getDao().queryHqlPageData("from HodanetFile where name like '%" + fileName
                                                 + "%' order by createTime desc", pageData);
    }

    @Override
    public PageData<HodanetFile> getFiles(PageData<HodanetFile> pageData) {
        return getDao().queryHqlPageData("from HodanetFile order by createTime desc", pageData);
    }

    @Override
    public byte[] getFileByte(String id) {
        InputStream inputStream = getFileStream(id);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count = 0;
        try {
            while ((count = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new HodanetFileException(e);
        } finally {
            closeInputStream(inputStream);
            closeOutputStream(outputStream);
        }
    }

    @Override
    public File getFileOnDisk(String id) {
        File file = new File(basePath, id);
        if (!file.exists()) {
            throw new HodanetFileException("ļڣ");
        }
        return file;
    }

    @Override
    public InputStream getFileStream(String id) {
        File file = getFileOnDisk(id);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new HodanetFileException(e);
        }
    }

    @Override
    public List<HodanetFile> getFiles() {
        return getDao().queryHql("from HodanetFile order by createTime desc");
    }

    @Override
    public String saveRemoteFile(byte[] buffer1, String fileName, String remark, String userId) {

        ByteArrayInputStream inputStream = null;
        String id = "";
        try {
            inputStream = new ByteArrayInputStream(buffer1);
            id = generateUUID();
            String fileType = getFileType(fileName);
            HodanetFile hodanetFile = new HodanetFile();
            hodanetFile.setId(id);
            hodanetFile.setName(fileName);
            hodanetFile.setRemark(remark);
            hodanetFile.setType(fileType);
            // HodanetFile.setLength(buffer.length);
            hodanetFile.setUser(getDao().get(User.class, userId));
            hodanetFile.setPath(id);
            getDao().save(hodanetFile);

            FileOutputStream outputStream = null;

            File file = new File(basePath, id);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            long totalCount = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                totalCount += count;
                outputStream.write(buffer, 0, count);
                outputStream.flush();
            }
            // ôС
            hodanetFile.setLength(totalCount);
            // ´С
            getDao().update(hodanetFile);

        } catch (Exception e) {
            throw new HodanetFileException("ļʧ!", e);
        } finally {
            closeInputStream(inputStream);
        }

        return id;
    }

}
