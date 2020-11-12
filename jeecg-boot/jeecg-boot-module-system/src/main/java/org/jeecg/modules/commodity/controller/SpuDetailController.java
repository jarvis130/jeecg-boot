package org.jeecg.modules.commodity.controller;

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
import org.jeecg.modules.commodity.entity.SpuDetail;
import org.jeecg.modules.commodity.service.ISpuDetailService;

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
 * @Description: spu_detail
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Api(tags="spu_detail")
@RestController
@RequestMapping("/commodity/spuDetail")
@Slf4j
public class SpuDetailController extends JeecgController<SpuDetail, ISpuDetailService> {
	@Autowired
	private ISpuDetailService spuDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param spuDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "spu_detail-分页列表查询")
	@ApiOperation(value="spu_detail-分页列表查询", notes="spu_detail-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SpuDetail spuDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SpuDetail> queryWrapper = QueryGenerator.initQueryWrapper(spuDetail, req.getParameterMap());
		Page<SpuDetail> page = new Page<SpuDetail>(pageNo, pageSize);
		IPage<SpuDetail> pageList = spuDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param spuDetail
	 * @return
	 */
	@AutoLog(value = "spu_detail-添加")
	@ApiOperation(value="spu_detail-添加", notes="spu_detail-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SpuDetail spuDetail) {
		spuDetailService.save(spuDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param spuDetail
	 * @return
	 */
	@AutoLog(value = "spu_detail-编辑")
	@ApiOperation(value="spu_detail-编辑", notes="spu_detail-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SpuDetail spuDetail) {
		spuDetailService.updateById(spuDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_detail-通过id删除")
	@ApiOperation(value="spu_detail-通过id删除", notes="spu_detail-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		spuDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "spu_detail-批量删除")
	@ApiOperation(value="spu_detail-批量删除", notes="spu_detail-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.spuDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_detail-通过id查询")
	@ApiOperation(value="spu_detail-通过id查询", notes="spu_detail-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SpuDetail spuDetail = spuDetailService.getById(id);
		if(spuDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(spuDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param spuDetail
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SpuDetail spuDetail) {
        return super.exportXls(request, spuDetail, SpuDetail.class, "spu_detail");
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
        return super.importExcel(request, response, SpuDetail.class);
    }

}
