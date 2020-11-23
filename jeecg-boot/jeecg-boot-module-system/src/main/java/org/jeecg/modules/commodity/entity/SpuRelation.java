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
 * @Description: spu_relation
 * @Author: jeecg-boot
 * @Date:   2020-11-23
 * @Version: V1.0
 */
@Data
@TableName("spu_relation")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spu_relation对象", description="spu_relation")
public class SpuRelation implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    private java.lang.String spuId;
	/**关联的商品编号*/
	@Excel(name = "关联的商品编号", width = 15)
    @ApiModelProperty(value = "关联的商品编号")
    private java.lang.String relationId;
	/**使用商品数量，医药方案场景使用*/
	@Excel(name = "使用商品数量，医药方案场景使用", width = 15)
    @ApiModelProperty(value = "使用商品数量，医药方案场景使用")
    private java.lang.Integer useNum;
	/**使用的商品规格*/
	@Excel(name = "使用的商品规格", width = 15)
    @ApiModelProperty(value = "使用的商品规格")
    private java.lang.String skuId;
	/**商品价格*/
	@Excel(name = "商品价格", width = 15)
    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal price;
}
