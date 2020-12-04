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
import org.jeecg.modules.tech.entity.TechArrange;
import org.jeecg.modules.tech.service.ITechArrangeService;

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
 * @Description: tech_arrange
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Api(tags="tech_arrange")
@RestController
@RequestMapping("/tech/techArrange")
@Slf4j
public class TechArrangeController extends JeecgController<TechArrange, ITechArrangeService> {
	@Autowired
	private ITechArrangeService techArrangeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param techArrange
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tech_arrange-分页列表查询")
	@ApiOperation(value="tech_arrange-分页列表查询", notes="tech_arrange-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TechArrange techArrange,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TechArrange> queryWrapper = QueryGenerator.initQueryWrapper(techArrange, req.getParameterMap());
		Page<TechArrange> page = new Page<TechArrange>(pageNo, pageSize);
		IPage<TechArrange> pageList = techArrangeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param techArrange
	 * @return
	 */
	@AutoLog(value = "tech_arrange-添加")
	@ApiOperation(value="tech_arrange-添加", notes="tech_arrange-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TechArrange techArrange) {
		techArrangeService.save(techArrange);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param techArrange
	 * @return
	 */
	@AutoLog(value = "tech_arrange-编辑")
	@ApiOperation(value="tech_arrange-编辑", notes="tech_arrange-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TechArrange techArrange) {
		techArrangeService.updateById(techArrange);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tech_arrange-通过id删除")
	@ApiOperation(value="tech_arrange-通过id删除", notes="tech_arrange-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		techArrangeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tech_arrange-批量删除")
	@ApiOperation(value="tech_arrange-批量删除", notes="tech_arrange-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.techArrangeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tech_arrange-通过id查询")
	@ApiOperation(value="tech_arrange-通过id查询", notes="tech_arrange-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TechArrange techArrange = techArrangeService.getById(id);
		if(techArrange==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(techArrange);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param techArrange
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TechArrange techArrange) {
        return super.exportXls(request, techArrange, TechArrange.class, "tech_arrange");
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
        return super.importExcel(request, response, TechArrange.class);
    }

}
