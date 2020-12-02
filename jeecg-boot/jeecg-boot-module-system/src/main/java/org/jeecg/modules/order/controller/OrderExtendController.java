package org.jeecg.modules.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.order.entity.OrderExtend;
import org.jeecg.modules.order.service.IOrderExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: order_extend
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Api(tags="order_extend")
@RestController
@RequestMapping("/order/orderExtend")
@Slf4j
public class OrderExtendController extends JeecgController<OrderExtend, IOrderExtendService> {
	@Autowired
	private IOrderExtendService orderExtendService;
	
	/**
	 * 分页列表查询
	 *
	 * @param orderExtend
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "order_extend-分页列表查询")
	@ApiOperation(value="order_extend-分页列表查询", notes="order_extend-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderExtend orderExtend,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderExtend> queryWrapper = QueryGenerator.initQueryWrapper(orderExtend, req.getParameterMap());
		Page<OrderExtend> page = new Page<OrderExtend>(pageNo, pageSize);
		IPage<OrderExtend> pageList = orderExtendService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param orderExtend
	 * @return
	 */
	@AutoLog(value = "order_extend-添加")
	@ApiOperation(value="order_extend-添加", notes="order_extend-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OrderExtend orderExtend) {
		orderExtendService.save(orderExtend);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param orderExtend
	 * @return
	 */
	@AutoLog(value = "order_extend-编辑")
	@ApiOperation(value="order_extend-编辑", notes="order_extend-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OrderExtend orderExtend) {
		orderExtendService.updateById(orderExtend);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "order_extend-通过id删除")
	@ApiOperation(value="order_extend-通过id删除", notes="order_extend-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		orderExtendService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "order_extend-批量删除")
	@ApiOperation(value="order_extend-批量删除", notes="order_extend-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.orderExtendService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "order_extend-通过id查询")
	@ApiOperation(value="order_extend-通过id查询", notes="order_extend-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OrderExtend orderExtend = orderExtendService.getById(id);
		if(orderExtend==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(orderExtend);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param orderExtend
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderExtend orderExtend) {
        return super.exportXls(request, orderExtend, OrderExtend.class, "order_extend");
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
        return super.importExcel(request, response, OrderExtend.class);
    }

}
