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
    private java.lang.Integer cid1;
	/**2级类目id*/
	@Excel(name = "2级类目id", width = 15)
    @ApiModelProperty(value = "2级类目id")
    private java.lang.Integer cid2;
	/**3级类目id*/
	@Excel(name = "3级类目id", width = 15)
    @ApiModelProperty(value = "3级类目id")
    private java.lang.Integer cid3;
	/**商品所属品牌id*/
	@Excel(name = "商品所属品牌id", width = 15)
    @ApiModelProperty(value = "商品所属品牌id")
    private java.lang.Integer brandId;
	/**是否上架，0下架，1上架*/
	@Excel(name = "是否上架，0下架，1上架", width = 15)
    @ApiModelProperty(value = "是否上架，0下架，1上架")
    private java.lang.Integer onSale;
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
}
