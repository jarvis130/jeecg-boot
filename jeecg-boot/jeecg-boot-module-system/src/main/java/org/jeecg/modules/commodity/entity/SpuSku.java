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
 * @Description: spu_sku
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("spu_sku")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spu_sku对象", description="spu_sku")
public class SpuSku implements Serializable {
    private static final long serialVersionUID = 1L;

	/**sku id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "sku id")
    private java.lang.String id;
	/**spu id*/
	@Excel(name = "spu id", width = 15)
    @ApiModelProperty(value = "spu id")
    private java.lang.String spuId;
	/**商品标题*/
	@Excel(name = "商品标题", width = 15)
    @ApiModelProperty(value = "商品标题")
    private java.lang.String title;
	/**商品的图片，多个图片以‘,’分割*/
	@Excel(name = "商品的图片，多个图片以‘,’分割", width = 15)
    @ApiModelProperty(value = "商品的图片，多个图片以‘,’分割")
    private java.lang.String images;
	/**销售价格，单位为分*/
	@Excel(name = "销售价格，单位为分", width = 15)
    @ApiModelProperty(value = "销售价格，单位为分")
    private java.lang.Integer price;
	/**特有规格属性在spu属性模板中的对应下标组合*/
	@Excel(name = "特有规格属性在spu属性模板中的对应下标组合", width = 15)
    @ApiModelProperty(value = "特有规格属性在spu属性模板中的对应下标组合")
    private java.lang.String indexes;
	/**sku的特有规格参数，json格式*/
	@Excel(name = "sku的特有规格参数，json格式", width = 15)
    @ApiModelProperty(value = "sku的特有规格参数，json格式")
    private java.lang.String ownSpec;
	/**是否有效，0无效，1有效*/
	@Excel(name = "是否有效，0无效，1有效", width = 15)
    @ApiModelProperty(value = "是否有效，0无效，1有效")
    private java.lang.Integer status;
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
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
}
