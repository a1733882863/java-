package com.xz.service;

import com.xz.mapper.ProductMapper;
import com.xz.model.Goods;
import com.xz.model.Product;
import com.xz.model.ProductVo;
import com.xz.utils.exprotexcel.ExportExcel;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService {

    @Lazy
    @Autowired
    GoodsServie goodsService;

    @Autowired
    ProductMapper productMapper;

    public List<Product> insertProduct(List<Product> products, Long[] tagIds) throws Exception {
        //1、保存主表
        Goods goods = new Goods();
        //goods.setId(1234L);
        goods.setCreateDate(new Date());
        goods.setReview(0L);
        goods.setTotalSale(0L);
        goods.setHits(0L);
        goods.setScore(0);
        int affectCount = goodsService.insertUseGeneratedKeys(goods);
        if (affectCount <= 0) {
            throw new Exception("保存商品主表失败");
        }

        //2、保存子表
        int listCount = 0;

        int unPutNum = 0;
        //ProductArticle article = null;
        for (Product product : products) {
            InitProduct(product);

            product.setCreateDate(new Date());
            product.setGoodsId(goods.getId());
            product.setDeleted(0);
            product.setIsMetaVideo(0);
            System.out.println(product.getSn());
            //product.setType(0);
           // product.setPublishType(0);
            //是否上架，默认不上架
            if (product.getIsMarketable() == null) {
                product.setIsMarketable(0);
            }

            if (product.getSn() == null) {
                unPutNum ++;
                continue;
            }


            if (product.getIsList() != null) {
                //listCount++;
            } else {
                product.setIsList(0);
            }

            if (product.getUnit() == null) {
                product.setUnit("件");
            }


            if (product.getType() == null) {
                product.setType(0);
            }

            if (product.getPublishType() == null) {
                product.setPublishType(0);
            }

            if (product.getQuality() == null) {
                product.setQuality(0);
            }



            productMapper.insertUseGeneratedKeys(product);

        }

        if (listCount > 1) {
            throw new Exception("显示商品数超过1个,请检查");
        }

        Long mainProductId = null;

        for (Product product : products) {
            if (product.getIsList() != null) {
                mainProductId = product.getId();
                break;
            }
        }

        for (Product product : products) {
            if (!product.getUnitType().equals(0)) {
                product.setMainProductId(mainProductId);
            } else {
                product.setMainProductId(product.getId());
            }
            productMapper.updateByPrimaryKeySelective(product);
        }

//        if (tagIds != null) {
//            for (Long tagId : tagIds) {
//                ProductTag adTag = new ProductTag();
//                adTag.setProducts(products.get(0).getId());
//                adTag.setTags(tagId);
//                productTagService.insert(adTag);
//            }
//        }

        Product params = new Product();
        params.setGoodsId(goods.getId());
        params.setDeleted(0);
        return productMapper.select(params);
    }

    private void InitProduct(Product product) {
        if (product.getMaxLimit() == null) {
            product.setMaxLimit(0);
        }
        if (product.getMinLimit() == null) {
            product.setMinLimit(1);
        }
        if (product.getUnitRate() == null) {
            product.setUnitRate(BigDecimal.ONE);
        }
        if (product.getStock() == null) {
            product.setStock(0);
        }
        if (product.getSubType() == null) {
            product.setSubType(0);
        }
        if (product.getShippingMethod() == null) {
            product.setShippingMethod("0");
        }
        if (product.getAllocatedStock() == null) {
            product.setAllocatedStock(0);
        }
        if (product.getWeight() == null) {
            product.setWeight(BigDecimal.ZERO);
        }
        if (product.getCost() == null) {
            product.setCost(BigDecimal.ZERO);
        }
        if (product.getDeleted() == null) {
            product.setDeleted(0);
        }
        if (product.getUnitRate() == null) {
            product.setUnitRate(BigDecimal.ONE);
        }
        if (product.getUnitType() == null) {
            product.setUnitType(0);
        }
        if (product.getPoint() == null) {
            product.setPoint(0);
        }
    }

    public int insertUseGeneratedKeys(Product t) {

        return productMapper.insertUseGeneratedKeys(t);
    }

    public List<ProductVo> selectProductVoList(Map<String, Object> params) {
        return productMapper.selectProductVoList(params);
    }

    public Integer selectRowCount(Map<String, Object> prm) {
        return productMapper.selectRowCount(prm);
    }

    public XSSFWorkbook transportExcel(List<ProductVo> list,String sheetName) throws Exception {
        //导出字段转换
        Map<String,String> convertToMap = new HashMap<>();
        convertToMap.put("市场价","marketPrice");
        convertToMap.put("是否上架(0:否,1:是)","isMarketable");
        convertToMap.put("商品名","name");
        convertToMap.put("规格名称","spec1");
        convertToMap.put("售价","price");
        convertToMap.put("商品归类(相同商品不同规格参数一样)","goodsId");
        convertToMap.put("分类名称","productCategoryName");
        convertToMap.put("商品库存","stock");
        convertToMap.put("最小购买(默认1)","minLimit");
        convertToMap.put("最大购买(默认不限制)","maxLimit");
        convertToMap.put("条码","sn");
        convertToMap.put("标签名(若有多个逗号隔开)","");

        //导出字段转换
        Map<String,String> convertToMap2 = new HashMap<>();
        convertToMap2.put("marketPrice","市场价");
        convertToMap2.put("isMarketable","是否上架(0:否,1:是)");
        convertToMap2.put("name","商品名");
        convertToMap2.put("spec1","规格名称");
        convertToMap2.put("price","售价");
        convertToMap2.put("goodsId","商品归类(相同商品不同规格参数一样)");
        convertToMap2.put("productCategoryName","分类名称");
        convertToMap2.put("stock","商品库存");
        convertToMap2.put("minLimit","最小购买(默认1)");
        convertToMap2.put("maxLimit","最大购买(默认不限制)");
        convertToMap2.put("sn","条码");
        convertToMap2.put("","标签名(若有多个逗号隔开)");


        ExportExcel<ProductVo> ee= new ExportExcel<ProductVo>();
        //根据ProductVo遍历出headers
        Class productClass = ProductVo.class;
        Field[] declaredFields = productClass.getDeclaredFields();
        String[] headers = new String[convertToMap2.size()];
        for (int i = 0;i < headers.length; i ++) {
            //如果再map里存在就放进去
            if (convertToMap2.containsKey(declaredFields[i].getName())) {
                headers[i] = convertToMap2.get(declaredFields[i].getName());
            }
        }

        //ee.exportExcel(headers,list,fileName,response);
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            // 遍历集合数据，产生数据行
            Iterator<ProductVo> it = list.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                ProductVo productVo =  it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = productVo.getClass().getDeclaredFields();
                for (short i = 0; i < headers.length; i++) {
                    //获取对应bean的字段名
                    String fieldName = convertToMap.get(headers[i]);
                    //Field field = productClass.getField(fieldName);
                    //Field field = fields[i];
                    //String fieldName = field.getName();

                    XSSFCell cell = row.createCell(i);


                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = productVo.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object tempValue = getMethod.invoke(productVo, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String fieldValue = null;
                    // 其它数据类型都当作字符串简单处理
                    if(tempValue != null && tempValue != ""){
                        fieldValue = tempValue.toString();
                    }
                    if (fieldValue != null) {
                        XSSFRichTextString richString = new XSSFRichTextString(fieldValue);
                        cell.setCellValue(richString);
                    }
                }
            }

            return workbook;

        } catch (Exception e) {
            throw new Exception("导出失败");
        }
    }
}
