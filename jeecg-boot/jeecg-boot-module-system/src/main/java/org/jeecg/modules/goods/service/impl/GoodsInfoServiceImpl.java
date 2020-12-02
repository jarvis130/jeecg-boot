package org.jeecg.modules.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.goods.entity.ApiGoodsVo;
import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.entity.GoodsSku;
import org.jeecg.modules.goods.entity.SkuDataEntity;
import org.jeecg.modules.goods.entity.SkuJsonEntity;
import org.jeecg.modules.goods.mapper.GoodsInfoMapper;
import org.jeecg.modules.goods.mapper.GoodsSkuMapper;
import org.jeecg.modules.goods.service.IGoodsInfoService;
import org.jeecg.modules.inventory.entity.InventoryInfo;
import org.jeecg.modules.inventory.mapper.InventoryInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private InventoryInfoMapper inventoryInfoMapper;

    @Autowired
    private GoodsSkuServiceImpl goodsSkuService;

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
    public void updateGoodsInfo(GoodsInfo goodsInfo){

        try {
            goodsInfoMapper.updateById(goodsInfo);
            //保存规格
            String skuJsonData = goodsInfo.getSkuJsonData();
            if(goodsInfo.getEnableSku() && skuJsonData != null){
                SkuJsonEntity skuJsonEntity = JSON.parseObject(skuJsonData, SkuJsonEntity.class);
                if(skuJsonEntity != null){
                    GoodsSku goodsSku = new GoodsSku();
                    String goodsId = goodsInfo.getId();
                    //获取当前商品所有规格值
                    List<GoodsSku> goodsSkuList = goodsSkuService.getGoodsSkuListByGoodsId(goodsId);
                    if(goodsSkuList != null && goodsSkuList.size() > 0){

                        //将sku设置为删除状态
                        goodsSku.setDelFlag("1");
                        goodsSku.setGoodsId(goodsId);
                        QueryWrapper<GoodsSku> queryWrapper = new QueryWrapper();
                        queryWrapper.eq("goods_id", goodsId);
                        goodsSkuMapper.update(goodsSku, queryWrapper);

                        //解析规格数据
                        JSONArray data = skuJsonEntity.getDataSource();
                        for(int i=0; i<data.size(); i++){
                            SkuDataEntity item = data.getObject(i, SkuDataEntity.class);
                            String skuId = item.getId();
                            boolean finded = false;

                            for(int j=0; j<goodsSkuList.size(); j++){
                                goodsSku = goodsSkuList.get(j);
                                if(skuId.equals(goodsSku.getId())){
                                    //更新规格表
                                    BeanUtils.copyProperties(item, goodsSku);
                                    goodsSku.setGoodsId(goodsId);
                                    String stock = item.getStock();
                                    if(stock == null){
                                        stock="0";
                                    }
                                    goodsSku.setStock(Integer.parseInt(stock));
                                    String salePrice = item.getSalePrice();
                                    if(salePrice == null){
                                        salePrice="0";
                                    }
                                    goodsSku.setDelFlag("0");
                                    goodsSku.setSalePrice(new BigDecimal(salePrice));
                                    goodsSkuMapper.updateById(goodsSku);
                                    //更新库存表
                                    InventoryInfo inventoryInfo = new InventoryInfo();
//                                    inventoryInfo.setValidAmount(Integer.parseInt(stock));
                                    QueryWrapper<InventoryInfo> wrapper = new QueryWrapper();
                                    wrapper.eq("goods_id", goodsId);
                                    wrapper.eq("sku_id", skuId);
                                    inventoryInfoMapper.update(inventoryInfo, wrapper);
                                    //
                                    finded = true;
                                    break;
                                }
                            }

                            //没找到，则新建插入
                            if(!finded){
                                //插入规格表
                                BeanUtils.copyProperties(item, goodsSku);
                                goodsSku.setGoodsId(goodsId);
                                String stock = item.getStock();
                                if(stock == null){
                                    stock="0";
                                }
                                goodsSku.setStock(Integer.parseInt(stock));
                                String salePrice = item.getSalePrice();
                                if(salePrice == null){
                                    salePrice="0";
                                }
                                goodsSku.setSalePrice(new BigDecimal(salePrice));
                                goodsSkuMapper.insert(goodsSku);
                                //插入库存表
                                InventoryInfo inventoryInfo = new InventoryInfo();
//                                inventoryInfo.setValidAmount(Integer.parseInt(stock));
//                                inventoryInfo.setGoodsId(goodsId);
//                                inventoryInfo.setSkuId(skuId);
//                                inventoryInfo.setInvalidAmount(0);
                                inventoryInfoMapper.insert(inventoryInfo);
                            }
                        }
                    }else{

                        JSONArray data = skuJsonEntity.getDataSource();

                        for(int i=0; i<data.size(); i++){
                            SkuDataEntity item = data.getObject(i, SkuDataEntity.class);
                            //插入
                            BeanUtils.copyProperties(item, goodsSku);
                            goodsSku.setGoodsId(goodsId);
                            String stock = item.getStock();
                            if(stock == null){
                                stock="0";
                            }
                            goodsSku.setStock(Integer.parseInt(stock));
                            String salePrice = item.getSalePrice();
                            if(salePrice == null){
                                salePrice="0";
                            }
                            goodsSku.setSalePrice(new BigDecimal(salePrice));
                            goodsSkuMapper.insert(goodsSku);
                            String skuId = goodsSku.getId();
                            //插入库存表
                            InventoryInfo inventoryInfo = new InventoryInfo();
//                            inventoryInfo.setValidAmount(Integer.parseInt(stock));
//                            inventoryInfo.setGoodsId(goodsId);
//                            inventoryInfo.setSkuId(skuId);
//                            inventoryInfo.setInvalidAmount(0);
                            inventoryInfoMapper.insert(inventoryInfo);
                        }

                    }

                }
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IPage<GoodsInfo> queryGoodsList(ApiGoodsVo goodsVo) {
        Page<GoodsInfo> page = new Page<>(goodsVo.getPageNum(), goodsVo.getPageSize());
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>()
                .eq("", goodsVo.getAppId())
                .eq("", goodsVo.getType());
        return this.baseMapper.selectPage(page, queryWrapper);
    }
}
