package cn.com.basic.excel.export;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhuzhihang on 2016/8/31.
 * JXLS 通用导出方法
 */
@Service
public class BaseExportService {
    @Autowired
    ResourcePatternResolver resourceLoader;

    private String rootPath = ResourceLoader.CLASSPATH_URL_PREFIX + "/excel/";

    private HSSFWorkbook getExcel(String name) throws Exception {
        Resource excelData = resourceLoader.getResource(rootPath + name + ExportUtils.excelSuffix);

        POIFSFileSystem fs = new POIFSFileSystem(excelData.getInputStream());

        return new HSSFWorkbook(fs);
    }


    public HSSFWorkbook export(String name, Map<String, Object> data) throws Exception {
        HSSFWorkbook workbook = getExcel(name);

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformWorkbook(workbook, data);

        return workbook;
    }


}
