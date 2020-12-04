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
 * @Description: tech_arrange
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("tech_arrange")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tech_arrange对象", description="tech_arrange")
public class TechArrange implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键,员工编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键,员工编号")
    private java.lang.String id;
	/**排班项主键对应码表*/
	@Excel(name = "排班项主键对应码表", width = 15)
    @ApiModelProperty(value = "排班项主键对应码表")
    private java.lang.String itemCode;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
}
