package com.xz.mapper;

import com.xz.model.Goods;
import com.xz.model.Product;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper  extends tk.mybatis.mapper.common.BaseMapper<Goods>, MySqlMapper<Goods>, IdsMapper<Goods>, ConditionMapper<Goods>, ExampleMapper<Goods> {

    //int insertUseGeneratedKeys1();
}