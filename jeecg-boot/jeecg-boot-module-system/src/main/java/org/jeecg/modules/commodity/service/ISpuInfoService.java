package org.jeecg.modules.commodity.service;

import org.jeecg.modules.commodity.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.commodity.entity.SpuInfoVO;

/**
 * @Description: spu_info
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
public interface ISpuInfoService extends IService<SpuInfo> {

    public SpuInfoVO saveSpu(SpuInfoVO spuInfoVO);

    public SpuInfoVO updateSpu(SpuInfoVO spuInfoVO);
}
