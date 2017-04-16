package cn.com.basic.simple;


import cn.com.basic.dto.BusinessReceiptsDTO;
import cn.com.basic.dto.PlatformRecordReservedField;
import cn.com.basic.mq._01_helloworld.RabbitMQRecv;
import cn.com.basic.mq._01_helloworld.RabbitMQSend;
import cn.com.basic.mq._02_workqueues.NewTask;
import cn.com.basic.mq._02_workqueues.Work;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaijiayi on 2016/3/10.
 */
public class SpringTest {

    @Test
    public void randomDouble() throws Exception {

        Random random=new Random();
        int randomCode = random.nextInt(99)+1;
        double a = randomCode*0.01;
        System.out.println("随机生成： "+a);
    }


}