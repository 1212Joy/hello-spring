package cn.com.basic.excel;

import cn.com.basic.BaseTest;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;

/**
 * Created by zhaijiayi on 2016/5/25.
 */
public class POIexportExcelTest extends BaseTest {
    @Autowired
    private POIexportExcel poiExportExcel;
    @Test
    public void testExcute() throws Exception {
        poiExportExcel.excute1();
    }

    @Test
    public void testExcute3() throws Exception {
        //Workbook book = poiExportExcel.excute3();
        //生成表格
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("业务收益表");
        //输出表头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        HSSFRow row1 =  sheet.createRow(0);
        HSSFCell row1Cell1 = row1.createCell(0);
        row1Cell1 .setCellValue("业务收益表");//第一行  0,5 ： 业务收益表
        row1Cell1.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFRow row2 =  sheet.createRow(1);

        row2 .createCell(0).setCellValue(" 编制单位:茂业金服");;//第二行  0,end ： 编制单位:茂业金服

        HSSFRow row3 = sheet.createRow(2);//第三行
        for(int i=1;i<12;i++){
            sheet.addMergedRegion(new CellRangeAddress(2, 3, i, i+1));
        }
        //B34 ~ K34  一、业务收入	居间服务费收入	取现费手续费收入	账户管理费收入	充值费收入	转让费收入	提前还款罚息收入	逾期滞纳金收入	催收管理费收入	会员费（每年）收入	二、业务支出
        String row3Context = "一、业务收入;居间服务费收入;取现费手续费收入;账户管理费收入;充值费收入;转让费收入;提前还款罚息收入;逾期滞纳金收入;催收管理费收入;会员费（每年）收入;二、业务支出";
        fillCellValue(1,row3Context,row3);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 18, 18));  //S34 三、业务利润
        row3.createCell(18).setCellValue("三、业务利润");

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 12, 15));        //M3~P3  第三方支出
        row3.createCell(12).setCellValue("第三方支出");

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 16, 17));             //Q3~R3  平台支出
        row3.createCell(16).setCellValue("平台支出");

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 19, 24));                 //T3~Y3  投资人
        row3.createCell(19).setCellValue("投资人");

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 25, 29));       //Z3~AD3 借款方
        row3.createCell(25).setCellValue("借款方");

        HSSFRow row4 = sheet.createRow(3);//第四行
        row3.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue("日期");//A4
        //M4~R4 充值费支出	取现费支出	认证费支出	其他支出	营销费用支出	营销费用支出
        String row4Context1 = "充值费支出;取现费支出;认证费支出;其他支出;营销费用支出;营销费用支出";
        fillCellValue(12,row4Context1,row4);
        //T4~AD4 充值	投资	支出	收入	总收入	毛利率	借款金额	费用支出	利息支出	总支出	成本率
        String row4Context2 = "充值;投资;支出;收入;总收入;毛利率;借款金额;费用支出;利息支出;总支出;成本率";
        fillCellValue(19,row4Context2,row4);
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
        HSSFRow lastRow2 = sheet.createRow(allRows+2);  //投资百分比
        lastRow2.createCell(0).setCellValue("投资百分比");
        HSSFRow lastRow1 = sheet.createRow(allRows+3);  //茂业百分比
        lastRow1.createCell(0).setCellValue("茂业百分比");

        String path = "E:\\";
        String fileName = "aaaaaa_"   + ".xls";
        FileOutputStream output = new FileOutputStream(path + fileName);
        // 把相应的Excel 工作簿写入到文件中
        book.write(output);
        //即清空缓冲区数据
        output.flush();
        // 操作结束，关闭文件
        output.close();
        System.out.println("文件生成成功！"+path + fileName);


    }
    private void fillCellValue(int starCellIndex,String values,HSSFRow row) {
        String arrayTitleName[] = values.split(";");
        for(int i =0;i<arrayTitleName.length;i++){
            row.createCell(starCellIndex+i).setCellValue(arrayTitleName[i]);
        }


    }
}