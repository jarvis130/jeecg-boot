package org.jeecg.modules.goods.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.goods.entity.GoodsSku;
import org.jeecg.modules.goods.service.IGoodsSkuService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: goods_sku
 * @Author: jeecg-boot
 * @Date:   2020-11-01
 * @Version: V1.0
 */
@Api(tags="goods_sku")
@RestController
@RequestMapping("/goods/goodsSku")
@Slf4j
public class GoodsSkuController extends JeecgController<GoodsSku, IGoodsSkuService> {
	@Autowired
	private IGoodsSkuService goodsSkuService;
	
	/**
	 * 分页列表查询
	 *
	 * @param goodsSku
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "goods_sku-分页列表查询")
	@ApiOperation(value="goods_sku-分页列表查询", notes="goods_sku-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GoodsSku goodsSku,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GoodsSku> queryWrapper = QueryGenerator.initQueryWrapper(goodsSku, req.getParameterMap());
		Page<GoodsSku> page = new Page<GoodsSku>(pageNo, pageSize);
		IPage<GoodsSku> pageList = goodsSkuService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param goodsSku
	 * @return
	 */
	@AutoLog(value = "goods_sku-添加")
	@ApiOperation(value="goods_sku-添加", notes="goods_sku-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GoodsSku goodsSku) {
		goodsSkuService.save(goodsSku);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param goodsSku
	 * @return
	 */
	@AutoLog(value = "goods_sku-编辑")
	@ApiOperation(value="goods_sku-编辑", notes="goods_sku-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GoodsSku goodsSku) {
		goodsSkuService.updateById(goodsSku);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "goods_sku-通过id删除")
	@ApiOperation(value="goods_sku-通过id删除", notes="goods_sku-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		goodsSkuService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "goods_sku-批量删除")
	@ApiOperation(value="goods_sku-批量删除", notes="goods_sku-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.goodsSkuService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "goods_sku-通过id查询")
	@ApiOperation(value="goods_sku-通过id查询", notes="goods_sku-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GoodsSku goodsSku = goodsSkuService.getById(id);
		if(goodsSku==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(goodsSku);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param goodsSku
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GoodsSku goodsSku) {
        return super.exportXls(request, goodsSku, GoodsSku.class, "goods_sku");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, GoodsSku.class);
    }

}
