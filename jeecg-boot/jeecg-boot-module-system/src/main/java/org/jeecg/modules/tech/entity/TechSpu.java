package org.jeecg.modules.tech.entity;

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
 * @Description: tech_spu
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("tech_spu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tech_spu对象", description="tech_spu")
public class TechSpu implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**techId*/
	@Excel(name = "techId", width = 15)
    @ApiModelProperty(value = "techId")
    private java.lang.String techId;
	/**spuId*/
	@Excel(name = "spuId", width = 15)
    @ApiModelProperty(value = "spuId")
    private java.lang.String spuId;
}
