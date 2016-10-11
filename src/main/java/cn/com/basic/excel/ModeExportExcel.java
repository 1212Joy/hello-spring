package cn.com.basic.excel;

import cn.com.basic.dto.HelloWorldDto;
import cn.com.basic.excel.export.BaseExportService;
import cn.com.basic.excel.export.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaiJiaYi on 2016/10/10.
 *  XLSTransformer - 导出表格
 *
 */
@Service
public class ModeExportExcel {
    @Autowired
    BaseExportService exportService;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Excel exportExcelByMode() throws Exception {

        //获取表格数据
        List<HelloWorldDto> list = new ArrayList<HelloWorldDto>();
        HelloWorldDto helloWorldDto = new HelloWorldDto();
        helloWorldDto.setId(1);
        helloWorldDto.setName("Lucy");
        list.add(helloWorldDto);

        //存到Map中
        Map<String, Object> data = new HashMap();
        data.put("list", list);

        //表格名称
        String excelName = "test_mode";
        //模板表格名称
        String templetName = "mode";

        Excel excel = new Excel();
        excel.setWorkbook(exportService.export(templetName, data));
        excel.setName(excelName);
        return excel;
    }


}
