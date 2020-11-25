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
 * @Description: spu_detail
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("spu_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spu_detail对象", description="spu_detail")
public class SpuDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**spuId*/
	@Excel(name = "id", width = 15)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**商品描述信息*/
	@Excel(name = "商品描述信息", width = 15)
    @ApiModelProperty(value = "商品描述信息")
    private java.lang.String description;
	/**通用规格参数数据*/
	@Excel(name = "通用规格参数数据", width = 15)
    @ApiModelProperty(value = "通用规格参数数据")
    private java.lang.String genericSpec;
	/**特有规格参数及可选值信息，json格式*/
	@Excel(name = "特有规格参数及可选值信息，json格式", width = 15)
    @ApiModelProperty(value = "特有规格参数及可选值信息，json格式")
    private java.lang.String specialSpec;
	/**包装清单*/
	@Excel(name = "包装清单", width = 15)
    @ApiModelProperty(value = "包装清单")
    private java.lang.String packingList;
	/**售后服务*/
	@Excel(name = "售后服务", width = 15)
    @ApiModelProperty(value = "售后服务")
    private java.lang.String afterService;
}
