package org.jeecg.modules.goods.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: goods_info
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Data
@TableName("goods_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="goods_info对象", description="goods_info")
public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**商品主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "商品主键")
    private java.lang.String id;
	/**商品分类*/
	@Excel(name = "商品分类", width = 15)
    @ApiModelProperty(value = "商品分类")
    private java.lang.String catId;
	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    private java.lang.String goodsSn;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**商品分类；1商品；2方案；3视频*/
	@Excel(name = "商品分类；1商品；2方案；3视频", width = 15)
    @ApiModelProperty(value = "商品分类；1商品；2方案；3视频")
    private java.lang.Integer goodsType;
	/**品牌编号*/
	@Excel(name = "品牌编号", width = 15)
    @ApiModelProperty(value = "品牌编号")
    private java.lang.String brandId;
	/**市场价*/
	@Excel(name = "市场价", width = 15)
    @ApiModelProperty(value = "市场价")
    private java.math.BigDecimal marketPrice;
    @Excel(name = "平台价", width = 15)
    @ApiModelProperty(value = "平台价")
    private java.math.BigDecimal salePrice;
	/**关键词*/
	@Excel(name = "关键词", width = 15)
    @ApiModelProperty(value = "关键词")
    private java.lang.String keywords;
	/**原始图片*/
	@Excel(name = "原始图片", width = 15)
    @ApiModelProperty(value = "原始图片")
    private java.lang.String originalImg;
	/**默认缩略图*/
	@Excel(name = "默认缩略图", width = 15)
    @ApiModelProperty(value = "默认缩略图")
    private java.lang.String goodsThumb;
	/**默认压缩后图片*/
	@Excel(name = "默认压缩后图片", width = 15)
    @ApiModelProperty(value = "默认压缩后图片")
    private java.lang.String goodsImg;
	/**商品简述*/
	@Excel(name = "商品简述", width = 15)
    @ApiModelProperty(value = "商品简述")
    private java.lang.String goodsBrief;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
    private java.lang.String goodsDesc;
	/**是否实物商品，是1，否2*/
	@Excel(name = "是否实物商品，是1，否2", width = 15)
    @ApiModelProperty(value = "是否实物商品，是1，否2")
    private java.lang.String isReal;
	/**外部编码*/
	@Excel(name = "外部编码", width = 15)
    @ApiModelProperty(value = "外部编码")
    private java.lang.String extensionCode;
	/**是否上架*/
	@Excel(name = "是否上架", width = 15)
    @ApiModelProperty(value = "是否上架")
    private java.lang.Boolean isOnSale;
	/**是否推荐*/
	@Excel(name = "是否推荐", width = 15)
    @ApiModelProperty(value = "是否推荐")
    private java.lang.String isBest;
	/**是否新品*/
	@Excel(name = "是否新品", width = 15)
    @ApiModelProperty(value = "是否新品")
    private java.lang.String isNew;
	/**是否热卖*/
	@Excel(name = "是否热卖", width = 15)
    @ApiModelProperty(value = "是否热卖")
    private java.lang.String isHot;
	/**是否推荐*/
	@Excel(name = "是否推荐", width = 15)
    @ApiModelProperty(value = "是否推荐")
    private java.lang.String isPromote;
	/**租户编号*/
	@Excel(name = "租户编号", width = 15)
    @ApiModelProperty(value = "租户编号")
    private java.lang.String tenantId;

	/**是否启用sku编辑，1是，0否*/
	@Excel(name = "是否启用sku编辑，1是，0否", width = 15)
    @ApiModelProperty(value = "是否启用sku编辑，1是，0否")
    private java.lang.Boolean enableSku;
	/**规格json数据，用于前台页面展示*/
	@Excel(name = "规格json数据，用于前台页面展示", width = 15)
    @ApiModelProperty(value = "规格json数据，用于前台页面展示")
    private java.lang.String skuJsonData;

    /**是否启用attribute编辑，1是，0否*/
    @Excel(name = "是否启用attribute编辑，1是，0否", width = 15)
    @ApiModelProperty(value = "是否启用sku编辑，1是，0否")
    private java.lang.Boolean enableAttribute;

	/**属性json数据，用于前台页面展示*/
	@Excel(name = "属性json数据，用于前台页面展示", width = 15)
    @ApiModelProperty(value = "属性json数据，用于前台页面展示")
    private java.lang.String attributeJsonData;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sortOrder;
	/**删除标志（1代表存在 2代表删除）*/
	@Excel(name = "删除标志（1代表存在 2代表删除）", width = 15)
    @ApiModelProperty(value = "删除标志（1代表存在 2代表删除）")
    private java.lang.String delFlag;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private java.lang.String createBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
