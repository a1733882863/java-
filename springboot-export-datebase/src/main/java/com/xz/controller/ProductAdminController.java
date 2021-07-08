package com.xz.controller;

import com.newM.ExcelData;
import com.xz.model.Product;
import com.xz.model.ProductVo;
import com.xz.service.ProductService;
import com.xz.utils.OssFileUtil;
import com.xz.utils.exprotexcel.ExportExcel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

@Controller
@RequestMapping("/aa")
public class ProductAdminController {

    @Autowired
    ProductService productService;


    public static Map<String,String> excelMap1 = null;

    public static Map<String,String> excelMap2 = null;

    static {
        excelMap1 = new HashMap();
        excelMap2 = new HashMap();
        //菜品编码 菜品名称  菜品分类 售卖价 规格名称 会员价 预估成本 条形码  数字助记码   是否打印  售卖转态  起售份数 菜品辣度 菜品描述
         //       菜品详细描述  备注1


        excelMap1.put("菜品编码","goods");
        excelMap1.put("菜品名称","name");
        excelMap1.put("菜品分类","categoryName");
        excelMap1.put("售卖价","price");
        excelMap1.put("规格名称","spec1");
        excelMap1.put("会员价","vip1price");
        //excelMap1.put("预估成本","sn");
        excelMap1.put("条形码","sn");
        //excelMap1.put("数字助记码","sn");
        //excelMap1.put("是否打印","sn");
        excelMap1.put("售卖转态","isMarketable");
        //excelMap1.put("起售份数","sn");
        //excelMap1.put("菜品辣度","sn");
        excelMap1.put("菜品描述","subTitle");
        excelMap1.put("单位(如件,斤，两)","unit");

        excelMap2.put("sn","菜品编码");
        excelMap2.put("name","菜品名称");
        excelMap2.put("categoryName","菜品分类");
        excelMap2.put("price","售卖价");
        excelMap2.put("spec1","规格名称");
        excelMap2.put("vip1price","会员价");
        //excelMap1.put("预估成本","sn");
        //excelMap1.put("条形码","sn");
        //excelMap1.put("数字助记码","sn");
        //excelMap1.put("是否打印","sn");
        excelMap2.put("isMarketable","售卖转态");
        //excelMap1.put("起售份数","sn");
        //excelMap1.put("菜品辣度","sn");
        excelMap2.put("subTitle","菜品描述");
        excelMap2.put("unit","单位(如件,斤，两)");

    }

    @ResponseBody
    @PostMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest request, MultipartFile file) throws Exception {

        File file1 = new File("C:\\\\Users\\\\xz\\\\Desktop\\\\github\\\\java基础练习\\\\springboot-export-datebase\\\\src\\\\main\\\\resources\\\\sheet2.xlsx");
        InputStream inputStream = new FileInputStream(file1);
        String uploadName = "excel_"+"300";
        //InputStream inputStream = file.getInputStream();
        OssFileUtil.uploadAliyun(null,"excelUpload/"+uploadName,inputStream,"UTF-8");
        //file对象名记得和前端name属性值一致
        //System.out.println(file1.getOriginalFilename());
        return "";
    }




    //导出菜品信息
    //type=0，最简导出，只导出必要字段
    @ResponseBody
    @GetMapping("/exportData")
//    @ApiImplicitParams(
//            {

