package com.hodanet.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtil {

    private static final int   BUFFER_SIZE    = 1024;

    public static final String FILE_TEMP_PATH = "e:\\temp_file";

    public static void copy(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int size = 0;
            while ((size = in.read(buffer)) > 0) {
                out.write(buffer, 0, size);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        }
    }

    public static void copy(byte[] bs, OutputStream out) throws IOException {
        ByteArrayInputStream bai = new ByteArrayInputStream(bs);
        copy(bai, out);
    }

    public static void copy(File srcFile, File dstFile) throws IOException {
        copy(new FileInputStream(srcFile), new FileOutputStream(dstFile));
    }

    public static void copy(String srcPath, String dstPath) throws IOException {
        copy(new FileInputStream(srcPath), new FileOutputStream(dstPath));
    }

    public static void moveFolder(String srcPath, String dstPath) throws IOException {
        copyFolder(srcPath, dstPath);
        delFolder(srcPath);
    }

    public static void copyFolder(String oldPath, String newPath) throws IOException {
        if (!new File(newPath).exists()) {
            new File(newPath).mkdirs();
        }
        if (!oldPath.endsWith(File.separator)) {
            oldPath += File.separator;
        }
        if (!newPath.endsWith(File.separator)) {
            newPath += File.separator;
        }
        File src = new File(oldPath);
        String[] filePaths = src.list();
        File temp = null;
        for (int i = 0; i < filePaths.length; i++) {
            temp = new File(oldPath + filePaths[i]);
            if (temp.isFile()) {
                copy(temp, new File(newPath + temp.getName()));
            }
            if (temp.isDirectory()) {
                copyFolder(oldPath + filePaths[i], newPath + filePaths[i]);
            }
        }
    }

    public static void copyFileToFolder(String oldPath, String newPath) throws IOException {
        if (!new File(newPath).exists()) {
            new File(newPath).mkdirs();
        }
        copy(new File(oldPath), new File(newPath + File.separator + new File(oldPath).getName()));
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void delAllFile(String path) {
        File file = new File(path);
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);
                delFolder(path + File.separator + tempList[i]);
            }
        }
    }

    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        File myFilePath = new File(folderPath);
        myFilePath.delete();
    }

    public static long getFolderStoreSize(File file) {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; flist != null && i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFolderStoreSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static boolean isEmpty(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (File tempFile : fileList) {
                    if (tempFile.isFile()) {
                        return false;
                    } else if (tempFile.isDirectory()) {
                        if (!isEmpty(tempFile)) {
                            return false;
                        }
                    }
                }
            } else {
                throw new RuntimeException("not a directory!!!!!");
            }
        }
        return true;
    }

    public static String getStrFormFile(File file) throws IOException {
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = null;
            while ((str = reader.readLine()) != null) {
                sb.append("'" + str + "',\n");
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static byte[] getByteArray(InputStream in) throws IOException {
        byte[] bs = null;
        try {
            bs = new byte[in.available()];
            in.read(bs);
        } finally {
            in.close();
        }
        return bs;
    }
    
    public static String getFileName(String dirPath, String suffix) {
        String fileName;
        File file;
        do {
            fileName = DateConverterUtil.format(DateConverterUtil.yyyyMMddHHmmss_SSS) + "." + suffix;
            file = new File(dirPath + File.separator + fileName);
        } while (file.exists());
        return fileName;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getStrFormFile(new File("123456.txt")));
    }
}
