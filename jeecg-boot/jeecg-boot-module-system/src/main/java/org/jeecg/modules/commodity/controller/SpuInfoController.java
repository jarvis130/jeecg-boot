package org.jeecg.modules.commodity.controller;

import java.util.ArrayList;
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
import org.jeecg.modules.commodity.entity.SpuInfo;
import org.jeecg.modules.commodity.entity.SpuInfoVO;
import org.jeecg.modules.commodity.service.ISpuDetailService;
import org.jeecg.modules.commodity.service.ISpuInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: spu_info
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Api(tags="spu_info")
@RestController
@RequestMapping("/commodity/spuInfo")
@Slf4j
public class SpuInfoController extends JeecgController<SpuInfo, ISpuInfoService> {
	@Autowired
	private ISpuInfoService spuInfoService;

	@Autowired
	private ISpuDetailService spuDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param spuInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "spu_info-分页列表查询")
	@ApiOperation(value="spu_info-分页列表查询", notes="spu_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SpuInfo spuInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		QueryWrapper<SpuInfo> queryWrapper = QueryGenerator.initQueryWrapper(spuInfo, req.getParameterMap());
		Page<SpuInfo> page = new Page<SpuInfo>(pageNo, pageSize);
		IPage<SpuInfo> pageList = spuInfoService.page(page, queryWrapper);

		//批量查询明细数据
		List<String> ids = pageList.getRecords().stream().map(SpuInfo::getId).collect(Collectors.toList());

		List<SpuInfoVO> spuInfoVOList = new ArrayList<>();

		if(ids!=null && ids.size()>0){
			Map<String, SpuDetail>  spuDetails = spuDetailService.getSpuDetailByIds(ids);
			pageList.getRecords().forEach(item->{
				SpuDetail spuDetail = spuDetails.get(item.getId());
				SpuInfoVO vo = new SpuInfoVO();
				BeanUtils.copyProperties(item, vo);
				if(spuDetail != null){
					BeanUtils.copyProperties(spuDetail, vo);
				}

				spuInfoVOList.add(vo);
			});
		}

		Page<SpuInfoVO> list = new Page<SpuInfoVO>();
		list.hitCount(pageList.isHitCount());
		list.setRecords(spuInfoVOList);
		list.setCurrent(pageList.getCurrent());
		list.setSize(pageList.getSize());
		list.setTotal(pageList.getTotal());
		list.setSearchCount(pageList.isSearchCount());
		list.setOptimizeCountSql(pageList.optimizeCountSql());

		return Result.OK(list);
	}
	
	/**
	 *   添加
	 *
	 * @param spuInfoVO
	 * @return
	 */
	@AutoLog(value = "spu_info-添加")
	@ApiOperation(value="spu_info-添加", notes="spu_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SpuInfoVO spuInfoVO) {
		SpuInfoVO entity = spuInfoService.saveSpu(spuInfoVO);
		return Result.OK(entity);
	}
	
	/**
	 *  编辑
	 *
	 * @param spuInfoVO
	 * @return
	 */
	@AutoLog(value = "spu_info-编辑")
	@ApiOperation(value="spu_info-编辑", notes="spu_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SpuInfoVO spuInfoVO) {
		SpuInfoVO entity = spuInfoService.updateSpu(spuInfoVO);
		return Result.OK(entity);
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_info-通过id删除")
	@ApiOperation(value="spu_info-通过id删除", notes="spu_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		spuInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "spu_info-批量删除")
	@ApiOperation(value="spu_info-批量删除", notes="spu_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.spuInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "spu_info-通过id查询")
	@ApiOperation(value="spu_info-通过id查询", notes="spu_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SpuInfo spuInfo = spuInfoService.getById(id);
		if(spuInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(spuInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param spuInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SpuInfo spuInfo) {
        return super.exportXls(request, spuInfo, SpuInfo.class, "spu_info");
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
        return super.importExcel(request, response, SpuInfo.class);
    }

}
