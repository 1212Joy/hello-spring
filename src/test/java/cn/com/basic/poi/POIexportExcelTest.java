package cn.com.basic.poi;

import cn.com.basic.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/5/25.
 */
public class POIexportExcelTest extends BaseTest {
    @Autowired
    private POIexportExcel poiExportExcel;
    @Test
    public void testExcute() throws Exception {
        poiExportExcel.excute();
    }
}