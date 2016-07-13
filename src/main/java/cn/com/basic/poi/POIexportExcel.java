package cn.com.basic.poi;

/**
 * Created by zhaijiayi on 2016/5/25.
 */

import cn.com.basic.dao.HelloWorldDao;
import cn.com.basic.dto.HelloWorldDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class POIexportExcel {
    @Autowired
    private HelloWorldDao helloWorldDao;

    /**
     *
     */
    /*
     * Apache POI是Apache软件基金会的开放源码函式库，POI提供API给Java程序对Microsoft
     * Office格式档案读和写的功能。结构： 　　
     * HSSF － 提供读写Microsoft Excel格式档案的功能。
     * XSSF － 提供读写Microsoft Excel OOXML格式档案的功能。 　　
     * HWPF － 提供读写Microsoft Word格式档案的功能。 　　
     * HSLF － 提供读写Microsoft PowerPoint格式档案的功能。 　　
     * HDGF － 提供读写Microsoft Visio格式档案的功能。
     */
    //安装对象声明顺序  输出
    public String excute1() {
        // TODO Auto-generated method stub
        String excelTitle = new String("姓名%身份%性别");
        String arrayTitleName[] = excelTitle.split("%");
        int row = 0;
        try {

            // 创建Execl工作薄
            HSSFWorkbook book = new HSSFWorkbook();
            // 在Excel工作簿中建一工作表，其名为缺省值 ，可以指定也可以不指定
            HSSFSheet sheet = book.createSheet("goods");

            //将需要导出的数据库表中的数据存入 List 中
            List<HelloWorldDto> helloWorldDtolist = helloWorldDao.getAll();
            for (HelloWorldDto h : helloWorldDtolist) {
                // 使用反射机制获取Class类
                Class cl = Class.forName("cn.com.basic.dto.HelloWorldDto");
                // 获取class类的所有成员变量
                Field[] field = cl.getDeclaredFields();
                if (row == 0) {
                    //创建sheet表中的行
                    HSSFRow top = sheet.createRow(row);
                    //输出表头
                    for (int tem = 0; tem < arrayTitleName.length; tem++) {
                        HSSFCell topc = top.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        topc.setCellValue(arrayTitleName[tem]);
                    }
                  //  HSSFRow reco1 = sheet.createRow(row + 1);
                    HSSFRow reco2 = sheet.createRow(row + 1);
                    //第0行输出字段名同时第一行输出第一条记录值
                    for (int tem = 0; tem < field.length; tem++) {
                        field[tem].setAccessible(true);
                        //建立单无格
                      /*  HSSFCell recoc1 = reco1.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        recoc1.setCellValue(field[tem].getName());*/
                        HSSFCell recoc2 = reco2.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        recoc2.setCellValue(field[tem].get(h) == null ? "" : field[tem].get(h)
                                .toString());
                    }
                    row += 2;
                } else {//每一横行列循环
                    HSSFRow reco = sheet.createRow(row);
                    for (int tem = 0; tem < field.length; tem++) {
                        field[tem].setAccessible(true);
                        //建立单无格
                        HSSFCell recoc = reco.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        /*
                         * 在Label对象的构造子中指名单元格位置是第i列第row行(i,row) 以及单元格内容为
                         * field[tem].get(good).toString()即good对象每个字段的值，这里需要注意一下，如果表中
                         * 字段为Null时就报错，所以要将field[tem].get(good).toString()改为：
                         * field[tem].get(good) == null ? "":
                         * field[tem].get(good).toString() 这样的话如果值为Null时就 导出空字符即可。
                         */
                        recoc.setCellValue(field[tem].get(h) == null ? "" : field[tem].get(h)
                                .toString());
                    }
                    row++;
                }
            }
        //   sheet.removeRow(sheet.getRow(1));
            // 新建一输出文件流
            String path = "E:\\";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String timeString = sdf.format(Calendar.getInstance().getTime());
            String fileName = "POI表格测试_" + timeString + ".xls";
            FileOutputStream output = new FileOutputStream(path + fileName);
            // 把相应的Excel 工作簿写入到文件中
            book.write(output);
            //即清空缓冲区数据
            output.flush();
            // 操作结束，关闭文件
            output.close();

            System.out.println("文件生成成功！"+path + fileName);
            return path + fileName;
        } catch (ClassNotFoundException e) {
            System.out.println("未找到指定的类！");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("打开文件失败！");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("写入文件失败！");
            e.printStackTrace();
        }
        return "";
    }
//f返回book
    public HSSFWorkbook excute2(String excelTitle) {
        excelTitle = new String("公司%记账日期%业务日期%会计期间%凭证字%凭证号%分录号%摘要%科目%币种%汇率%方向%原币金额%数量%单价%借方金额%贷方金额%制单人%过账人%审核人%附件数量%过账标记%机制凭证模块%删除标记%凭证序号%单位%参考信息%是否有现金流量%\t现金流量标记%业务编号%结算方式%结算号%辅助账摘要%核算项目1%编码1%名称1%核算项目2%编码2%名称2%核算项目3%编码3%名称3%核算项目4%编码4%名称4%核算项目5%编码5%名称5%核算项目6%编码6%名称6%核算项目7%编码7%名称7%核算项目8%编码8%名称8%发票号%换票证号%客户%费用类别%收款人%物料%账务组织%供应商%辅助账业务日期%到期日");
        String arrayTitleName[] = excelTitle.split("%");
        int row = 0;
        try {

            // 创建Execl工作薄
            HSSFWorkbook book = new HSSFWorkbook();
            // 在Excel工作簿中建一工作表，其名为缺省值 ，可以指定也可以不指定
            HSSFSheet sheet = book.createSheet("sheet1");
            //创建sheet表中的行
            HSSFRow top = sheet.createRow(row);
            //输出表头
            for (int tem = 0; tem < arrayTitleName.length; tem++) {
                //建立单无格
                HSSFCell topc = top.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                topc.setCellValue(arrayTitleName[tem]);
            }
            //输出表数据
            for (int row2 = 2; row2 < 5; row2++) {
                //行
                HSSFRow reco = sheet.createRow(row2 + 1);
                //列
                HSSFCell recoc = reco.createCell(2, HSSFCell.CELL_TYPE_STRING);
                recoc.setCellValue("啦啦啦");
            }

            return book;
        } catch (Exception e) {
            throw new IllegalArgumentException("导出表格发生错误，请重新导出");
        }
    }

    //合并单元格
    public Workbook excute3() {
        try{
        Workbook wb=new HSSFWorkbook();

        Sheet sheet=wb.createSheet();
        /*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */

        //在sheet里增加合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 3, 3, 9));

        Row row = sheet.createRow(0);

        Cell cell_1 = row.createCell(3);

        cell_1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");

        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。
        Cell cell_2 = row.createCell(10);

        cell_2.setCellValue("what's up ! ");

        return wb;
        } catch (Exception e) {
            throw new IllegalArgumentException("导出表格发生错误，请重新导出");
        }
    }
    //将数据转换成list值 输出到表格
    //平台报表  - 正常到期收入明细表
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public HSSFWorkbook excute4()throws Exception {
        /*参数处理*/
        List<ArrayList> lists = new ArrayList();
        ArrayList array;
        String excelTitle = "";

        List<HelloWorldDto> platformAccountsRecordDTOListTem = helloWorldDao.getAll();
        List<HelloWorldDto> platformAccountsRecordDTOList = platformAccountsRecordDTOListTem == null? new ArrayList(): platformAccountsRecordDTOListTem;
        for (int i =0;i<platformAccountsRecordDTOList.size();i++) {
            HelloWorldDto record = platformAccountsRecordDTOList.get(i);
            array = new ArrayList();
            array.add(record.getId());
            array.add(record.getName());

            lists.add(array);
        }
        String arrayTitleName[] = excelTitle.split(",");
        //表格样式
        HSSFWorkbook temBook =  new HSSFWorkbook();
        //样式
        HSSFCellStyle headerStyle = temBook.createCellStyle();
        HSSFCellStyle titleStyle = temBook.createCellStyle();
        HSSFCellStyle dataStyle = temBook.createCellStyle();
        HSSFFont font1 = temBook.createFont();
        HSSFFont font2 = temBook.createFont();
        HSSFFont font3 = temBook.createFont();
        font1.setFontHeightInPoints((short) 13); //字体大小
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        headerStyle.setFont(font1);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        font2.setFontHeightInPoints((short) 11); //字体大小
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        titleStyle.setFont(font2);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        font3.setFontHeightInPoints((short) 10); //字体大小
        dataStyle.setFont(font3);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        String name ="正常到期收入明细表";
        HSSFWorkbook profitBook = exportExcel(temBook,"",arrayTitleName,lists,name,1,titleStyle,dataStyle);
        HSSFSheet sheet = profitBook.getSheet(name);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1.setCellValue(name);
        row1Cell1.setCellStyle(headerStyle);
        sheet.getRow(1).setHeight((short) (18 * 20 ));

        int allRows = sheet.getLastRowNum();
        HSSFRow lastRow = sheet.createRow(allRows+1);
        int rowNumber = allRows+1;
        fillCellStyle(lastRow,null,dataStyle,7);
        lastRow.getCell(0).setCellValue("合计");
        HSSFCell lastRowCell = lastRow.getCell(4);
        lastRowCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        lastRowCell.setCellFormula("SUM(E3:E"+rowNumber+")");
        for(int i=0;i<7;i++){
            sheet.setColumnWidth(i,(short)15*256);
            sheet.setColumnWidth(5,(short) 20*256);
        }
        return profitBook;
    }
    //导入表格模板输入数据
    public HSSFWorkbook exportAccountAgeDetail() {

        try {
            File fi = new File("");//file path
//          File fi= new File("D://accountAgeDetail.xls");
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            return wb;
        } catch (Exception e) {

        }
        return null;
    }
    private HSSFWorkbook exportExcel(  HSSFWorkbook book,String excelName,String excelTitleName[],List<ArrayList> dataArrayList,String sheetName,
                                       int startIndex,HSSFCellStyle titleStyle,HSSFCellStyle dataStyle) {
        try {
             /*处理数据*/
            String sheet1Name = "sheet1";
            if(!StringUtils.isEmpty(sheetName)){
                sheet1Name = sheetName;
            }

            /*创建表格*/
            book.createName().setNameName("_"+excelName+".xls");
            HSSFSheet sheet = book.createSheet(sheet1Name);

            /*输出表头*/
            HSSFRow row0 = sheet.createRow(startIndex);
            for (int tem = 0; tem < excelTitleName.length; tem++) {
                HSSFCell topc = row0.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                if(titleStyle != null){
                    topc.setCellStyle(titleStyle);
                }
                topc.setCellValue(excelTitleName[tem]);
                sheet.autoSizeColumn(tem);
            }
            /*输出list数据*/
            if(dataArrayList == null) {
                return book;
            }
            int row = startIndex + 1;
            for (List record : dataArrayList) {
                HSSFRow reco = sheet.createRow(row);
                for (int tem = 0; tem < record.size(); tem++) {
                    HSSFCell recoc ;
                    if(record.get(tem) == null){
                        recoc = reco.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        recoc.setCellValue(record.get(tem) == null ? "" : record.get(tem).toString());
                    }
                    else  if( "class java.lang.Double".equals(record.get(tem).getClass().toString())){
                        recoc = reco.createCell(tem, HSSFCell.CELL_TYPE_NUMERIC);
                        recoc.setCellValue((Double)record.get(tem) );
                    }else{
                        recoc = reco.createCell(tem, HSSFCell.CELL_TYPE_STRING);
                        recoc.setCellValue(record.get(tem) == null ? "" : record.get(tem).toString());
                    }
                    if(titleStyle != null){
                        recoc.setCellStyle(dataStyle);
                    }
                }
                row++;
            }
            return book;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String formatData(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
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
    private void fillCellStyle(HSSFRow row,Integer cellArray[], HSSFCellStyle style,Integer column) {
        if(column == null){
            for(int i =0;i<cellArray.length;i++){
                row.getCell(cellArray[i]).setCellStyle(style); //已存在
            }
        }else{
            for(int i =0;i<column;i++){
                row.createCell(i).setCellStyle(style);//生成新cell
            }
        }
    }

}