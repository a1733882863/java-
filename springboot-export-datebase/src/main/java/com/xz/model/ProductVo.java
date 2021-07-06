/**
* 版权声明：厦门睿商网络科技有限公司 版权所有 违者必究
* 日    期：2020-02-17
*/
package com.xz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *   产品档案数据库对象
 * </pre>
 * @author Rzico Boot
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVo {
    //@ApiModelProperty(value = "")
    protected Long id;

    //@ApiModelProperty(value = "市场价")
    protected BigDecimal marketPrice;

    //@ApiModelProperty(value = "是否上架")
    protected Boolean isMarketable;

    //@ApiModelProperty(value = "商户名称")
    protected String mchName;

   // @ApiModelProperty(value = "商户Logo")
    protected String mchLogo;
//
 //   @ApiModelProperty(value = "名称")
    protected String name;

  //  @ApiModelProperty(value = "规格1")
    protected String spec1;

  //  @ApiModelProperty(value = "规格2")
    protected String spec2;

  //  @ApiModelProperty(value = "销售价")
    protected BigDecimal price;
//
   // @ApiModelProperty(value = "vip价")
    protected BigDecimal vipPrice;

   // @ApiModelProperty(value = "vip1价")
    protected BigDecimal vip1price;

  //  @ApiModelProperty(value = "vip2价")
    protected BigDecimal vip2price;

   // @ApiModelProperty(value = "vip3价")
    protected BigDecimal vip3price;

   // @ApiModelProperty(value = "vip4价")
    protected BigDecimal vip4price;

   // @ApiModelProperty(value = "vip5价")
    protected BigDecimal vip5price;

  //  @ApiModelProperty(value = "商品")
    protected Long goodsId;

   // @ApiModelProperty(value = "运费模板")
    protected Long freightId;

   // @ApiModelProperty(value = "品牌")
    protected Long brandId;

    //@ApiModelProperty(value = "分类")
    protected Long productCategoryId;

    //@ApiModelProperty(value = "分类路径")
    protected String treePath;

   // @ApiModelProperty(value = "分类名称")
    protected String productCategoryName;

    //@ApiModelProperty(value = "缩略图")
    protected String thumbnail;

   // @ApiModelProperty(value = "当前库存")
    protected Integer stock;

   // @ApiModelProperty(value = "品牌名称")
    protected String brandName;

    //@ApiModelProperty(value = "描述说明")
    protected String subTitle;

    //@ApiModelProperty(value = "最小起订量")
    protected Integer minLimit;

   // @ApiModelProperty(value = "日限量")
    protected Integer maxLimit;

    //@ApiModelProperty(value = "总销量")
    protected Integer totalSale;

    //@ApiModelProperty(value = "好评数")
    protected Integer review;

    //@ApiModelProperty(value = "sku")
    protected Integer sku;

    //@ApiModelProperty(value = "类型")
    protected Integer subType;

   // @ApiModelProperty(value = "积分")
    protected Integer point;

    //@/ApiModelProperty(value = "编号")
    protected String sn;


  //  protected List<PromotionVo> promotions = Collections.emptyList();

   // protected List<CouponVo> coupons = Collections.emptyList();

    public String getSpec() {
        if (getSpec1()==null) {
            setSpec1("");
        }
        if (getSpec2()==null) {
            setSpec2("");
        }
        return getSpec1().concat(getSpec2());
    }

//    @JsonIgnore
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


}
