package org.jeecg.modules.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.goods.entity.ApiGoodsVo;
import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.service.IGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="api")
@RestController
@RequestMapping("/api/goods")
@Slf4j
public class ApiGoodsInfoController {
    @Autowired
    private IGoodsInfoService goodsInfoService;

    /**
     * 分页列表查询
     *
     * @param apiGoodsVo
     * @return
     */
    @AutoLog(value = "商品管理-分页列表查询")
    @ApiOperation(value="商品管理-分页列表查询", notes="商品管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(@RequestBody ApiGoodsVo apiGoodsVo){
        if (apiGoodsVo.getPageSize() > 100) {
            Result.error("恶意访问！！");
        }
        IPage<GoodsInfo> pageList = goodsInfoService.queryGoodsList(apiGoodsVo);
        return Result.OK(pageList);
    }

}