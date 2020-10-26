package org.jeecg.modules.goods.service.impl;

import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.mapper.GoodsInfoMapper;
import org.jeecg.modules.goods.service.IGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: goods_info
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public String insertGoodsInfo(GoodsInfo goodsInfo){

        goodsInfoMapper.insert(goodsInfo);
        return goodsInfo.getId();
    }
}
