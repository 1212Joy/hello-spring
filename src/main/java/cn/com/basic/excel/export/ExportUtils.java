package cn.com.basic.excel.export;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * Created by zhuzhihang on 2016/8/31.
 * 导出Utils
 */
public class ExportUtils {

    public static String excelSuffix = ".xls";

    protected static final Logger logger = LoggerFactory.getLogger(ExportUtils.class);


    public static void writeExcel(Excel excel, HttpServletResponse response) {
        Objects.requireNonNull(excel, "导出表格不存在");
        writeExcel(excel.getName(), excel.getWorkbook(), response);
    }

    public static void writeExcel(String excelName, HSSFWorkbook workbook, HttpServletResponse response) {
        if (StringUtils.isBlank(excelName)) {
            throw new NullPointerException("请提供导出表格名称");
        }

        if (!excelName.endsWith(excelSuffix)) {
            excelName = excelName + excelSuffix;
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/msexcel;");
            response.setHeader("Content-Disposition", new String(("attachment;filename=" + toUtf8String(excelName)).getBytes("GB2312"), "UTF-8"));
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw new IllegalArgumentException("导出表格发生错误，请重新导出");
        }
    }

    private static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
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

    /**
     * @param name          表格名称
     * @param startDate     查询开始日期 nullable
     * @param realStartDate 实际有数据开始日期 nullable
     * @param endDate       查询结束日期 nullable
     * @param type          1-yyyyMMdd, 2-yyyyMM, 3-yyyy
     * @return 格式化后的名称
     * <p>
     * 如果选择了时间段: XXX表_（20160810-20160825）。
     * 如果只选择了开始时间，没有选择结束时间，则结束时间是当天的日期。
     * 如果没有选择开始时间，只选择了结束时间，则开始时间以最早的那条数据的日期为准。
     * 如果两个时间都没有选择，则开始时间是最早那条数据日期，结束时间是当天日期。
     * 如果选择了月份： XXX表_（201608）。
     * 如果没有选择月份则显示从开始有数据的月份到当月月份，例如XXX表_(201603-201609)
     * 如果选择了截至时间：XXX表_（20160315-20160815），起始时间就是数据中最早那条数据日期，截至日期就是选择的截至日期，
     * 例如最早那条数据日期是3月15号，截至日期选择了8月15号，则名称显示为XXX表_（20160315-20160815）。
     * 如果没选择截至时间，则显示截至到当天的时间，例如，今天是8月31日，则显示 XXX表_（20160315-20160831）
     */
    public static String buildName(String name, Date startDate, Date realStartDate, Date endDate, int type) {
        String start = StringUtils.EMPTY;
        String end;

        String result;

        if (startDate == null) {
            if (realStartDate != null) {
                start = getFormatString(realStartDate, type) + "-";
            }
        } else {
            start = getFormatString(startDate, type) + "-";
        }

        if (endDate == null) {
            end = getFormatString(new Date(), type);
        } else {
            end = getFormatString(endDate, type);
        }

        if (StringUtils.isBlank(start) && StringUtils.isBlank(end)) {
            result = name;
        } else {
            result = name + "_（" + start + end + "）";
        }

        logger.info("[表格名称] - [result] = {}", result);

        return result;
    }

    private static String getFormatString(Date date, int type) {
        if (type == 1) {
            return DateFormatUtils.format(date, "yyyyMMdd");
        } else if (type == 2) {
            return DateFormatUtils.format(date, "yyyyMM");
        } else if (type == 3) {
            return DateFormatUtils.format(date, "yyyy");
        } else {
            return DateFormatUtils.format(date, "yyyyMMdd");
        }
    }

    public static String buildName(String name, String startDate, String realStartDate, String endDate, int type) {
        // TODO: 2016/9/1  to be finished

        return "";
    }

}
