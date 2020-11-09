package org.jeecg.modules.goods.service;

import org.jeecg.modules.goods.entity.GoodsSku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: goods_sku
 * @Author: jeecg-boot
 * @Date:   2020-11-01
 * @Version: V1.0
 */
public interface IGoodsSkuService extends IService<GoodsSku> {

    /**
     *
     * @param goodsId
     * @return
     */
    public List<GoodsSku> getGoodsSkuListByGoodsId(String goodsId);
}
