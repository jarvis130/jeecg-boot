package org.jeecg.modules.comment.controller;

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
import org.jeecg.modules.comment.entity.GoodsComment;
import org.jeecg.modules.comment.service.IGoodsCommentService;
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
 * @Description: goods_comment
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Api(tags="goods_comment")
@RestController
@RequestMapping("/comment/goodsComment")
@Slf4j
public class GoodsCommentController extends JeecgController<GoodsComment, IGoodsCommentService> {
	@Autowired
	private IGoodsCommentService goodsCommentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param goodsComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "goods_comment-分页列表查询")
	@ApiOperation(value="goods_comment-分页列表查询", notes="goods_comment-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GoodsComment goodsComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GoodsComment> queryWrapper = QueryGenerator.initQueryWrapper(goodsComment, req.getParameterMap());
		Page<GoodsComment> page = new Page<GoodsComment>(pageNo, pageSize);
		IPage<GoodsComment> pageList = goodsCommentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param goodsComment
	 * @return
	 */
	@AutoLog(value = "goods_comment-添加")
	@ApiOperation(value="goods_comment-添加", notes="goods_comment-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GoodsComment goodsComment) {
		goodsCommentService.save(goodsComment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param goodsComment
	 * @return
	 */
	@AutoLog(value = "goods_comment-编辑")
	@ApiOperation(value="goods_comment-编辑", notes="goods_comment-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GoodsComment goodsComment) {
		goodsCommentService.updateById(goodsComment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "goods_comment-通过id删除")
	@ApiOperation(value="goods_comment-通过id删除", notes="goods_comment-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		goodsCommentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "goods_comment-批量删除")
	@ApiOperation(value="goods_comment-批量删除", notes="goods_comment-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.goodsCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "goods_comment-通过id查询")
	@ApiOperation(value="goods_comment-通过id查询", notes="goods_comment-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GoodsComment goodsComment = goodsCommentService.getById(id);
		if(goodsComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(goodsComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param goodsComment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GoodsComment goodsComment) {
        return super.exportXls(request, goodsComment, GoodsComment.class, "goods_comment");
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
        return super.importExcel(request, response, GoodsComment.class);
    }

}
