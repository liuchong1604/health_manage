package com.health.upload.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/15 16:20
 */
public class UploadUtils {
    /**
     * 生成文件夹名字
     * @param ossProjectParentFolder
     * @return
     */
    public static String generateFolderNameByDate(String ossProjectParentFolder){
        return ossProjectParentFolder + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 生成文件名字
     * @param originFileName
     * @return
     */
    public static String generateFileName(String originFileName){
        String extension = "";
        if(originFileName.contains(".")){
            extension = originFileName.substring(originFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString().replaceAll("-","") + extension;
    }

    public static void uploadStringFile(String endPoint, String accessKeyId, String accessKeySecret, String folderName, String fileName, String bucketName, InputStream inputStream){
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        String objectName = folderName + "/" + fileName;
        ossClient.putObject(bucketName,objectName,inputStream);
        ossClient.shutdown();
    }
}
