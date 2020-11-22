package org.jeecg.modules.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.modules.commodity.entity.*;
import org.jeecg.modules.commodity.mapper.SpuDetailMapper;
import org.jeecg.modules.commodity.mapper.SpuInfoMapper;
import org.jeecg.modules.commodity.mapper.SpuSkuMapper;
import org.jeecg.modules.commodity.service.ISpuInfoService;
import org.jeecg.modules.inventory.entity.InventoryInfo;
import org.jeecg.modules.inventory.mapper.InventoryInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: spu_info
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements ISpuInfoService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SpuSkuMapper spuSkuMapper;

    @Autowired
    private InventoryInfoMapper inventoryInfoMapper;

    @Override
    @Transactional
    public SpuInfoVO saveSpu(SpuInfoVO spuInfoVO) {

        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuInfoVO, spuInfo);
        spuInfoMapper.insert(spuInfo);
        String id = spuInfo.getId();
        //
        SpuDetail spuDetail = new SpuDetail();
        BeanUtils.copyProperties(spuInfoVO, spuDetail);
        spuDetail.setId(id);
        spuDetailMapper.insert(spuDetail);
        spuInfoVO.setId(id);
        //处理特殊规格
        if(spuInfoVO.getEnableSpecialSpec()){
            updateSku(id, spuInfoVO.getSpecialSpec());
        }


        return spuInfoVO;
    }

    @Override
    @Transactional
    public SpuInfoVO updateSpu(SpuInfoVO spuInfoVO) {

        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuInfoVO, spuInfo);
        spuInfoMapper.updateById(spuInfo);
        String id = spuInfo.getId();
        //
        SpuDetail spuDetail = new SpuDetail();
        BeanUtils.copyProperties(spuInfoVO, spuDetail);
        spuDetailMapper.updateById(spuDetail);
        spuInfoVO.setId(id);
        //处理特殊规格
        if(spuInfoVO.getEnableSpecialSpec()){
            updateSku(id, spuInfoVO.getSpecialSpec());
        }

        return spuInfoVO;
    }

    private void updateSku(String spuId, String specialSpec){
        //将字符串转化给Json对象
        JsonSpecialSpec jsonSpecialSpec = JSON.parseObject(specialSpec, JsonSpecialSpec.class);
        String tableStr = jsonSpecialSpec.getTable();
        List<SpuSku> list = JSON.parseArray(tableStr, SpuSku.class);

        //查询是否存在
        for(SpuSku item: list){
            //根据spuId,key判断是否存在
            QueryWrapper<SpuSku> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sku_key", item.getSkuKey());
            queryWrapper.eq("spu_id", spuId);
            SpuSku spuSku = spuSkuMapper.selectOne(queryWrapper);
            if(spuSku != null){
               //更新
                String skuId = spuSku.getId();
                spuSkuMapper.updateById(item);
                //更新库存表
                InventoryInfo inventoryInfo = inventoryInfoMapper.selectById(skuId);
                if(inventoryInfo != null){
                    int num = inventoryInfo.getValidStock() + item.getStock();
                    inventoryInfo.setValidStock(num);
                    inventoryInfoMapper.updateById(inventoryInfo);
                }

            }else{
                //新增
                item.setSpuId(spuId);
                item.setStatus(1);
                spuSkuMapper.insert(item);
                //插入库存表
                InventoryInfo inventoryInfo = new InventoryInfo();
                inventoryInfo.setId(item.getId());
                inventoryInfo.setSpuId(spuId);
                inventoryInfo.setValidStock(item.getStock());
                inventoryInfoMapper.insert(inventoryInfo);
            }

        }
    }

}
