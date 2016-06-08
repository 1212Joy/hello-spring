package cn.com.basic;

import cn.com.basic.dto.HelloWorldDto;
import cn.com.basic.mq._01_helloworld.RabbitMQRecv;
import cn.com.basic.mq._01_helloworld.RabbitMQSend;
import cn.com.basic.mq._02_workqueues.NewTask;
import cn.com.basic.mq._02_workqueues.Work;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhaijiayi on 2016/3/10.
 */
public class SimpleTest {

    @Test
    public void randomDouble() throws Exception {
        
        java.util.Random random=new java.util.Random();
        int randomCode = random.nextInt(99)+1;
        double a = randomCode*0.01;
        System.out.println("随机生成： "+a);
    }

    @Test
    public void parseJsonString() throws Exception {
        String job_key  = String.format("job_%s","aaa");
        System.out.println("数值为: "+job_key);
    }

    @Test
    public void send() throws Exception {
        RabbitMQSend rabbitMQSend1 = new RabbitMQSend("hello1","hello baby1");
        rabbitMQSend1.sendExcute();

    }
    @Test
    public void receive() throws Exception {
        RabbitMQRecv rabbitMQRecv = new RabbitMQRecv("hello1");
        rabbitMQRecv.recvExcute();

    }
    @Test
    public void taskTest() throws Exception {
        String [] message ={"hhh","First message.","Second message..","Third message...","Fourth message....","Fifth message....."};
        NewTask newTask = new NewTask("workqueue",message);
        newTask.sendExcute();

    }
    @Test
    public void work1() throws Exception {
        Work work1 = new Work("task");
        work1.recvExcute();
    }
    @Test
    public void work2() throws Exception {
        Work work2 = new Work("task");
        work2.recvExcute();
    }
    @Test
    public void stringArray() throws Exception {
        String a = "First message.Second message Third message Fourth message Fifth  message";
        for (char ch: a.toCharArray()) {
            if (ch == '.') System.out.println(ch);
        }

    }
    @Test
    public  void genTxRef() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timeString = sdf.format(Calendar.getInstance().getTime());
        String result = timeString;//2016052510311620766655
        for (int i = 0; i < 10; i++) {
            logger.info("第{}次，{}", i, result);
        }
    }
    @Test
    public void splitString(){
        String profit = "科目%交易行为%金额（元）%交易时间%交易状态%贷款申请编号%理财标的标号%用户账户%";
        String tradAction = new String("公司%记账日期%业务日期%会计期间%凭证字%凭证号%分录号%摘要%科目%币种%汇率%方向%原币金额%数量%单价%借方金额%贷方金额%制单人%过账人%审核人%附件数量%过账标记%机制凭证模块%删除标记%凭证序号%单位%参考信息%是否有现金流量%%现金流量标记%业务编号%结算方式%结算号%辅助账摘要%核算项目1%编码1%名称1%核算项目2%编码2%名称2%核算项目3%编码3%名称3%核算项目4%编码4%名称4%核算项目5%编码5%名称5%核算项目6%编码6%名称6%核算项目7%编码7%名称7%核算项目8%%编码8%名称8%发票号%换票证号%客户%费用类别%收款人%物料%账务组织%供应商%辅助账业务日期%到期日");
        String apply = "";
        String aaa = "218737%1338%韦东%中国民生银行%广东省-深圳市-市民中心支行%622622063650505%110.58 %110.58 %100.00 %2015/3/9 10:58:21%";

        String a[] = aaa.split("%");
        System.out.println(a.length);
            for(String tem:a){
                System.out.println(tem.trim());
            }
    }
    @Test
    public  void testExcel() {
       String excelTitle = new String("标识%");
        String arrayTitleName[] = excelTitle.split("%");
        int row = 0;
        try {

            // 创建Execl工作薄
            HSSFWorkbook book = new HSSFWorkbook();
            // 在Excel工作簿中建一工作表，其名为缺省值 ，可以指定也可以不指定
            HSSFSheet sheet = book.createSheet();
            //创建sheet表中的行
            HSSFRow top = sheet.createRow(row);
            //第0行输出字段名同时第一行输出第一条记录值
            for(int tem =0;tem<arrayTitleName.length;tem++) {
                //建立单无格
                HSSFCell topc = top.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                topc.setCellValue(arrayTitleName[tem]);
            }
            for(int row2 = 2;row2<5;row2++){
                HSSFRow reco = sheet.createRow(row2+1);
                HSSFCell recoc = reco.createCell(2,HSSFCell.CELL_TYPE_STRING);
                recoc.setCellValue("hahaha");
            }


            // 新建一输出文件流
            String path = "E:\\";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String timeString = sdf.format(Calendar.getInstance().getTime());
            String fileName = "poiGoods_"+timeString+".xls";
            FileOutputStream output = new FileOutputStream(path+fileName);
            // 把相应的Excel 工作簿写入到文件中
            book.write(output);
            //即清空缓冲区数据
            output.flush();
            // 操作结束，关闭文件
            output.close();

            System.out.println("文件生成成功！"+fileName);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("写入文件失败！");
            e.printStackTrace();
        }
    }
    @Test
    public void formatDate(){
        Date nowtime = new Date();
        logger.info(nowtime.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        logger.info(simpleDateFormat.format(nowtime));
      simpleDateFormat.format(nowtime);

    }
    @Test
    public void testSetMethodDefaultValue(){
        HelloWorldDto helloWorldDto = new HelloWorldDto("girl");
        helloWorldDto.setSex("aaaa");
        logger.info(formatTime(new Date(),"yyyy/MM/dd"));

    }

    public static String formatTime(Date time,String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(time);

    }
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}