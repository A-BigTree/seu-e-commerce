package cn.seu.cs.eshop.api.controller.common;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.cache.ImageIdGenerateService;
import cn.seu.cs.eshop.api.component.SftpUtil;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static cn.seu.cs.eshop.api.constants.ApiConstants.DEFAULT_SUFFIX;
import static cn.seu.cs.eshop.api.constants.ApiConstants.IMAGE_ESHOP_PREFIX;
import static cs.seu.cs.eshop.common.sdk.util.TimeUtils.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@RestController
@RequestMapping("/file/manage")
@Slf4j
public class EshopFileController {
    @Resource
    SftpUtil sftp;
    @Resource
    ImageIdGenerateService imageIdGenerateService;

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @PostMapping("/image/upload")
    public BaseResponse uploadImage(@RequestParam("image") MultipartFile photo,
                                    @RequestParam("prefix") String prefix) {
        try {
            String fileName = photo.getOriginalFilename();
            String suffix = DEFAULT_SUFFIX;
            if (fileName != null && !StringUtils.isEmpty(fileName.substring(fileName.lastIndexOf(".")))) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            Long time = getCurrentTime();
            String file = time + imageIdGenerateService.generateUniqueId() + suffix;
            String result = convertString(time, DATE_FORMAT) + "/" + prefix + "/";
            InputStream image = photo.getInputStream();
            sftp.upload(IMAGE_ESHOP_PREFIX + result, file, image);
            return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, result + file);
        } catch (Exception e) {
            log.error("Image update error. e: ", e);
            return ResponseBuilderUtils.buildFailResponse(BaseResponse.class, "图片上传失败", null);
        }
    }
}
