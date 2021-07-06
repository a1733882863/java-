package com.xz.mapper;


import com.xz.model.Goods;
import com.xz.model.Product;
import com.xz.model.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper extends tk.mybatis.mapper.common.BaseMapper<Product>, MySqlMapper<Product>, IdsMapper<Product>, ConditionMapper<Product>, ExampleMapper<Product> {
    //List<ProductVo> selectProductVoList(Map<String, Object> params);

    public Integer selectRowCount(Map<String, Object> prm);

    public List<ProductVo> selectProductVoList(Map<String, Object> params);

    //int insertUseGeneratedKeys(Product product);
}
