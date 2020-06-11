package com.health.upload.service.impl;

import com.health.upload.service.IUploadService;
import com.health.upload.utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/15 16:16
 */
@Service
public class UploadServiceImpl implements IUploadService {

    private static List<String> CONTENT_TYPE = Arrays.asList("image/gif","image/jpeg");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Value(value = "${oss.endpoint}")
    public String endPoint;
    @Value(value = "${oss.accessKeyId}")
    private String accessKeyId;

    @Value(value = "${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value(value = "${oss.bucketName}")
    private String bucketName;

    @Value(value = "${oss.bucket.domain}")
    private String bucketDomain;

    @Value(value = "${oss.project.parent.folder}")
    private String ossProjectParentFolder;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {
        if(file.isEmpty()){
            LOGGER.info("文件为空");
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        try {
            String contentType = file.getContentType();
            if(!CONTENT_TYPE.contains(contentType)){
                LOGGER.info("文件类型不合法: {}",originalFilename);
                return null;
            }
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                LOGGER.info("文件内容不合法:{}",originalFilename);
                return null;
            }
            String fileName = UploadUtils.generateFileName(originalFilename);
            String folderName = UploadUtils.generateFolderNameByDate(ossProjectParentFolder);
            InputStream inputStream = file.getInputStream();
            UploadUtils.uploadStringFile(endPoint,accessKeyId,accessKeySecret,folderName,fileName,bucketName,inputStream);
            String picturePath = "http://image.health.com/" + folderName + "/" +fileName;
            return picturePath;
        } catch (IOException e) {
            LOGGER.info("服务器内部错误: {}",originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
