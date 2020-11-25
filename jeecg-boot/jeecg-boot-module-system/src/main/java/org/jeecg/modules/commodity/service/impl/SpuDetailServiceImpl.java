package org.jeecg.modules.commodity.service.impl;

import org.jeecg.modules.commodity.entity.SpuDetail;
import org.jeecg.modules.commodity.mapper.SpuDetailMapper;
import org.jeecg.modules.commodity.service.ISpuDetailService;
import org.jeecg.modules.system.vo.SysUserDepVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: spu_detail
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Service
public class SpuDetailServiceImpl extends ServiceImpl<SpuDetailMapper, SpuDetail> implements ISpuDetailService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Override
    public Map<String, SpuDetail> getSpuDetailByIds(List<String> ids) {

        List<SpuDetail> list = spuDetailMapper.selectBatchIds(ids);

        Map<String, SpuDetail> res = new HashMap<String, SpuDetail>();

        list.forEach(item -> {
            res.put(item.getId(), item);
        });
        return res;
    }
}
