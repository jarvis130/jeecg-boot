package org.jeecg.modules.tech.controller;

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
import org.jeecg.modules.tech.entity.TechSpu;
import org.jeecg.modules.tech.service.ITechSpuService;

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
 * @Description: tech_spu
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Api(tags="tech_spu")
@RestController
@RequestMapping("/tech/techSpu")
@Slf4j
public class TechSpuController extends JeecgController<TechSpu, ITechSpuService> {
	@Autowired
	private ITechSpuService techSpuService;
	
	/**
	 * 分页列表查询
	 *
	 * @param techSpu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tech_spu-分页列表查询")
	@ApiOperation(value="tech_spu-分页列表查询", notes="tech_spu-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TechSpu techSpu,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TechSpu> queryWrapper = QueryGenerator.initQueryWrapper(techSpu, req.getParameterMap());
		Page<TechSpu> page = new Page<TechSpu>(pageNo, pageSize);
		IPage<TechSpu> pageList = techSpuService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param techSpu
	 * @return
	 */
	@AutoLog(value = "tech_spu-添加")
	@ApiOperation(value="tech_spu-添加", notes="tech_spu-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TechSpu techSpu) {
		techSpuService.save(techSpu);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param techSpu
	 * @return
	 */
	@AutoLog(value = "tech_spu-编辑")
	@ApiOperation(value="tech_spu-编辑", notes="tech_spu-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TechSpu techSpu) {
		techSpuService.updateById(techSpu);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tech_spu-通过id删除")
	@ApiOperation(value="tech_spu-通过id删除", notes="tech_spu-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		techSpuService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tech_spu-批量删除")
	@ApiOperation(value="tech_spu-批量删除", notes="tech_spu-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.techSpuService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tech_spu-通过id查询")
	@ApiOperation(value="tech_spu-通过id查询", notes="tech_spu-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TechSpu techSpu = techSpuService.getById(id);
		if(techSpu==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(techSpu);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param techSpu
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TechSpu techSpu) {
        return super.exportXls(request, techSpu, TechSpu.class, "tech_spu");
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
        return super.importExcel(request, response, TechSpu.class);
    }

}
