package org.jeecg.modules.biz.entity;

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
 * @Description: crm_biz_oppt
 * @Author: jeecg-boot
 * @Date:   2020-09-18
 * @Version: V1.0
 */
@Data
@TableName("crm_biz_oppt")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="crm_biz_oppt对象", description="crm_biz_oppt")
public class BizOppt implements Serializable {
    private static final long serialVersionUID = 1L;

	/**商机主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "商机主键")
    private java.lang.String id;
	/**商机编号*/
	@Excel(name = "商机编号", width = 15)
    @ApiModelProperty(value = "商机编号")
    private java.lang.Integer boSn;
	/**客户编号*/
	@Excel(name = "客户编号", width = 15)
    @ApiModelProperty(value = "客户编号")
    private java.lang.String userId;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
	/**当前处理人;对应sys_user表的username*/
	@Excel(name = "当前处理人;对应sys_user表的username", width = 15)
    @ApiModelProperty(value = "当前处理人;对应sys_user表的username")
    private java.lang.Integer handleBy;
	/**主体编号*/
	@Excel(name = "主体编号", width = 15)
    @ApiModelProperty(value = "主体编号")
    private java.lang.Integer subjectId;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
