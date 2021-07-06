package com.xz.model; /**
 * 版权声明：厦门睿商网络科技有限公司 版权所有 违者必究
 * 日    期：2020-02-17
 */


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Supplier;

/**
 * <pre>
 *   产品档案数据库对象
 * </pre>
 * @author Rzico Boot
 * @version 1.0
 */
@Data
@Table(name = "wx_product")
public class Product {


    @Id
    protected Long id;

    @Column(name = "goods")
    protected Long goodsId;
    //@ApiModelProperty(value = "")

    //@ApiModelProperty(value = "创建日期")
    protected Date createDate;

//    @ApiModelProperty(value = "修改日期")
    protected Date modifyDate;

  //  @ApiModelProperty(value = "成本价")
    protected BigDecimal cost;

    //@ApiModelProperty(value = "是否删除")
    protected Boolean deleted;

    //@ApiModelProperty(value = "是否列出")
    protected Boolean isList;

 //   @ApiModelProperty(value = "是否有视频")
    protected Boolean isMetaVideo;

   // @ApiModelProperty(value = "是否上架")
    protected Boolean isMarketable;

    //@ApiModelProperty(value = "市场价")
    protected BigDecimal marketPrice;

    //@ApiModelProperty(value = "名称")
    protected String name;

    //@ApiModelProperty(value = "简称")
    protected String shortName;

    //@ApiModelProperty(value = "销售价")
    protected BigDecimal price;

    //@/ApiModelProperty(value = "编号")
    protected String sn;

    //@ApiModelProperty(value = "单位")
    protected String unit;

    //@ApiModelProperty(value = "单位转换率")
    protected BigDecimal unitRate;

    //@ApiModelProperty(value = "vip1价")
    protected BigDecimal vip1price;

    //@ApiModelProperty(value = "vip2价")
    protected BigDecimal vip2price;

//    @ApiModelProperty(value = "vip3价")
    protected BigDecimal vip3price;

 //   @ApiModelProperty(value = "vip4价")
    protected BigDecimal vip4price;

  //  @ApiModelProperty(value = "vip5价")
    protected BigDecimal vip5price;

//    @ApiModelProperty(value = "重量")
    protected BigDecimal weight;

  //  @ApiModelProperty(value = "商品")
    //@Column(name = "goods")
    //protected Long goods;

  //  @ApiModelProperty(value = "分类")
    //@Column(name = "product_category_id")
    protected Long productCategoryId;

   // @ApiModelProperty(value = "")
    protected Integer orders;

   // @ApiModelProperty(value = "规格1")
    protected String spec1;

    //@ApiModelProperty(value = "规格2")
    protected String spec2;

    //@ApiModelProperty(value = "缩略图")
    protected String thumbnail;

   // @ApiModelProperty(value = "已分配库存")
    protected Integer allocatedStock;

    //@ApiModelProperty(value = "当前库存")
    protected Integer stock;

  //  @ApiModelProperty(value = "商品类型 {0:实物,1:虚拟,2:积分}")
    protected Integer type;

  //  @ApiModelProperty(value = "类型 {0.产品,1:套餐,2:水票,3.压桶,4.桶装水}")
    protected Integer subType;

   // @ApiModelProperty(value = "来源 {0:自营,1:芸市场}")
    protected Integer publishType;

   // @ApiModelProperty(value = "配送方式(0.全国快递,1.到店核销,2.同城配送)")
    protected String  shippingMethod;

   // @ApiModelProperty(value = "保期质")
    protected Integer quality;

   // @ApiModelProperty(value = "积分商品")
    protected Integer point;

   // @ApiModelProperty(value = "分销政策")
    protected Long distributionId;

   // @ApiModelProperty(value = "品牌")
    protected Long brandId;

    //@ApiModelProperty(value = "店主号(暂保留)")
    protected Long freightId;

   // @ApiModelProperty(value = "其他资源({video:视频url,images:[图片url],labal:['标签']})" )
    //@JsonIgnore
    protected String meta;

   // @ApiModelProperty(value = "商品详情" )
    //@JsonIgnore
    protected String content;

