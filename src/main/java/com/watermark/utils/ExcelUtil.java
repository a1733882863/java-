package com.watermark.utils;

import com.watermark.domain.ExportShopInfoVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static <T> List<T> parseFromExcel(String path, Class<T> aimClass) {
        return parseFromExcel(path, 0, aimClass);
    }

    @SuppressWarnings("deprecation")
    public static <T> List<T> parseFromExcel(String path, int firstIndex, Class<T> aimClass) {
        List<T> result = new ArrayList<T>();
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fis);
            //对excel文档的第一页,即sheet1进行操作
            Sheet sheet = workbook.getSheetAt(0);
            int lastRaw = sheet.getLastRowNum();
            for (int i = firstIndex; i < lastRaw; i++) {
                //第i行
                Row row = sheet.getRow(i);
                T parseObject = aimClass.newInstance();
                Field[] fields = aimClass.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    //反射时访问私有变量
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    //第j列
                    Cell cell = row.getCell(j);
                    if (cell == null)
                        continue;
                    //很重要的一行代码,如果不加,像12345这样的数字是不会给你转成String的,只会给你转成double,而且会导致cell.getStringCellValue()报错
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String cellContent = cell.getStringCellValue();
                    cellContent = "".equals(cellContent) ? "0" : cellContent;
                    if (type.equals(String.class)) {
                        field.set(parseObject, cellContent);
                    } else if (type.equals(char.class) || type.equals(Character.class)) {
                        field.set(parseObject, cellContent.charAt(0));
                    } else if (type.equals(int.class) || type.equals(Integer.class)) {
                        field.set(parseObject, Integer.parseInt(cellContent));
                    } else if (type.equals(long.class) || type.equals(Long.class)) {
                        field.set(parseObject, Long.parseLong(cellContent));
                    } else if (type.equals(float.class) || type.equals(Float.class)) {
                        field.set(parseObject, Float.parseFloat(cellContent));
                    } else if (type.equals(double.class) || type.equals(Double.class)) {
                        field.set(parseObject, Double.parseDouble(cellContent));
                    } else if (type.equals(short.class) || type.equals(Short.class)) {
                        field.set(parseObject, Short.parseShort(cellContent));
                    } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                        field.set(parseObject, Byte.parseByte(cellContent));
                    } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        field.set(parseObject, Boolean.parseBoolean(cellContent));
                    }
                }
                result.add(parseObject);
            }
            fis.close();
            return result;
        } catch (

                Exception e) {
            e.printStackTrace();
            System.err.println("An error occured when parsing object from Excel. at " );
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        //参数里的5表示有效行数从第5行开始
        List<ExportShopInfoVo> studentInfos = ExcelUtil.parseFromExcel("C:\\Users\\xz\\Desktop\\shop.xls", 2,
                ExportShopInfoVo.class);
        for (int i = 0; i < studentInfos.size(); i++) {
            System.err.println(studentInfos.get(i).toString());
            //开始查看大小并赋值
            //ExcelUtil.assignValue(studentInfos.get(i));

        }
    }


    // 带标题写入Excel
    public static <T> void writeExcelWithTitle(List<T> beans, String path) {
        writeExcel(beans,path,true);
    }

    // 仅把数据写入Excel
    public static <T> void writeExcel(List<T> beans, String path) {
        writeExcel(beans,path,false);
    }

    private static <T> void writeExcel(List<T> beans, String path, boolean writeTitle) {
        if(beans == null || beans.size() == 0) return;
        Workbook workbook = new HSSFWorkbook();
        FileOutputStream fos = null;
        int offset = writeTitle ? 1 : 0;
        try {
            Sheet sheet = workbook.createSheet();
            for (int i = 0; i < beans.size() + offset; ++i) {
                if(writeTitle && i == 0) {createTitle(beans, sheet);continue;}
                Row row = sheet.createRow(i);
                T bean = beans.get(i - offset);
                Field[] fields = bean.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    Cell cell = row.createCell(j);
                    //Date,Calender都可以 使用  +"" 操作转成字符串
                    cell.setCellValue(field.get(bean)+"");
                }
            }
            fos = new FileOutputStream(path);
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static <T> void createTitle(List<T> beans,Sheet sheet){
        Row row = sheet.createRow(0);
        T bean = beans.get(0);
        Field[] fields = bean.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Cell cell = row.createCell(i);
            cell.setCellValue(field.getName());
        }
    }

}



//select
//        b.create_date,
//        a.name as category_name,
//        b.name as shop_name,
//        b.linkman,
//        b.telephone,
//        b.address,
//        b.`status`,
//        b.scope_type,
//        b.deleted,
//        c.food,
//        "1" as size_food1,
//        c.food_mark,
//        "2" as size_food2,
//        c.license,
//        "3" as size_license1,
//        c.license_mark,
//        "4" as size_license2
//        from wx_shop_category a
//        right join wx_shop b on a.id = b.shop_category_id
//        left join wx_enterprise c on c.id = b.enterprise_id
//        left join sys_mch d on c.mch_id = d.id
//        where d.del_flag = 0