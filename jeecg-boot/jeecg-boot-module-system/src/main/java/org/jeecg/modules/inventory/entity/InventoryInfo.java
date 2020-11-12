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
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("inventory_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="inventory_info对象", description="inventory_info")
public class InventoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
    @ApiModelProperty(value = "商品编号")
    private java.lang.String goodsId;
	/**规格主键*/
	@Excel(name = "规格主键", width = 15)
    @ApiModelProperty(value = "规格主键")
    private java.lang.String skuId;
	/**有效库存*/
	@Excel(name = "有效库存", width = 15)
    @ApiModelProperty(value = "有效库存")
    private java.lang.Integer validAmount;
	/**无效库存*/
	@Excel(name = "无效库存", width = 15)
    @ApiModelProperty(value = "无效库存")
    private java.lang.Integer invalidAmount;
}
