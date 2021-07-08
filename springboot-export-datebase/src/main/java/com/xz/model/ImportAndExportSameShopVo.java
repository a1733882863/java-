package com.xz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Supplier;

public class ImportAndExportSameShopVo {

    //@ApiModelProperty(value = "名称")
    protected String name;

    //@ApiModelProperty(value = "简称")
    protected String shortName;

    // @ApiModelProperty(value = "规格1")
    protected String spec1;

    //@Column(name = "goods")
    protected Long goodsId;
    //@ApiModelProperty(value = "")

    //  @ApiModelProperty(value = "分类")
    //@Column(name = "product_category_id")
    protected Long productCategoryId;

    //    @ApiModelProperty(value = "重量")
    protected BigDecimal weight;

    //@ApiModelProperty(value = "销售价")
    protected BigDecimal price;

    //@ApiModelProperty(value = "市场价")
    protected BigDecimal marketPrice;

    //  @ApiModelProperty(value = "成本价")
    protected BigDecimal cost;

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

    //@/ApiModelProperty(value = "编号")
    protected String sn;

    //@ApiModelProperty(value = "单位")
    protected String unit;

    // @ApiModelProperty(value = "")
    protected Integer orders;



    // @ApiModelProperty(value = "是否上架")
    protected Boolean isMarketable;

    //   @ApiModelProperty(value = "是否有视频")
    protected Boolean isMetaVideo;

    //@ApiModelProperty(value = "是否删除")
    protected Boolean deleted;


    //@ApiModelProperty(value = "是否列出")
    protected Boolean isList;



















    //  @ApiModelProperty(value = "商品")
    //@Column(name = "goods")
    //protected Long goods;






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




}
