package cn.com.basic.poi;

/**
 * Created by zhaijiayi on 2016/5/25.
 */

import cn.com.basic.dao.HelloWorldDao;
import cn.com.basic.dto.HelloWorldDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public String excute() {
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
}