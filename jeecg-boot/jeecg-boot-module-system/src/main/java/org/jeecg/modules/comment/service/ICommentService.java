package org.jeecg.modules.comment.service;

import org.jeecg.modules.comment.entity.GoodsComment;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: ICommentService
 * Author:   kehaojian
 * Date:     2020/12/4 18:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface ICommentService {
    boolean save(GoodsComment goodsComment);
}