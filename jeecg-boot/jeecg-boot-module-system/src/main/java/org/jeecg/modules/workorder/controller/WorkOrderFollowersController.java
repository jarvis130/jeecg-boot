package org.jeecg.modules.workorder.controller;

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
import org.jeecg.modules.workorder.entity.WorkOrderFollowers;
import org.jeecg.modules.workorder.service.IWorkOrderFollowersService;

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
 * @Description: work_order_followers
 * @Author: jeecg-boot
 * @Date:   2020-09-30
 * @Version: V1.0
 */
@Api(tags="work_order_followers")
@RestController
@RequestMapping("/workorder/workOrderFollowers")
@Slf4j
public class WorkOrderFollowersController extends JeecgController<WorkOrderFollowers, IWorkOrderFollowersService> {
	@Autowired
	private IWorkOrderFollowersService workOrderFollowersService;
	
	/**
	 * 分页列表查询
	 *
	 * @param workOrderFollowers
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "work_order_followers-分页列表查询")
	@ApiOperation(value="work_order_followers-分页列表查询", notes="work_order_followers-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WorkOrderFollowers workOrderFollowers,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WorkOrderFollowers> queryWrapper = QueryGenerator.initQueryWrapper(workOrderFollowers, req.getParameterMap());
		Page<WorkOrderFollowers> page = new Page<WorkOrderFollowers>(pageNo, pageSize);
		IPage<WorkOrderFollowers> pageList = workOrderFollowersService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param workOrderFollowers
	 * @return
	 */
	@AutoLog(value = "work_order_followers-添加")
	@ApiOperation(value="work_order_followers-添加", notes="work_order_followers-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WorkOrderFollowers workOrderFollowers) {
		workOrderFollowersService.save(workOrderFollowers);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param workOrderFollowers
	 * @return
	 */
	@AutoLog(value = "work_order_followers-编辑")
	@ApiOperation(value="work_order_followers-编辑", notes="work_order_followers-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WorkOrderFollowers workOrderFollowers) {
		workOrderFollowersService.updateById(workOrderFollowers);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "work_order_followers-通过id删除")
	@ApiOperation(value="work_order_followers-通过id删除", notes="work_order_followers-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		workOrderFollowersService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "work_order_followers-批量删除")
	@ApiOperation(value="work_order_followers-批量删除", notes="work_order_followers-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.workOrderFollowersService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "work_order_followers-通过id查询")
	@ApiOperation(value="work_order_followers-通过id查询", notes="work_order_followers-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WorkOrderFollowers workOrderFollowers = workOrderFollowersService.getById(id);
		if(workOrderFollowers==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(workOrderFollowers);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param workOrderFollowers
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WorkOrderFollowers workOrderFollowers) {
        return super.exportXls(request, workOrderFollowers, WorkOrderFollowers.class, "work_order_followers");
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
        return super.importExcel(request, response, WorkOrderFollowers.class);
    }

}
