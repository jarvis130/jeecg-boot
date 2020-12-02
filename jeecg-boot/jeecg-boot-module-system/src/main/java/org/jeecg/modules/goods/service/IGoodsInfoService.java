package org.jeecg.modules.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.goods.entity.ApiGoodsVo;
import org.jeecg.modules.goods.entity.GoodsInfo;

/**
 * @Description: goods_info
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
public interface IGoodsInfoService extends IService<GoodsInfo> {

    /**
     *
     * @param goodsInfo
     * @return
     */
    public GoodsInfo insertGoodsInfo(GoodsInfo goodsInfo);

    /**
     *
     * @param goodsInfo
     * @return
     */
    public void updateGoodsInfo(GoodsInfo goodsInfo);

    IPage<GoodsInfo> queryGoodsList(ApiGoodsVo goodsVo);
}
