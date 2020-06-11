package com.health.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/15 16:15
 */
public interface IUploadService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file);
}
