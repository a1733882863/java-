package com.export;

import lombok.Data;

@Data
public class ReportDO2 {
    String name1;
    String order_date2;
    String order_num3;
    String packaging_fee4;
    String shop_fee5;
    String coupon_swj6;
    String coupon_pla7;
    String real_pay8;
    String product_pay9;
    String distribute10;
    String rider_fee11;
    String wx_bill12;
//    d.name 商户名,
//    DATE_FORMAT(a.create_date,'%Y-%m-%d') 订单日期,
//    a.id 订单序号,
//    a.order_status,
//            case a.order_type when 0 then '实时单' when 1 then '预约单' else '未知订单类型' end 订单类型,
//    a.sn 订单号,
//    a.distribution_amount 结算金额,
//    a.sub_price 商品费用,
//    a.packaging_fee 包装费用,
//if(c.amount,c.amount,0) 平台补贴,
//    a.fee 服务费用,
//    a.rider_fee 配送费用,
//    a.discount 商家补贴,
//        订单状态,
//     支付状态,
//     配送状态
}
