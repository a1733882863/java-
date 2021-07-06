package com.xz.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * <pre>
 *   产品档案数据库对象
 * </pre>
 * @author Rzico Boot
 * @version 1.0
 */
@Data
@Table(name = "wx_goods")
public class Goods{
    ///@ApiModelProperty(value = "")
    @Id
    protected Long id;

    //@ApiModelProperty(value = "创建日期")
    protected java.util.Date createDate;

    //@ApiModelProperty(value = "修改日期")
    protected java.util.Date modifyDate;

    //@ApiModelProperty(value = "星级")
    protected Integer score;

    //@ApiModelProperty(value = "好评")
    protected Long review;

    //@ApiModelProperty(value = "人气")
    protected Long hits;

   // @ApiModelProperty(value = "总销量")
    protected Long totalSale;

    //@ApiModelProperty(value = "创建者")
    protected String createBy;

    //@ApiModelProperty(value = "修改者")
    protected String modifyBy;
}
