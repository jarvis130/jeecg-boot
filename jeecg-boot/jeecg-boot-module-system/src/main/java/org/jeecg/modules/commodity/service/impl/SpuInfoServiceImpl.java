package org.jeecg.modules.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.modules.commodity.entity.SpuDetail;
import org.jeecg.modules.commodity.entity.SpuInfo;
import org.jeecg.modules.commodity.entity.SpuInfoVO;
import org.jeecg.modules.commodity.mapper.SpuDetailMapper;
import org.jeecg.modules.commodity.mapper.SpuInfoMapper;
import org.jeecg.modules.commodity.service.ISpuInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public SpuInfoVO saveSpu(SpuInfoVO spuInfoVO) {

        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuInfoVO, spuInfo);
        spuInfoMapper.insert(spuInfo);

        String id = spuInfo.getId();

        SpuDetail spuDetail = new SpuDetail();
        BeanUtils.copyProperties(spuInfoVO, spuDetail);
        spuDetail.setId(id);
        spuDetailMapper.insert(spuDetail);

        spuInfoVO.setId(id);

        return spuInfoVO;
    }

    @Override
    @Transactional
    public SpuInfoVO updateSpu(SpuInfoVO spuInfoVO) {

        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuInfoVO, spuInfo);
        spuInfoMapper.updateById(spuInfo);
        String id = spuInfo.getId();

        SpuDetail spuDetail = new SpuDetail();
        BeanUtils.copyProperties(spuInfoVO, spuDetail);
        spuDetailMapper.updateById(spuDetail);

        spuInfoVO.setId(id);

        return spuInfoVO;
    }
}
