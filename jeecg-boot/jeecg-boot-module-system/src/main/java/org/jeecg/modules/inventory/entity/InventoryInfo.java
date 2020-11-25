package org.jeecg.modules.inventory.entity;

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
 * @Description: inventory_info
 * @Author: jeecg-boot
 * @Date:   2020-11-23
 * @Version: V1.0
 */
@Data
@TableName("inventory_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inventory_info对象", description="inventory_info")
public class InventoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**规格主键，sku_id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "规格主键，sku_id")
    @Dict(dicCode = "id",dictTable="spu_sku",dicText="sku_key")
    private java.lang.String id;

	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    @Dict(dicCode = "id",dictTable="spu_info",dicText="title")
    private java.lang.String spuId;

	/**有效库存*/
	@Excel(name = "有效库存", width = 15)
    @ApiModelProperty(value = "有效库存")
    private java.lang.Integer validStock;

	/**无效库存,残次品*/
	@Excel(name = "无效库存,残次品", width = 15)
    @ApiModelProperty(value = "无效库存,残次品")
    private java.lang.Integer invalidStock;

	/**可秒杀库存*/
	@Excel(name = "可秒杀库存", width = 15)
    @ApiModelProperty(value = "可秒杀库存")
    private java.lang.Integer seckillStock;

	/**秒杀总数量*/
	@Excel(name = "秒杀总数量", width = 15)
    @ApiModelProperty(value = "秒杀总数量")
    private java.lang.Integer seckillTotal;

	/**仓库编号*/
	@Excel(name = "仓库编号", width = 15)
    @ApiModelProperty(value = "仓库编号")
    private java.lang.String deptId;

    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;

    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;

    /**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
