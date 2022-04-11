

package com.waminet.common.tools.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.waminet.common.tools.exception.RenException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @description: Excel工具类
 * @author Lance
 * @version 1.0.0
 * @data: 2022-04-7
 */
public class ExcelUtils {

    /**
     * Excel导出
     *
     * @param response  response
     * @param fileName  文件名
     * @param list      数据List
     * @param pojoClass 对象Class
     */
    public static void exportExcel(HttpServletResponse response, String fileName, Collection<?> list, Class<?> pojoClass) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            //当前日期
            fileName = DateUtils.format(new Date());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, list);
        Sheet sheet1 = workbook.getSheetAt(0);
        sheet1.setDefaultColumnWidth(50 * 256);
        sheet1.setDefaultRowHeight((short) (2 * 256));
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        //设置表头样式
        Font font = workbook.createFont();
        CellStyle cellStyle = workbook.createCellStyle();
        //加粗
        font.setBold(true);
        cellStyle.setFont(font);
        //设置表头颜色
        cellStyle.setTopBorderColor(IndexedColors.GREEN.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
        cellStyle.setFillBackgroundColor(IndexedColors.PINK.getIndex());
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
        workbook.write(out);
        out.flush();
        out.close();
    }

    /**
     * Excel导出，先sourceList转换成List<targetClass>，再导出
     *
     * @param response    response
     * @param fileName    文件名
     * @param sourceList  原数据List
     * @param targetClass 目标对象Class
     */
    public static void exportExcelToTarget(HttpServletResponse response, String fileName, Collection<?> sourceList, Class<?> targetClass) throws Exception {
        List<Object> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }

        exportExcel(response, fileName, targetList, targetClass);
    }


    /**
     * Excel导出，先sourceList转换成List<targetClass>，再导出
     *
     * @param filePath    文件临时目录
     * @param fileName    文件名
     * @param sourceList  原数据List
     * @param targetClass 目标对象Class
     * @param columnClass 自定义导出对象，必须是已ColumnType结尾的，且类型是boolean类型的，true为不导出
     */
    public static String exportExcelToTarget(String filePath, String fileName, Collection<?> sourceList, Class<?> targetClass, Object columnClass) throws Exception {
        List<Object> targetList = new ArrayList<>(sourceList.size());
        // 原数据List转换
        for (Object source : sourceList) {
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }

        // Excel导出，自定义导出列
        return ExcelUtils.exportExcel(filePath, fileName, targetList, targetClass, columnClass);
    }


    /**
     * Excel根据自定义样式导出，先sourceList转换成List<targetClass>，再导出
     *
     * @param filePath    文件临时目录
     * @param fileName    文件名
     * @param sourceList  原数据List
     * @param targetClass 目标对象Class
     * @param columnClass 自定义导出对象，必须是已ColumnType结尾的，且类型是boolean类型的，true为不导出
     */
    public static String exportCustomStyleExcelToTarget(String tableName ,String sheetName,String filePath, String fileName, Collection<?> sourceList, Class<?> targetClass, Object columnClass) throws Exception {
        List<Object> targetList = new ArrayList<>(sourceList.size());
        // 原数据List转换
        for (Object source : sourceList) {
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }

        // Excel导出，自定义导出列
        return ExcelUtils.exportCustomStyleExcel(tableName,sheetName,filePath, fileName, targetList, targetClass, columnClass);
    }


    /**
     * Excel导出，自定义导出列
     *
     * @param filePath    文件临时目录
     * @param fileName    文件名
     * @param sourceList  原数据List
     * @param pojoClass   对象Class
     * @param columnClass 自定义导出对象，必须是已ColumnType结尾的，且类型是boolean类型的，true为不导出
     */
    private static String exportExcel(String filePath, String fileName, Collection<?> sourceList, Class<?> pojoClass, Object columnClass) throws Exception {
        // 导出Excel
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, sourceList);
        // 自定义导出列设置
        ExcelUtils.setCustomExcelColumn(columnClass, workbook);
        // 本地临时文件目录路径
        String localDirPath = FileUtils.getLocalDirPath(filePath);
        // 本地临时文件目录文件
        File file = new File(localDirPath);
        // 本地临时文件目录文件不存在的场合
        if (!file.exists()) {
            // 创建本地临时文件目录
            file.mkdirs();
        }

        // 临时导出文件
        String localFilePath = FileUtils.getLocalFilePath(filePath, fileName);

        // 文件流输出
        FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        return localFilePath;
    }

    /**
     * Excel自定义样式导出，自定义导出列
     *
     * @param filePath    文件临时目录
     * @param fileName    文件名
     * @param sourceList  原数据List
     * @param pojoClass   对象Class
     * @param columnClass 自定义导出对象，必须是已ColumnType结尾的，且类型是boolean类型的，true为不导出
     */
    private static String exportCustomStyleExcel(String tableName,String sheetName,String filePath, String fileName, Collection<?> sourceList, Class<?> pojoClass, Object columnClass) throws Exception {
        ExportParams exportParams = new ExportParams(tableName, sheetName, ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        // 导出Excel
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, sourceList);
        // 自定义导出列设置
        ExcelUtils.setCustomExcelColumn(columnClass, workbook);

        // 本地临时文件目录路径
        String localDirPath = FileUtils.getLocalDirPath(filePath);
        // 本地临时文件目录文件
        File file = new File(localDirPath);
        // 本地临时文件目录文件不存在的场合
        if (!file.exists()) {
            // 创建本地临时文件目录
            file.mkdirs();
        }

        // 临时导出文件
        String localFilePath = FileUtils.getLocalFilePath(filePath, fileName);

        // 文件流输出
        FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        return localFilePath;
    }



    /**
     * 自定义导出列设置
     *
     * @param columns
     * @param workbook
     * @throws Exception
     */
    private static void setCustomExcelColumn(Object columns, Workbook workbook) throws Exception {
        // 自定义导出列为空的场合
        if (columns == null) {
            throw new RenException("自定义导出列为空");
        }

        // Sheet
        Sheet sheet = workbook.getSheetAt(0);

        // 自定义导出列字段
        Field[] fields = ExcelUtils.getClassFields(columns.getClass());

        // 导出的列索引数
        int columnIndex = 0;
        // 遍历自定义导出列字段
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            // 自定义导出列字段属性结尾为：ColumnType的属性
            if (field.getName().contains("ColumnType")) {
                // 自定义导出列字段属性非布尔值的场合
                if (!field.getGenericType().toString().equalsIgnoreCase("boolean")) {
                    throw new RenException("自定义导出列字段属性非布尔值");
                }

                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
                // 值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                // 实际上setAccessible是启用和禁用访问安全检查的开关，并不是为true就能访问为false就不能访问。
                field.setAccessible(true);

                // 自定义导出列字段属性值
                Object value = field.get(columns);
                // 自定义导出列字段属性值为空的场合
                if (value == null) {
                    continue;
                }

                // 自定义导出列字段属性非布尔值的场合
                if (!(value instanceof Boolean)) {
                    continue;
                }

                // 自定义导出列字段属性值
                boolean valueBoolean = ((Boolean) value).booleanValue();
                // 自定义导出列字段属性值为True的场合，导出该字段
                if (valueBoolean == true) {
                    // 启用该字段
                    sheet.setColumnHidden(columnIndex, false);
                    // 宽度25个字符
                    sheet.setColumnWidth(columnIndex, 25 * 256);
                    columnIndex++;
                }
            }
        }
    }

    /**
     * 反射获取类的所有属性
     *
     * @param clazz
     * @return
     */
    private static Field[] getClassFields(Class<?> clazz) {
        ArrayList list = new ArrayList();

        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length; ++i) {
                list.add(fields[i]);
            }

            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);

        return (Field[]) list.toArray(fields);
    }

}
