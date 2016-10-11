package cn.com.basic.excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by zhuzhihang on 2016/9/1.
 */
public class Excel {
    HSSFWorkbook workbook;
    String name;

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Excel{" +
                "name='" + name + '\'' +
                '}';
    }
}
