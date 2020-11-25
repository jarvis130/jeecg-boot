package org.jeecg.modules.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.entity.GoodsSku;
import org.jeecg.modules.goods.mapper.GoodsSkuMapper;
import org.jeecg.modules.goods.service.IGoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.sql.Wrapper;
import java.util.List;

/**
 * @Description: goods_sku
 * @Author: jeecg-boot
 * @Date:   2020-11-01
 * @Version: V1.0
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements IGoodsSkuService {

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public List<GoodsSku> getGoodsSkuListByGoodsId(String goodsId){

        QueryWrapper<GoodsSku> queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id", goodsId);
        List<GoodsSku> result = goodsSkuMapper.selectList(queryWrapper);
        return result;
    }
}
