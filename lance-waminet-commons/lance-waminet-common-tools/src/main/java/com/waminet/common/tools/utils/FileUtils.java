

package com.waminet.common.tools.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class FileUtils {

    /**
     * 文件对象转换
     *
     * @param file
     * @return
     */
    public static MultipartFile toMultipartFile(File file) throws IOException {
        String fieldName = "file";  //重点  这个名字需要和你对接的MultipartFil的名称一样
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, "multipart/form-data", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        FileInputStream fis = new FileInputStream(file);
        OutputStream os = item.getOutputStream();
        while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        os.close();
        fis.close();

        return new CommonsMultipartFile(item);
    }

    /**
     * OSS文件下载
     *
     * @param url
     * @param targetFilePath
     */
    public static void download(String url, String targetFilePath) throws IOException {
        // 创建目录及文件
        File targetFile = new File(targetFilePath);
        // 目录文件不存在的场合
        if (!targetFile.getParentFile().exists()) {
            // 创建目录
            targetFile.getParentFile().mkdirs();
        }
        // 临时文件不存在的场合
        if (!targetFile.exists()) {
            // 创建文件
            targetFile.createNewFile();
        }

        // 建立连接
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        // 连接指定的资源
        connection.connect();

        // 获取网络输入流
        InputStream inputStream = connection.getInputStream();
        // 缓冲输入流
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // 写入到文件
        FileOutputStream outputStream = new FileOutputStream(targetFile);
        // 缓冲输出流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // 设置缓存区大小
        byte[] buf = new byte[4096];
        // 读取文件
        int length = bufferedInputStream.read(buf);
        // 写入目标文件
        while (length != -1) {
            // 写入文件
            bufferedOutputStream.write(buf, 0, length);
            // 读取文件
            length = bufferedInputStream.read(buf);
        }

        // 关闭缓冲输出流
        bufferedOutputStream.close();
        // 关闭缓冲输入流
        bufferedInputStream.close();
        // 关闭连接
        connection.disconnect();
    }

    /**
     * 获取临时文件目录
     *
     * @param filePath
     * @return
     */
    public static String getLocalDirPath(String filePath) {
        // 系统名称
        String OS = System.getProperty("os.name").toLowerCase();

        // WNT
        if (OS.indexOf("windows") >= 0) {
            return "C:\\upload\\" + filePath;
        }
        // LINUX
        else {
            return "/usr/local/waybill/upload/" + filePath;
        }
    }

    /**
     * 获取临时文件目录
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static String getLocalFilePath(String filePath, String fileName) {
        // 系统名称
        String OS = System.getProperty("os.name").toLowerCase();

        // WNT
        if (OS.indexOf("windows") >= 0) {
            return "C:\\upload\\" + filePath + "\\" + fileName;
        }
        // LINUX
        else {
            return "/usr/local/waybill/upload/" + filePath + "\\" + fileName;
        }
    }

    /**
     * 获取临时文件名字
     *
     * @param source
     * @return
     */
    public static String getFileName(String source, Long mallId) {
        // 当前日期
        String currentDate = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_DIGITAL);

        // 生成6位随机数
        int random = (int) ((Math.random() * 9 + 1) * 100000);

        return source + "_" + mallId + "_" + currentDate + "_" + random + ".xls";
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void delete(String filePath) {
        File file = new File(filePath);

        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        String url = " https://badger-waybill.oss-cn-shanghai.aliyuncs.com/20211105/b82b9085fd6f476e8b1ef90c4a82ea11.xlsx";
        String localFileName = getFileName("pdd_waybill_statement", 111111L);
        String localFilePath = FileUtils.getLocalFilePath("pdd_waybill_statement_import", localFileName);

        download(url, localFilePath);

    }

}