   // @ApiModelProperty(value = "商品描述")
    protected String subTitle;

    //@ApiModelProperty(value = "最小起订量")
    protected Integer minLimit;

    //@ApiModelProperty(value = "日限量")
    protected Integer maxLimit;

    //@ApiModelProperty(value = "供应商")
    protected Long supplierId;

    //@ApiModelProperty(value = "归属企业")
    protected Long enterpriseId;

    //@ApiModelProperty(value = "创建者")
    protected String createBy;

    //@ApiModelProperty(value = "更新者")
    protected String modifyBy;

    //@ApiModelProperty(value = "单位类型")
    protected Integer unitType;

    //@ApiModelProperty(value = "主商品ID")
    protected Long mainProductId;


    //@ApiModelProperty(value = "商品分类", hidden = true)
    //@JsonIgnore
//   protected ProductCategory productCategory;
//
//    //@ApiModelProperty(value = "商品详情")
//   protected ProductArticle article;
//
//    //@ApiModelProperty(value = "所属商户")
//    //@JsonIgnore
  // protected Enterprise enterprise;
//
//    //@IgnoreSwaggerParameter
//    //@JsonIgnore
   protected Supplier supplier;
//
//    //@ApiModelProperty(value = "税率" )
   protected java.math.BigDecimal tax;
//
//    protected List<Tag> tags = Collections.emptyList();

   // @ApiModelProperty(value = "分类名称", hidden = true)
//    public String getProductCategoryName(){
//        ProductCategory productCategory = getProductCategory();
//        if (null != productCategory){
//            return productCategory.getName();
//        }else{
//            return "";
//        }
//    }
//
//    public void setProductCategoryName(String productCategoryName){
//
//    }
//
//   // @ApiModelProperty(value = "商户名称", hidden = true)
//    public String getMchName(){
//        Enterprise enterprise = getEnterprise();
//        if (null != enterprise){
//            return enterprise.getName();
//        }else{
//            return "";
//        }
//    }

    public void setMchName(String mchName){

    }


//    @ApiModelProperty(value = "供应商名称", hidden = true)
//    public String getSupplierName(){
//        Supplier supplier = getSupplier();
//        if (null != supplier){
//            return supplier.getName();
//        }else{
//            return "";
//        }
//    }
//
//    public void setSupplierName(String supplierName){
//
//    }

    //@JsonIgnore
//    public String getSpec() {
//        if (getSpec1()==null) {
//            setSpec1("");
//        }
//        if (getSpec2()==null) {
//            setSpec2("");
//        }
//        return getSpec1().concat(getSpec2());
//    }
//
//    public void setSpec(String value) {
//
//    }
//
//    //@JsonIgnore
//    public BigDecimal getMemberPrice(Member member) {
//        BigDecimal price = getPrice();
//        if (member!=null) {
//            if (member.getVvip() == 5 && getVip5price().compareTo(BigDecimal.ZERO) > 0) {
//                price = getVip5price();
//            } else
//            if (member.getVvip() == 4 && getVip4price().compareTo(BigDecimal.ZERO) > 0) {
//                price = getVip4price();
//            } else
//            if (member.getVvip() == 3 && getVip3price().compareTo(BigDecimal.ZERO) > 0) {
//                price = getVip3price();
//            } else
//            if (member.getVvip() == 2 && getVip2price().compareTo(BigDecimal.ZERO) > 0) {
//                price = getVip2price();
//            } else
//            if (member.getVvip() == 1 && getVip1price().compareTo(BigDecimal.ZERO) > 0) {
//                price = getVip1price();
//            }
//        }
//        return price;
//    }
//
//    //@JsonIgnore
//    public ProductArticle getMetaAndContent() {
//        ProductArticle article = new ProductArticle();
//        article.setContent(getContent());
//        if (getMeta()!=null) {
//            article.setProductMeta(JSON.parseObject(getMeta(), ProductMeta.class));
//        } else {
//            article.setProductMeta(new ProductMeta());
//        }
//        return article;
//    }

}
