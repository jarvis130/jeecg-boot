package org.jeecg.modules.inventory.controller;

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
import org.jeecg.modules.inventory.entity.InventoryInfo;
import org.jeecg.modules.inventory.service.IInventoryInfoService;

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
 * @Description: inventory_info
 * @Author: jeecg-boot
 * @Date:   2020-11-09
 * @Version: V1.0
 */
@Api(tags="inventory_info")
@RestController
@RequestMapping("/inventory/inventoryInfo")
@Slf4j
public class InventoryInfoController extends JeecgController<InventoryInfo, IInventoryInfoService> {
	@Autowired
	private IInventoryInfoService inventoryInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param inventoryInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "inventory_info-分页列表查询")
	@ApiOperation(value="inventory_info-分页列表查询", notes="inventory_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InventoryInfo inventoryInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InventoryInfo> queryWrapper = QueryGenerator.initQueryWrapper(inventoryInfo, req.getParameterMap());
		Page<InventoryInfo> page = new Page<InventoryInfo>(pageNo, pageSize);
		IPage<InventoryInfo> pageList = inventoryInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param inventoryInfo
	 * @return
	 */
	@AutoLog(value = "inventory_info-添加")
	@ApiOperation(value="inventory_info-添加", notes="inventory_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InventoryInfo inventoryInfo) {
		inventoryInfoService.save(inventoryInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param inventoryInfo
	 * @return
	 */
	@AutoLog(value = "inventory_info-编辑")
	@ApiOperation(value="inventory_info-编辑", notes="inventory_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InventoryInfo inventoryInfo) {
		inventoryInfoService.updateById(inventoryInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inventory_info-通过id删除")
	@ApiOperation(value="inventory_info-通过id删除", notes="inventory_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inventoryInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "inventory_info-批量删除")
	@ApiOperation(value="inventory_info-批量删除", notes="inventory_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inventoryInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "inventory_info-通过id查询")
	@ApiOperation(value="inventory_info-通过id查询", notes="inventory_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InventoryInfo inventoryInfo = inventoryInfoService.getById(id);
		if(inventoryInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(inventoryInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inventoryInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InventoryInfo inventoryInfo) {
        return super.exportXls(request, inventoryInfo, InventoryInfo.class, "inventory_info");
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
        return super.importExcel(request, response, InventoryInfo.class);
    }

}
