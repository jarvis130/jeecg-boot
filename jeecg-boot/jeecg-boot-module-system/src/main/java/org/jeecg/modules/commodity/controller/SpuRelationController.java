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
import org.jeecg.modules.commodity.entity.SpuRelation;
import org.jeecg.modules.commodity.entity.SpuSku;
import org.jeecg.modules.commodity.mapper.SpuRelationMapper;
import org.jeecg.modules.commodity.service.ISpuRelationService;

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
 * @Description: spu_relation
 * @Author: jeecg-boot
 * @Date:   2020-11-23
 * @Version: V1.0
 */
@Api(tags="spu_relation")
@RestController
@RequestMapping("/commodity/spuRelation")
@Slf4j
public class SpuRelationController extends JeecgController<SpuRelation, ISpuRelationService> {
	@Autowired
	private ISpuRelationService spuRelationService;

	 @Autowired
	 private SpuRelationMapper spuRelationMapper;
	/**
	 * 分页列表查询
	 *
	 * @param spuRelation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "spu_relation-分页列表查询")
	@ApiOperation(value="spu_relation-分页列表查询", notes="spu_relation-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SpuRelation spuRelation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SpuRelation> queryWrapper = QueryGenerator.initQueryWrapper(spuRelation, req.getParameterMap());
		Page<SpuRelation> page = new Page<SpuRelation>(pageNo, pageSize);
		IPage<SpuRelation> pageList = spuRelationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param spuRelation
	 * @return
	 */
	@AutoLog(value = "spu_relation-添加")
	@ApiOperation(value="spu_relation-添加", notes="spu_relation-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SpuRelation spuRelation) {
		spuRelationService.save(spuRelation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param spuRelation
	 * @return
	 */
	@AutoLog(value = "spu_relation-编辑")
	@ApiOperation(value="spu_relation-编辑", notes="spu_relation-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SpuRelation spuRelation) {
		spuRelationService.updateById(spuRelation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_relation-通过id删除")
	@ApiOperation(value="spu_relation-通过id删除", notes="spu_relation-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		spuRelationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "spu_relation-批量删除")
	@ApiOperation(value="spu_relation-批量删除", notes="spu_relation-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.spuRelationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_relation-通过id查询")
	@ApiOperation(value="spu_relation-通过id查询", notes="spu_relation-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SpuRelation spuRelation = spuRelationService.getById(id);
		if(spuRelation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(spuRelation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param spuRelation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SpuRelation spuRelation) {
        return super.exportXls(request, spuRelation, SpuRelation.class, "spu_relation");
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
        return super.importExcel(request, response, SpuRelation.class);
    }

	 /**
	  * 通过spuId查询
	  *
	  * @param spuId
	  * @return
	  */
	 @AutoLog(value = "spu_relation-通过spuId查询")
	 @ApiOperation(value="spu_relation-通过spuId查询", notes="spu_relation-通过spuId查询")
	 @GetMapping(value = "/queryBySpuId")
	 public Result<?> queryBySpuId(@RequestParam(name="spuId",required=true) String spuId) {

		 QueryWrapper<SpuRelation> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("spu_id", spuId);
		 List<SpuRelation> list = spuRelationMapper.selectList(queryWrapper);

		 return Result.OK(list);
	 }

}
