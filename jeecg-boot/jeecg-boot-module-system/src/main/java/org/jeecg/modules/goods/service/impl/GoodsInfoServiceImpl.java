package org.jeecg.modules.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.entity.SkuDataEntity;
import org.jeecg.modules.goods.entity.SkuJsonEntity;
import org.jeecg.modules.goods.mapper.GoodsInfoMapper;
import org.jeecg.modules.goods.service.IGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public GoodsInfo updateGoodsInfo(GoodsInfo goodsInfo){

        goodsInfoMapper.updateById(goodsInfo);
        //保存规格
        String skuJsonData = goodsInfo.getSkuJsonData();
        if(skuJsonData != null){
            SkuJsonEntity skuJsonEntity = JSON.parseObject(skuJsonData, SkuJsonEntity.class);
            if(skuJsonEntity != null){
                //将sku设置为删除状态

                //更新
                JSONArray data = skuJsonEntity.getDataSource();
                for(int i=0; i<data.size(); i++){
                    SkuDataEntity item = data.getObject(i, SkuDataEntity.class);
                    String id = item.getId();
                }
            }
        }

        return goodsInfoMapper.selectById(goodsInfo.getId());
    }
}
