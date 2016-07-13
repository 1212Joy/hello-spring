package cn.com.basic.controller;

import cn.com.basic.poi.POIexportExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zhaijiayi on 2016/5/25.
 */
@RestController
public class ExportExcelController {
    @Autowired
    POIexportExcel pOIexportExcel ;


    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void excute1(String name,HttpServletResponse response) {
        try {
            String filePath = pOIexportExcel.excute1();
            if(filePath.equals("")){
                throw new IllegalArgumentException("导出表格发生错误，请重新导出");
            }

            response.setContentType("application/msexcel;");
            response.setHeader("Content-Disposition", new String(("attachment;filename="+toUtf8String(name)+".xls").getBytes("GB2312"), "UTF-8"));

            File f = new File(filePath);
            FileInputStream in = new FileInputStream(f);
            byte b[] = new byte[1024];
            int i = 0;
            ServletOutputStream out = response.getOutputStream();
            while((i=in.read(b))!=  -1){
                out.write(b, 0, i);
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("导出表格发生错误，请重新导出");
        }
    }
    @RequestMapping(value = "/export/excel2", method = RequestMethod.GET)
    public void  excute2(String name,HttpServletResponse response) {
        //name: 《2016年4月1日-2016年4月30日投资者充值数据》
        try {
            HSSFWorkbook book = pOIexportExcel.excute2("");
            response.setContentType("application/msexcel;");
            response.setHeader("Content-Disposition", new String(("attachment;filename="+name+".xls").getBytes("GB2312"), "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("导出表格发生错误，请重新导出");
        }
    }
    private static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
