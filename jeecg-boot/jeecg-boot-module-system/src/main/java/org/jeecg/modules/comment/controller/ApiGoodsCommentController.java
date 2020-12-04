package org.jeecg.modules.comment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.Login;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.comment.entity.GoodsComment;
import org.jeecg.modules.comment.service.ICommentService;
import org.jeecg.modules.comment.service.IGoodsCommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags="api")
@RestController
@RequestMapping("/api/comment")
@Slf4j
public class ApiGoodsCommentController {

    @Resource
    private ICommentService gcommentService;

    @Resource
    private IGoodsCommentService goodsCommentService;

    /**
     *   添加
     *
     * @param goodsComment
     * @return
     */
    @Login
    @AutoLog(value = "goods_comment-评价添加")
    @ApiOperation(value="goods_comment-评价添加", notes="goods_comment-评价添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody GoodsComment goodsComment) {
        boolean result = gcommentService.save(goodsComment);
        if (result) {
            return Result.OK("评价成功！");
        }
        return Result.error("评价失败！");
    }

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
}