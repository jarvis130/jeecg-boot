package org.jeecg.modules.order.controller;

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
import org.jeecg.modules.order.entity.OrderInfo;
import org.jeecg.modules.order.service.IOrderInfoService;

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
 * @Description: order_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Api(tags="order_info")
@RestController
@RequestMapping("/order/orderInfo")
@Slf4j
public class OrderInfoController extends JeecgController<OrderInfo, IOrderInfoService> {
	@Autowired
	private IOrderInfoService orderInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param orderInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "order_info-分页列表查询")
	@ApiOperation(value="order_info-分页列表查询", notes="order_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderInfo orderInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderInfo> queryWrapper = QueryGenerator.initQueryWrapper(orderInfo, req.getParameterMap());
		Page<OrderInfo> page = new Page<OrderInfo>(pageNo, pageSize);
		IPage<OrderInfo> pageList = orderInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param orderInfo
	 * @return
	 */
	@AutoLog(value = "order_info-添加")
	@ApiOperation(value="order_info-添加", notes="order_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OrderInfo orderInfo) {
		orderInfoService.save(orderInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param orderInfo
	 * @return
	 */
	@AutoLog(value = "order_info-编辑")
	@ApiOperation(value="order_info-编辑", notes="order_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OrderInfo orderInfo) {
		orderInfoService.updateById(orderInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "order_info-通过id删除")
	@ApiOperation(value="order_info-通过id删除", notes="order_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		orderInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "order_info-批量删除")
	@ApiOperation(value="order_info-批量删除", notes="order_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.orderInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "order_info-通过id查询")
	@ApiOperation(value="order_info-通过id查询", notes="order_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(orderInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param orderInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderInfo orderInfo) {
        return super.exportXls(request, orderInfo, OrderInfo.class, "order_info");
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
        return super.importExcel(request, response, OrderInfo.class);
    }

}
