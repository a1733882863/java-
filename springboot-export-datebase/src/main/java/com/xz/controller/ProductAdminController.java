package com.xz.controller;

import com.newM.ExcelData;
import com.xz.model.Product;
import com.xz.model.ProductVo;
import com.xz.service.ProductService;
import com.xz.utils.OssFileUtil;
import com.xz.utils.exprotexcel.ExportExcel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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

    @ResponseBody
    @GetMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest request, MultipartFile file) throws Exception {

        File file1 = new File("C:\\\\Users\\\\xz\\\\Desktop\\\\github\\\\java基础练习\\\\springboot-export-datebase\\\\src\\\\main\\\\resources\\\\sheet2.xlsx");
        InputStream inputStream = new FileInputStream(file1);
        //InputStream inputStream = file.getInputStream();
        OssFileUtil.uploadAliyun(null,"excelUpload/"+file1.getName(),inputStream,"UTF-8");
        //file对象名记得和前端name属性值一致
        //System.out.println(file1.getOriginalFilename());
        return "";
    }




    @ResponseBody
    @GetMapping("/exportData")
    public String exportData(HttpServletResponse response) throws Exception {
        //菜品名称，菜品分类，商品标签，运费模板，货号，商品图片， 规格，
        Map<String, Object> params = new HashMap<String, Object>();
        //params = buildSortField(params, pageable);

        if (params.get("sortField") != null && "create_date".equals(params.get("sortField").toString())) {
            params.put("sortField", "orders");
            params.put("sortType", "asc");
        }

        params.put("deleted", false);
        params.put("isList", true);
        params.put("isMarketable", true);

       // Enterprise enterprise = enterpriseService.findByMch(mchId);
 //       if (enterprise != null) {
            params.put("enterpriseId", 300);
//        } else {
//            return CommResult.error("没有开通企业");
//        }

//        if (productCategoryId != null) {
//            params.put("treePath", String.valueOf(productCategoryId));
//        }

//        if (brandId != null) {
//            params.put("brandId", brandId);
//        }

//        if (startPrice != null) {
//            params.put("startPrice", startPrice);
//        }

//        if (endPrice != null) {
//            params.put("endPrice", endPrice);
//        }

//        if (subType != null) {
//            params.put("subType", subType);
//        }
//
//        if (name != null) {
//            params.put("name", name);
//        }
//
//        if (tagIds != null && tagIds.length > 0) {
//            params.put("tagIds", tagIds);
//        }
//
//        if (StringUtils.isNotEmpty(keyword)) {
//            params.put("keyword", keyword);
//        }

//        Page<Object> startPage = PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize());
        List<ProductVo> list = productService.selectProductVoList(params);

 //       Member member = memberService.getCurrent();
        //获取全场活动
        //TODO
        for (ProductVo product : list) {

            Map<String, Object> prm = new HashMap<>();
            prm.put("goodsId", product.getGoodsId());
            Integer rw = productService.selectRowCount(prm);
            product.setSku(rw);

//            if (member != null && member.getVvip().compareTo(1) > 0) {
//                product.setVipPrice(product.getMemberPrice(member));
//            } else {
                product.setVipPrice(product.getVip1price());
                if (product.getVipPrice().compareTo(BigDecimal.ZERO) == 0) {
                    product.setVipPrice(product.getPrice());
                }
//            }

//            if (member != null && member.getAmount().compareTo(BigDecimal.ZERO) > 0) {
//                List<PromotionVo> pvo = new ArrayList<>();
//                for (PromotionVo vo : product.getPromotions()) {
//                    if (vo.getType().equals(10)) {
//                        continue;
//                    }
//                    if (vo.getFirsted()) {
//                        continue;
//                    }
//                    if (product.getFreightId() == null && vo.getType().equals(6)) {
//                        continue;
//                    }
//                    pvo.add(vo);
//                }
//                product.setPromotions(pvo);
//            }

        }

 //       PageResult<ProductVo> pageResult = new PageResult<>(list, startPage.getTotal(), pageable);
 //       return CommResult.success(pageResult);



        //如果出现中文乱码请添加下面这句
        //queryJson = URLDecoder.decode(queryJson,"utf-8");
        //需要导入alibaba的fastjson包
        //User user = com.alibaba.fastjson.JSON.parseObject(queryJson, User.class);
        //List<User> userlList = userService.getUserForExcel(user);
        ExportExcel<ProductVo> ee= new ExportExcel<ProductVo>();
        //根据ProductVo遍历出headers
        Class productClass = ProductVo.class;
        Field[] declaredFields = productClass.getDeclaredFields();
        String[] headers = new String[declaredFields.length];
        for (int i = 0;i < declaredFields.length; i ++) {
            headers[i] = declaredFields[i].getName();

        }
        String fileName = "sheet2";
        ee.exportExcel(headers,list,fileName,response);


        return list.toString();
    }





    @ResponseBody
    @GetMapping("/importData")
    public String importData() throws Exception {

        InputStream inputStreamByUrl1 = OssFileUtil.getInputStreamByUrl("https://ptcb.oss-cn-hangzhou.aliyuncs.com/excelUpload/sheet2.xlsx");
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
            xlsFieldMap.put(i,underlineToCamel(fieldName));
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

            producttemp.setIsMarketable(false);
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