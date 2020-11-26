package org.jeecg.modules.commodity.entity;

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
 * @Description: spu_info
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("spu_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spu_info对象", description="spu_info")
public class SpuInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**spu id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "spu id")
    private java.lang.String id;

    /**spu类型*/
    @ApiModelProperty(value = "spu类型")
    private Integer spuType; //1是商品；2是方案；3是视频

    /**商品编号*/
    @Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    private java.lang.String code;

	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private java.lang.String title;

	/**子标题*/
	@Excel(name = "子标题", width = 15)
    @ApiModelProperty(value = "子标题")
    private java.lang.String subTitle;

	/**1级类目id*/
	@Excel(name = "1级类目id", width = 15)
    @ApiModelProperty(value = "1级类目id")
    private java.lang.String cid1;

	/**2级类目id*/
	@Excel(name = "2级类目id", width = 15)
    @ApiModelProperty(value = "2级类目id")
    private java.lang.String cid2;

	/**3级类目id*/
	@Excel(name = "3级类目id", width = 15)
    @ApiModelProperty(value = "3级类目id")
    @Dict(dicCode = "id",dictTable="sys_category",dicText="name")
    private java.lang.String cid3;

	/**商品所属品牌id*/
	@Excel(name = "商品所属品牌id", width = 15)
    @ApiModelProperty(value = "商品所属品牌id")
    private java.lang.Integer brandId;

	/**是否上架，0下架，1上架*/
	@Excel(name = "是否上架，0下架，1上架", width = 15)
    @ApiModelProperty(value = "是否上架，0下架，1上架")
    private java.lang.Boolean isOnSale;

	/**是否有效，0已删除，1有效*/
	@Excel(name = "是否有效，0已删除，1有效", width = 15)
    @ApiModelProperty(value = "是否有效，0已删除，1有效")
    private java.lang.Integer delFlag;

	/**添加时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "添加时间")
    private java.util.Date createTime;

	/**最后修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后修改时间")
    private java.util.Date updateTime;

    /**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;

    /**创建者*/
    @ApiModelProperty(value = "创建者")
    private java.lang.String createBy;

    /**是否实物商品，是1，否2*/
    @Excel(name = "是否实物商品，是1，否2", width = 15)
    @ApiModelProperty(value = "是否实物商品，是1，否2")
    private java.lang.Integer isReal;

    /**外部编码*/
    @Excel(name = "外部编码", width = 15)
    @ApiModelProperty(value = "外部编码")
    private java.lang.String extensionCode;

    /**是否新品*/
    @Excel(name = "是否新品", width = 15)
    @ApiModelProperty(value = "是否新品")
    private java.lang.Boolean isNew;

    /**是否热卖*/
    @Excel(name = "是否热卖", width = 15)
    @ApiModelProperty(value = "是否热卖")
    private java.lang.Boolean isHot;

    /**是否推荐*/
    @Excel(name = "是否推荐", width = 15)
    @ApiModelProperty(value = "是否推荐")
    private java.lang.Boolean isRecommend;

    /**租户编号*/
    @Excel(name = "租户编号", width = 15)
    @ApiModelProperty(value = "租户编号")
    private java.lang.String tenantId;

    /**关键词*/
    @Excel(name = "关键词", width = 15)
    @ApiModelProperty(value = "关键词")
    private java.lang.String keywords;

    /**默认缩略图*/
    @Excel(name = "默认缩略图", width = 15)
    @ApiModelProperty(value = "默认缩略图")
    private java.lang.String thumbs;

    /**默认压缩后图片*/
    @Excel(name = "默认压缩后图片", width = 15)
    @ApiModelProperty(value = "默认压缩后图片")
    private java.lang.String images;

    /**市场价*/
    @Excel(name = "市场价", width = 15)
    @ApiModelProperty(value = "市场价")
    private java.math.BigDecimal marketPrice;

    @Excel(name = "平台价", width = 15)
    @ApiModelProperty(value = "平台价")
    private java.math.BigDecimal salePrice;

    /**商品简述*/
    @Excel(name = "商品简述", width = 15)
    @ApiModelProperty(value = "商品简述")
    private java.lang.String brief;

    /**是否启用sku编辑，1是，0否*/
    @Excel(name = "是否启用sku编辑，1是，0否", width = 15)
    @ApiModelProperty(value = "是否启用sku编辑，1是，0否")
    private java.lang.Boolean enableSpecialSpec;

    /**是否启用attribute编辑，1是，0否*/
    @Excel(name = "是否启用attribute编辑，1是，0否", width = 15)
    @ApiModelProperty(value = "是否启用sku编辑，1是，0否")
    private java.lang.Boolean enableGenericSpec;

}