//                    @ApiImplicitParam(name = "productCategory", value = "分类 id", dataType = "Long", paramType = "query"),
//                    @ApiImplicitParam(name = "type", value = "商品类型 {0:导出表格,1:简单导出,2:全部导出}", dataType = "Integer", paramType = "query"),
//                    @ApiImplicitParam(name = "subType", value = "类型 {0.菜品,1:套餐,2:水票,3.压桶,4.桶装水}", dataType = "Integer", paramType = "query"),
//                    @ApiImplicitParam(name = "name", value = "商品名称", dataType = "String", paramType = "query"),
//                    @ApiImplicitParam(name = "keyword", value = "商品名称", dataType = "String", paramType = "query"),
//                    @ApiImplicitParam(name = "sn", value = "条码/货号", dataType = "String", paramType = "query"),
//                    @ApiImplicitParam(name = "deleted", value = "删除标志", dataType = "Boolean", paramType = "query"),
//                    @ApiImplicitParam(name = "isMarketable", value = "是否上架", dataType = "Boolean", paramType = "query"),
//                    @ApiImplicitParam(name = "tagIds", value = "标签", dataType = "String", paramType = "query"),
//                    @ApiImplicitParam(name = "isList", value = "是否显示多规格", dataType = "Boolean", paramType = "query"),
//            })
    public String exportData(Long productCategoryId,Integer deleted,Integer isList,
                             Integer isMarketable,
                             Long[] tagIds,Integer type,HttpServletResponse response) throws Exception {


        Map<String, Object> params = new HashMap<String, Object>();        //params = buildSortField(params, pageable);
        if (params.get("sortField") != null && "create_date".equals(params.get("sortField").toString())) {
            params.put("sortField", "orders");
            params.put("sortType", "asc");
        }
        params.put("deleted", 0);
        params.put("isList", 1);
        params.put("isMarketable", 1);

       // Enterprise enterprise = enterpriseService.findByMch(mchId);
 //       if (enterprise != null) {
            params.put("enterpriseId", 300);
            //params.put("enterpriseId", enterprise);
//        } else {
//            return CommResult.error("没有开通企业");
//        }

//        if (productCategoryId != null) {
//            params.put("treePath", String.valueOf(productCategoryId));
//        }
        List<ProductVo> list = productService.selectProductVoList(params);
        String sheetName = "sheet1";
        XSSFWorkbook workbook = productService.transportExcel(list,sheetName);

        BufferedOutputStream fos = null;
        try {
            String fileName = null;
//            if (type == null || type == 0) {
//                fileName = "商品导入模板" + ".xlsx";
//            } else {
//                fileName = "导出的商品" + ".xlsx";
//            }
            fileName = "导出的商品" + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }


        return "导出完毕";
    }





    @ResponseBody
    @GetMapping("/importData")
    public String importData(Integer type) throws Exception {

        String uploadName = "excel_"+"300";


        InputStream inputStreamByUrl1 = OssFileUtil.getInputStreamByUrl("https://ptcb.oss-cn-hangzhou.aliyuncs.com/excelUpload/"+ uploadName);
        ExcelData sheet1 = new ExcelData(inputStreamByUrl1,"sheet2");

        //ExcelData sheet1 = new ExcelData("C:\\Users\\xz\\Desktop\\github\\java基础练习\\springboot-export-datebase\\src\\main\\resources\\sheet2.xlsx", "sheet2");
        XSSFSheet xssfSheet = sheet1.getSheet();
        //一共多少行
        int rows = xssfSheet.getPhysicalNumberOfRows();
        //存放字段名, integer 是放序号
        Map<Integer, String> xlsFieldMap = new HashMap<>();
        //存放每一行的值
        //List<Map<String,Object>> productMapList = new ArrayList<>();
        //临时Map
        Map<String,Object> map = null;
        //类对象
        Class clazz = Product.class;
        //每行对象放入listProduct（赋值完成的product）
        Product product = null;
        List<Product> listProduct = new ArrayList<>();
        //头一行
        XSSFRow row0 = xssfSheet.getRow(0);
        //临时行
        XSSFRow row = null;
        //临时字段名
        String fieldName = null;


        //循环遍历 字段名放入Map<Integer, String> xlsFieldMap
        for (int i = 0;i < row0.getPhysicalNumberOfCells(); i++) {
            fieldName = row0.getCell(i).toString();
            if (type !=null && type == 1) {
                fieldName = excelMap1.get(fieldName);
                xlsFieldMap.put(i,fieldName);
            }
            else {
                xlsFieldMap.put(i,underlineToCamel(fieldName));
            }
        }


        //循环遍历，为List<Map<String,Object>> productMapList赋值
        for (int i = 1; i < rows; i++) {
            //每次遍历行创建product
            product = new Product();
            //map = new HashMap<>();
            //获取每一行
            row = xssfSheet.getRow(i);
            //每一行的列数
            int physicalNumberOfCells = row.getLastCellNum();
            for (int j = 0;j < physicalNumberOfCells; j++) {
                try {
                    //map.put(xlsFieldMap.get(j),row.getCell(j));
                    //获取product对应字段对象
                    Field declaredField = clazz.getDeclaredField(xlsFieldMap.get(j));
                    declaredField.setAccessible(true);
                    //获取字段类型
                    Class typeClass = declaredField.getType();
                    //参数类型数组
                    Class[] parameterTypes={String.class};
                    //根据参数类型获取相应的构造函数
                    Constructor con = typeClass.getConstructor(parameterTypes);
                    //Method valueOf = typeClass.getMethod("valueOf", Class.forName(typeClass.getName()));
                    //Object invoke = valueOf.invoke("1.2");
                    //参数数组
                    //Object[] parameters= null;
                    if (typeClass.getName().equals("java.lang.Integer") || typeClass.getName().equals("java.lang.Long")) {
                        Object[] parameters={row.getCell(j).toString().split("[.]")[0]};
                        //创建出这个对象的值

                        Object value = con.newInstance(parameters);
                        declaredField.set(product, value);
                    }else {
                        Object[] parameters={row.getCell(j).toString()};
                        //创建出这个对象的值
                        Object value = con.newInstance(parameters);
                        declaredField.set(product, value);
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            listProduct.add(product);

        }
        //productMapList.add(map);
        Assert.notNull(listProduct, "商品信息为空");
        //TODO  查询商户信息，只能录入对应的商户

        //不同的分类放入不同的
        Map<Long,List<Product>> productsByCategory = new HashMap<>();
        for (Product producttemp : listProduct) {
            if (productsByCategory.containsKey(producttemp.getGoodsId())) {
                productsByCategory.get(producttemp.getGoodsId()).add(producttemp);
            } else {
                List<Product> temp = new ArrayList<>();
                temp.add(producttemp);
                productsByCategory.put(producttemp.getGoodsId(),temp);
            }

            producttemp.setIsMarketable(0);
//            if (product.getSn()==null) {
//                return CommResult.error("货号不能为空");
//            }
//                if (product.getUnit() == null) {
//                    return CommResult.error("单位不能为空");
//                }
            product.setPublishType(0);
            if (product.getOrders() == null) {
                product.setOrders(99);
            }
            //TODO  product.setEnterpriseId(enterprise.getId());
        }

        //分类为0的问题


        for (Map.Entry entry : productsByCategory.entrySet() ) {

            productService.insertProduct((List<Product>) entry.getValue(), null);
        }


        //List<Product> productList = productService.insertProduct(listProduct, null);
       // System.out.println(productList);
        return "导入完成";
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
                    sb.append(param.charAt(i));
                }
            }
        }
        return sb.toString();
    }


    @RequestMapping("/upload")
    public Object  productFile() {
        return "上传成功";
    }


    @GetMapping("/test")
    public String  hello(Model req) {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("user", "nihao user1");
        mv.setViewName("hello");
        return "bb";
    }

}



//https://www.cnblogs.com/barrywxx/p/10700221.html