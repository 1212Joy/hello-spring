package cn.com.basic.upload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhaiJiaYi on 2017/2/20.
 */
@Service
public class UploadFileService {


    public String upload(MultipartFile file){
        String orignName = file.getOriginalFilename();
        String type = StringUtils.substringAfter(orignName,".");

        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String savePath = new StringBuilder("F:\\upload\\").append(fileName).append(".").append(type).toString();

        try {

            file.transferTo(new File(savePath));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }
}
