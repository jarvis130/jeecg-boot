package org.jeecg.modules.commodity.service;

import org.jeecg.modules.commodity.entity.SpuDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: spu_detail
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
public interface ISpuDetailService extends IService<SpuDetail> {

    public Map<String, SpuDetail> getSpuDetailByIds(List<String> ids);
}
