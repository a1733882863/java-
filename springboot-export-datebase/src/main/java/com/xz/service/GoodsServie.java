package com.xz.service;

import com.xz.mapper.GoodsMapper;
import com.xz.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServie {

    @Autowired
    GoodsMapper goodsMapper;

    public int insertUseGeneratedKeys(Goods t) {

        return goodsMapper.insertUseGeneratedKeys(t);
    }
}
