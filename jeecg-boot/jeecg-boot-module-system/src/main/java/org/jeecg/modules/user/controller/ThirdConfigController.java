package org.jeecg.modules.user.controller;

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
import org.jeecg.modules.user.entity.ThirdConfig;
import org.jeecg.modules.user.service.IThirdConfigService;
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
 * @Description: third_config
 * @Author: jeecg-boot
 * @Date:   2020-12-03
 * @Version: V1.0
 */
@Api(tags="third_config")
@RestController
@RequestMapping("/user/thirdConfig")
@Slf4j
public class ThirdConfigController extends JeecgController<ThirdConfig, IThirdConfigService> {
	@Autowired
	private IThirdConfigService thirdConfigService;
	
	/**
	 * 分页列表查询
	 *
	 * @param thirdConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "third_config-分页列表查询")
	@ApiOperation(value="third_config-分页列表查询", notes="third_config-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ThirdConfig thirdConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ThirdConfig> queryWrapper = QueryGenerator.initQueryWrapper(thirdConfig, req.getParameterMap());
		Page<ThirdConfig> page = new Page<ThirdConfig>(pageNo, pageSize);
		IPage<ThirdConfig> pageList = thirdConfigService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param thirdConfig
	 * @return
	 */
	@AutoLog(value = "third_config-添加")
	@ApiOperation(value="third_config-添加", notes="third_config-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ThirdConfig thirdConfig) {
		thirdConfigService.save(thirdConfig);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param thirdConfig
	 * @return
	 */
	@AutoLog(value = "third_config-编辑")
	@ApiOperation(value="third_config-编辑", notes="third_config-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ThirdConfig thirdConfig) {
		thirdConfigService.updateById(thirdConfig);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "third_config-通过id删除")
	@ApiOperation(value="third_config-通过id删除", notes="third_config-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		thirdConfigService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "third_config-批量删除")
	@ApiOperation(value="third_config-批量删除", notes="third_config-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.thirdConfigService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "third_config-通过id查询")
	@ApiOperation(value="third_config-通过id查询", notes="third_config-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ThirdConfig thirdConfig = thirdConfigService.getById(id);
		if(thirdConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(thirdConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param thirdConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ThirdConfig thirdConfig) {
        return super.exportXls(request, thirdConfig, ThirdConfig.class, "third_config");
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
        return super.importExcel(request, response, ThirdConfig.class);
    }

}
