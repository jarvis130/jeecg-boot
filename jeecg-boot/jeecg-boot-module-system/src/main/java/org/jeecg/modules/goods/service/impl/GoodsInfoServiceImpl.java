package org.jeecg.modules.goods.service.impl;

import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.util.FillRuleUtil;
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
    public GoodsInfo insertGoodsInfo(GoodsInfo goodsInfo){

        if(goodsInfo.getGoodsSn() == null){
            //用户没有输入就使用系统规则生成编码
            goodsInfo.setGoodsSn((String) FillRuleUtil.executeRule(FillRuleConstant.GOODS_CODE, null));
        }
        goodsInfoMapper.insert(goodsInfo);


        return goodsInfoMapper.selectById(goodsInfo.getId());
    }

    @Override
    public GoodsInfo updateGoodsInfo(GoodsInfo goodsInfo){

        goodsInfoMapper.updateById(goodsInfo);


        return goodsInfoMapper.selectById(goodsInfo.getId());
    }
}
