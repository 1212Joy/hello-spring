package cn.com.basic;

import cn.com.basic.utils.JsonUtils;
import cn.com.basic.dto.BusinessReceiptsDTO;
import cn.com.basic.dto.HelloWorldDto;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
            HSSFName name =  book.createName();
            name.setNameName("设计任务即可");
            logger.info("name::: "+name.getNameName());
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
            String fileName = book.getNameName(0)+".xls";//"poiGoods_"+timeString+".xls";
            System.out.println("文件生成成功！"+fileName);
           /* // 新建一输出文件流
            String path = "E:\\";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String timeString = sdf.format(Calendar.getInstance().getTime());
            String fileName = work.getNameName()+".xls";//"poiGoods_"+timeString+".xls";
            FileOutputStream output = new FileOutputStream(path+fileName);
            // 把相应的Excel 工作簿写入到文件中
            book.write(output);
            //即清空缓冲区数据
            output.flush();
            // 操作结束，关闭文件
            output.close();

            System.out.println("文件生成成功！"+fileName);*/
        }catch (Exception e) {
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
   /* @Test
    public void testSetMethodDefaultValue(){
        HelloWorldDto helloWorldDto = new HelloWorldDto("girl");
        helloWorldDto.setSex("aaaa");
        logger.info(formatTime(new Date(),"yyyy/MM/dd"));

    }
    @Test
    public void testHashMap(){
       Map <String,HelloWorldDto>aa = new HashMap();
        HelloWorldDto helloWorldDto = new HelloWorldDto("girl");
        helloWorldDto.setName("aaaa");
        aa.put("1",helloWorldDto);
        HelloWorldDto helloWorldDto11 = aa.get("1");
        helloWorldDto11.setName("bbb");
        System.out.print(aa.get("1").getName());
    }

    @Test
    public void testExcelPOI(){
        HelloWorldDto helloWorldDto = new HelloWorldDto("girl");
        helloWorldDto.setSex("aaaa");
        logger.info(formatTime(new Date(),"yyyy/MM/dd"));

    }*/
    @Test
    public void testJsonToListObj() throws Exception{
        String jsonString = "[{\"invest\":0,\"marketingOffsetExpenses\":0,\"marketingInterestExpenses\":0,\"investorReceipts\":0,\"bizDate\":\"201601\"},{\"invest\":0,\"marketingOffsetExpenses\":0,\"marketingInterestExpenses\":0,\"investorReceipts\":0,\"bizDate\":\"201602\"},{\"invest\":26536601.00,\"marketingOffsetExpenses\":0.00,\"marketingInterestExpenses\":0.00,\"investorReceipts\":2014470.21,\"bizDate\":\"201603\"},{\"invest\":33841247.00,\"marketingOffsetExpenses\":74500.40,\"marketingInterestExpenses\":0.00,\"investorReceipts\":3988632.44,\"bizDate\":\"201604\"},{\"invest\":19030941.00,\"marketingOffsetExpenses\":60.00,\"marketingInterestExpenses\":1285.36,\"investorReceipts\":266860.63,\"bizDate\":\"201605\"},{\"invest\":943500.00,\"marketingOffsetExpenses\":30.00,\"marketingInterestExpenses\":2404.47,\"investorReceipts\":31661.84,\"bizDate\":\"201606\"}]";
        JavaType javaType = getCollectionType(ArrayList.class, BusinessReceiptsDTO.class);
            List<BusinessReceiptsDTO> lst =  (List<BusinessReceiptsDTO>)mapper.readValue(jsonString, javaType);
        logger.info("wanch!");

         }



    @Test
    public void testGetType(){
        Double aaa = new Double(1.22);
        Double bbb = new Double(1.55);
        //+
        //-
        //*
        // /   %
        List xList = new ArrayList();
        xList.add("ssss");
        xList.add(aaa ==null?new Double(0):aaa.doubleValue());
        xList.add( new BigDecimal(1.22));//.doubleValue()   class java.lang.Double
        for(int i=0;i<xList.size();i++){
            System.out.println(xList.get(i));
            /*        System.out.println(  xList.get(i).getClass().toString());
            if("class java.math.BigDecimal".equals(xList.get(i).getClass().toString())){
                System.out.println(((BigDecimal)xList.get(i)).doubleValue() );

            };*/
        }

    }

    public static String formatTime(Date time,String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(time);

    }
    @Test
    public void testExcute3() throws Exception {
        //Workbook book = poiExportExcel.excute3();
        //生成表格
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("业务收益表");
        sheet.autoSizeColumn(1, true);
        //样式
        HSSFCellStyle style1 = book.createCellStyle();    //表头： 18 加粗 下划线 居中显示
        HSSFCellStyle style2 = book.createCellStyle();   //加粗
        HSSFCellStyle style3 = book.createCellStyle();   //加粗  背景蓝色  居中显示
        HSSFFont font1 = book.createFont();
        HSSFFont font2 = book.createFont();
        font1.setUnderline(HSSFFont.U_SINGLE); //下划线
        font1.setFontHeightInPoints((short) 18); //字体大小
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style1.setFont(font1);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style2.setFont(font2);

        style3.setFont(font2);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setFillForegroundColor(HSSFColor.BLUE_GREY.index);// 设置背景色
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);





        //输出表头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        HSSFRow row1 =  sheet.createRow(0);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1 .setCellValue("业务收益表");//第一行  0,5 ： 业务收益表
        row1Cell1.setCellStyle(style1);
        HSSFRow row2 =  sheet.createRow(1);//第二行  0,end ： 编制单位:茂业金服
        HSSFCell row2Cell1 =  row2 .createCell(0);
        row2Cell1.setCellValue("编制单位:茂业金服");
        row2Cell1.setCellStyle(style2);


        HSSFRow row3 = sheet.createRow(2);//第三行
        row3.setRowStyle(style2);
        for(int i=1;i<12;i++){
            sheet.addMergedRegion(new CellRangeAddress(2, 3, i, i));
        }
        //B34 ~ K34  一、业务收入	居间服务费收入	取现费手续费收入	账户管理费收入	充值费收入	转让费收入	提前还款罚息收入	逾期滞纳金收入	催收管理费收入	会员费（每年）收入	二、业务支出
        String row3Context = "一、业务收入;居间服务费收入;取现费手续费收入;账户管理费收入;充值费收入;转让费收入;提前还款罚息收入;逾期滞纳金收入;催收管理费收入;会员费（每年）收入;二、业务支出";
        fillCellValue(1,row3Context,row3,style2);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 18, 18));  //S34 三、业务利润
        row3.createCell(18).setCellValue("三、业务利润");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 15));        //M3~P3  第三方支出
        row3.createCell(12).setCellValue("第三方支出");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 17));             //Q3~R3  平台支出
        row3.createCell(16).setCellValue("平台支出");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 19, 24));                 //T3~Y3  投资人
        row3.createCell(19).setCellValue("投资人");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 25, 29));       //Z3~AD3 借款方
        row3.createCell(25).setCellValue("借款方");
        int cellArray[] = {1,11,18,12,16,19,25};
        fillCellStyle(row3,cellArray,style3);
        HSSFRow row4 = sheet.createRow(3);//第四行
        HSSFCell row4Cell1 =  row4.createCell(0, HSSFCell.CELL_TYPE_STRING);
        row4Cell1 .setCellValue("日期");//A4
        row4Cell1.setCellStyle(style2);

        //M4~R4 充值费支出	取现费支出	认证费支出	其他支出	营销费用支出	营销费用支出
        String row4Context1 = "充值费支出;取现费支出;认证费支出;其他支出;营销费用支出;营销费用支出";
        fillCellValue(12,row4Context1,row4,style2);
        //T4~AD4 充值	投资	支出	收入	总收入	毛利率	借款金额	费用支出	利息支出	总支出	成本率
        String row4Context2 = "充值;投资;支出;收入;总收入;毛利率;借款金额;费用支出;利息支出;总支出;成本率";
        fillCellValue(19,row4Context2,row4,style2);
      /*  //循环数据
        List<ArrayList> formatList = new ArrayList();
        int row = 4;
        for (List record : formatList) {
            HSSFRow reco = sheet.createRow(row);
            for (int tem = 0; tem < record.size(); tem++) {
                 reco.createCell(tem, HSSFCell.CELL_TYPE_NUMERIC).setCellValue((Double)record.get(tem) );
            }
            row++;
        }*/

        //最后三行
        int allRows = sheet.getLastRowNum();
        HSSFRow lastRow3 = sheet.createRow(allRows+1);  //合计金额
        lastRow3.createCell(0).setCellValue("合计金额");
        HSSFCell testCell1 = lastRow3.createCell(1);
        HSSFCell testCell2 = lastRow3.createCell(2);
        HSSFCell testCell3 =  lastRow3.createCell(3, HSSFCell.CELL_TYPE_FORMULA);
        testCell1.setCellValue(0.2);
        testCell2.setCellValue(0.3);
        testCell3.setCellFormula("SUM("+"B5"+":"+"C5"+")");//合计金额     =SUM(B6:D6)
        HSSFRow lastRow2 = sheet.createRow(allRows+2);  //投资百分比
        lastRow2.createCell(0).setCellValue("投资百分比");
        lastRow2.setRowStyle(style2);

        HSSFCellStyle cellStylePervent = book.createCellStyle();
        cellStylePervent.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));

        HSSFCell testCell11 = lastRow2.createCell(1, HSSFCell.CELL_TYPE_FORMULA);
        testCell11.setCellFormula( "B5"+"/"+"$D$5");//合计金额     =SUM(B6:D6)
        testCell11.setCellStyle(cellStylePervent);

        HSSFRow lastRow1 = sheet.createRow(allRows+3);  //茂业百分比
        lastRow1.createCell(0).setCellValue("茂业百分比");
        lastRow1.setRowStyle(style2);


        String path = "E:\\";
        String fileName = "aaaaaa_"   + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls";
        FileOutputStream output = new FileOutputStream(path + fileName);
        // 把相应的Excel 工作簿写入到文件中
        book.write(output);
        //即清空缓冲区数据
        output.flush();
        // 操作结束，关闭文件
        output.close();
        System.out.println("文件生成成功！"+path + fileName);


    }
    private void fillCellValue(int starCellIndex,String values,HSSFRow row, HSSFCellStyle style) {
        String arrayTitleName[] = values.split(";");
        for (int i = 0; i < arrayTitleName.length; i++) {
            HSSFCell cell = row.createCell(starCellIndex + i);
            cell.setCellValue(arrayTitleName[i]);
            if(style!=null)
            cell.setCellStyle(style);
        }
    }
    //设定 某一行指定列样式
    private void fillCellStyle(HSSFRow row,int cellArray[], HSSFCellStyle style) {
        for(int i =0;i<cellArray.length;i++){
            row.getCell(cellArray[i]).setCellStyle(style);
        }
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public  void simpleTest() {
  //    System.out.print(sub(null,new BigDecimal(4)));
        System.out.print(indexToColumn(26));
    }
    public static String percentCaculate(BigDecimal a1, BigDecimal a2) {
        if(a2 ==null|| a2.compareTo(BigDecimal.ZERO)==0){
            return "0%";
        }
        a1 = a1==null?BigDecimal.ZERO:a1;
        a1.setScale(7, BigDecimal.ROUND_HALF_UP);
        BigDecimal r = a1.divide(a2, 4, BigDecimal.ROUND_HALF_EVEN).setScale(4, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(r.doubleValue());
    }
    public static double sub(BigDecimal a1, BigDecimal a2) {
        a1 = a1==null?BigDecimal.ZERO:a1;
        a2 = a2==null?BigDecimal.ZERO:a2;
     return a1.subtract(a2).doubleValue();
    }

    /**
     * 用于将excel表格中列索引转成列号字母，从A对应1开始
     *
     * @param index
     *            列索引
     * @return 列号
     */
    private static String indexToColumn(int index) {
        index = index+1; //1 - A
        if (index <= 0) {
            try {
                throw new Exception("Invalid parameter");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        index--;
        String column = "";
        do {   if (column.length() > 0) {
            index--;
        }
            column = ((char) (index % 26 + (int) 'A')) + column;
            index = (int) ((index - index % 26) / 26);
        } while (index > 0);
        return column;
    }
    @Test
    public void jsonTest() throws Exception {
        //*Obj -> JSON String ("" or null 无值)
        PlatformRecordReservedField platformRecordReservedField = new PlatformRecordReservedField();
        platformRecordReservedField.setUserInfoID("444");
        platformRecordReservedField.setUserRealName(null);
        String reservedFieldJson = JsonUtils.toJson(platformRecordReservedField);

        //*Json String 转成对象
        PlatformRecordReservedField convertObj = JsonUtils.toBean(reservedFieldJson, PlatformRecordReservedField.class);


        // *Json String 转成对象 lsit
        List<PlatformRecordReservedField> businessReceiptsList3 = JsonUtils.toBeanList(reservedFieldJson,ArrayList.class, PlatformRecordReservedField.class);
        System.out.print(businessReceiptsList3.size());
    }
    //将jason 转换成对象list
    public static final ObjectMapper mapper = new ObjectMapper();
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    //java 8 test for Lambda
    @Test
    public void lambdaTest() throws Exception {
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

    // 以前的循环方式
         /*   for (String player : players) {
                System.out.print(player + "; ");
            }*/

    // 使用 lambda 表达式以及函数操作(functional operation)
        System.out.println("***lambda 表达式以及函数操作****");
            players.forEach((player) -> System.out.print(player + "; "));

    // 在 Java 8 中使用双冒号操作符(double colon operator)
        System.out.println("***lambda 使用双冒号操作符****");
            players.forEach(System.out::println);
    }

}