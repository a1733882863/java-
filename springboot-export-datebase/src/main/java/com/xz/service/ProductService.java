package com.xz.service;

import com.xz.mapper.ProductMapper;
import com.xz.model.Goods;
import com.xz.model.Product;
import com.xz.model.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            product.setDeleted(false);
            product.setIsMetaVideo(false);
            System.out.println(product.getSn());
            //product.setType(0);
           // product.setPublishType(0);
            //是否上架，默认不上架
            if (product.getIsMarketable() == null) {
                product.setIsMarketable(false);
            }

            if (product.getSn() == null) {
                unPutNum ++;
                continue;
            }


            if (product.getIsList() != null) {
                //listCount++;
            } else {
                product.setIsList(false);
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
        params.setDeleted(false);
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
            product.setDeleted(false);
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
}
