package cn.com.basic.controller;

import cn.com.basic.upload.UploadFileService;
import org.quartz.DateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by ZhaiJiaYi on 2017/2/20.
 */
@RestController
public class UploadFileController {
    @Autowired
    UploadFileService uploadFileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,produces = "text/plain")
    public void  upload(@RequestBody MultipartFile file)  throws Exception {
        uploadFileService.upload(file);
    }
}
