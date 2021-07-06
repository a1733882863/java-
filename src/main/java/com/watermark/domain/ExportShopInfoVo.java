package com.watermark.domain;

import lombok.Data;

import java.util.Date;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExportShopInfoVo {
//    select
//    b.create_date,
//    a.category_name,
//    b.name as shop_name,
//    b.linkman,
//    b.telephone,
//    b.address,
//    b.`status`   状态 {0:待审核,1:已审核,2:已关闭}
//    from wx_shop_category a
//    right join wx_shop b on a.id = b.shop_category_id
//
//    where b.deleted = 0  and b.scope_type = 2   经营范围 {0:其他,1:餐饮,2:酒店,3:名厨,4.餐车}
//    @ApiModelProperty("创建日期")
    Date createDate;
//    @ApiModelProperty("分类名称")
    String categoryName;
//    @ApiModelProperty("商店名")
    String shopName;
//    @ApiModelProperty("联系人")
    String linkman;
//    @ApiModelProperty("联系电话")
    String telephone;
//    @ApiModelProperty("地址")
    String address;
//    @ApiModelProperty("{0:待审核,1:已审核,2:已关闭}")
    Integer status;
//    @ApiModelProperty("经营范围{0:其他,1:餐饮,2:酒店,3:名厨,4.餐车}")
    Integer scopeType;
//    @ApiModelProperty("是否删除{0:未删除,1:已删除}")
    Integer deleted;
    String food;
    String sizeFood1;

    String foodMark;
    String sizeFood2;

    String license;
    String sizeLicense1;

    String licenseMark;
    String sizeLicense2;
}
