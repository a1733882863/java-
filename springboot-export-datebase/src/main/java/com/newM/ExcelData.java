package com.newM;

import com.xz.model.Product;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ExcelData {

    private XSSFSheet sheet;

    /**
     * 构造函数，初始化excel数据
     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    public ExcelData(String filePath, String sheetName){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelData(InputStream fileInputStream, String sheetName){
        try {
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 根据行和列的索引获取单元格的数据
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row,int column){
        XSSFRow row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn 目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName,int currentColumn,int targetColumn){
        String operateSteps="";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            XSSFRow row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if(cell.equals(caseName)){
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    //打印excel数据
    public void readExcelData(){
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for(int j=0;j<columns;j++){
                String cell = row.getCell(j).toString();
                System.out.println(cell);
            }
        }
    }

    //测试方法
    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
//        ExcelData sheet1 = new ExcelData("C:\\Users\\xz\\Desktop\\drtest.xlsx", "sheet2");
//        XSSFSheet xssfSheet = sheet1.getSheet();
//        //一共多少行
//        int rows = xssfSheet.getPhysicalNumberOfRows();
//        //存放字段名, integer 是放序号
//        Map<Integer, String> xlsFieldMap = new HashMap<>();
//        //存放每一行的值
//        //List<Map<String,Object>> productMapList = new ArrayList<>();
//        //临时Map
//        Map<String,Object> map = null;
//        //类对象
//        Class clazz = Product.class;
//        //每行对象放入listProduct（赋值完成的product）
//        Product product = null;
//        List<Product> listProduct = new ArrayList<>();
//        //头一行
//        XSSFRow row0 = xssfSheet.getRow(0);
//        //临时行
//        XSSFRow row = null;
//        //临时字段名
//        String fieldName = null;
//
//
//        //循环遍历 字段名放入Map<Integer, String> xlsFieldMap
//        for (int i = 0;i < row0.getPhysicalNumberOfCells(); i++) {
//            fieldName = row0.getCell(i).toString();
//            xlsFieldMap.put(i,underlineToCamel(fieldName));
//        }
//
//
//        //循环遍历，为List<Map<String,Object>> productMapList赋值
//        for (int i = 1; i < rows; i++) {
//            //每次遍历行创建product
//            product = new Product();
//            //map = new HashMap<>();
//            //获取每一行
//            row = xssfSheet.getRow(i);
//            //每一行的列数
//            int physicalNumberOfCells = row.getLastCellNum();
//            for (int j = 0;j < physicalNumberOfCells; j++) {
//                try {
//                    //map.put(xlsFieldMap.get(j),row.getCell(j));
//                    //获取product对应字段对象
//                    Field declaredField = clazz.getDeclaredField(xlsFieldMap.get(j));
//                    //获取字段类型
//                    Class typeClass = declaredField.getType();
//                    //参数类型数组
//                    Class[] parameterTypes={String.class};
//                    //根据参数类型获取相应的构造函数
//                    Constructor con = typeClass.getConstructor(parameterTypes);
//                    //Method valueOf = typeClass.getMethod("valueOf", Class.forName(typeClass.getName()));
//                    //Object invoke = valueOf.invoke("1.2");
//                    //参数数组
//                    Object[] parameters={row.getCell(j).toString()};
//                    //创建出这个对象的值
//                    Object value = con.newInstance(parameters);
//                    declaredField.set(product, value);
//                } catch (Exception e) {
//                    continue;
//                }
//            }
//
//            listProduct.add(product);
//            //productMapList.add(map);
//            Assert.notNull(listProduct, "商品信息为空");
//            //TODO  查询商户信息，只能录入对应的商户
//            //
//            for (Product producttemp : listProduct) {
//                producttemp.setIsMarketable(false);
////            if (product.getSn()==null) {
////                return CommResult.error("货号不能为空");
////            }
////                if (product.getUnit() == null) {
////                    return CommResult.error("单位不能为空");
////                }
//                product.setPublishType(0);
//                if (product.getOrders() == null) {
//                    product.setOrders(99);
//                }
//                //TODO  product.setEnterpriseId(enterprise.getId());
//
//            }
//        }

    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        char underLine = '_';
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == underLine) {
                flag = true;
                continue;   //标志设置为true,跳过
            } else {
                if (flag == true) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

}
