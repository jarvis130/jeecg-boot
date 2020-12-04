package org.jeecg.modules.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: goods_comment
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Data
@TableName("goods_comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="goods_comment对象", description="goods_comment")
public class GoodsComment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**评价ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "评价ID")
    private java.lang.String id;
	/**评价1-5星*/
	@Excel(name = "评价1-5星", width = 15)
    @ApiModelProperty(value = "评价1-5星")
    private java.lang.Integer score;
	/**评价用户ID*/
	@Excel(name = "评价用户ID", width = 15)
    @ApiModelProperty(value = "评价用户ID")
    private java.lang.String userId;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private java.lang.String tanentId;
	/**商品ID 关联goods.id*/
	@Excel(name = "商品ID 关联goods.id", width = 15)
    @ApiModelProperty(value = "商品ID 关联goods.id")
    private java.lang.String goodsId;
	/**评价订单ID*/
	@Excel(name = "评价订单ID", width = 15)
    @ApiModelProperty(value = "评价订单ID")
    private java.lang.Integer orderId;
	/**货品规格序列号存储*/
	@Excel(name = "货品规格序列号存储", width = 15)
    @ApiModelProperty(value = "货品规格序列号存储")
    private java.lang.Object addon;
	/**评价图片逗号分隔最多五张*/
	@Excel(name = "评价图片逗号分隔最多五张", width = 15)
    @ApiModelProperty(value = "评价图片逗号分隔最多五张")
    private java.lang.Object images;
	/**评价视频*/
	@Excel(name = "评价视频", width = 15)
    @ApiModelProperty(value = "评价视频")
    private java.lang.Object video;
	/**评价内容*/
	@Excel(name = "评价内容", width = 15)
    @ApiModelProperty(value = "评价内容")
    private java.lang.Object content;
	/**商家回复*/
	@Excel(name = "商家回复", width = 15)
    @ApiModelProperty(value = "商家回复")
    private java.lang.Object sellerContent;
	/**是否显示 1显示 2不显示*/
	@Excel(name = "是否显示 1显示 2不显示", width = 15)
    @ApiModelProperty(value = "是否显示 1显示 2不显示")
    private java.lang.Integer display;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
