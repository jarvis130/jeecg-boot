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
import org.jeecg.modules.goods.entity.GoodsInfo;
import org.jeecg.modules.goods.service.IGoodsInfoService;

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
 * @Description: goods_info
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Api(tags="goods_info")
@RestController
@RequestMapping("/goods/goodsInfo")
@Slf4j
public class GoodsInfoController extends JeecgController<GoodsInfo, IGoodsInfoService> {
	@Autowired
	private IGoodsInfoService goodsInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param goodsInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品管理-分页列表查询")
	@ApiOperation(value="商品管理-分页列表查询", notes="商品管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GoodsInfo goodsInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GoodsInfo> queryWrapper = QueryGenerator.initQueryWrapper(goodsInfo, req.getParameterMap());
		Page<GoodsInfo> page = new Page<GoodsInfo>(pageNo, pageSize);
		IPage<GoodsInfo> pageList = goodsInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param goodsInfo
	 * @return
	 */
	@AutoLog(value = "商品管理-添加")
	@ApiOperation(value="商品管理-添加", notes="商品管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GoodsInfo goodsInfo) {
		GoodsInfo result = goodsInfoService.insertGoodsInfo(goodsInfo);
		return Result.OK(result);
	}

	/**
	 *  编辑
	 *
	 * @param goodsInfo
	 * @return
	 */
	@AutoLog(value = "商品管理-编辑")
	@ApiOperation(value="商品管理-编辑", notes="商品管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GoodsInfo goodsInfo) {
        goodsInfoService.updateGoodsInfo(goodsInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品管理-通过id删除")
	@ApiOperation(value="商品管理-通过id删除", notes="商品管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		goodsInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品管理-批量删除")
	@ApiOperation(value="商品管理-批量删除", notes="商品管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.goodsInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品管理-通过id查询")
	@ApiOperation(value="商品管理-通过id查询", notes="商品管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GoodsInfo goodsInfo = goodsInfoService.getById(id);
		if(goodsInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(goodsInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param goodsInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GoodsInfo goodsInfo) {
        return super.exportXls(request, goodsInfo, GoodsInfo.class, "goods_info");
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
        return super.importExcel(request, response, GoodsInfo.class);
    }

}
