package org.jeecg.modules.workorder.entity;

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
 * @Description: work_order
 * @Author: jeecg-boot
 * @Date:   2020-09-30
 * @Version: V1.0
 */
@Data
@TableName("work_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="work_order对象", description="work_order")
public class WorkOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;

	/**工单编号*/
	@Excel(name = "工单编号", width = 15)
    @ApiModelProperty(value = "工单编号")
    private java.lang.String workCode;

    /**创建人*/
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    @Dict(dicCode = "username",dictTable="sys_user",dicText="realname")
    private java.lang.String createBy;

    /**紧急程度*/
    @Excel(name = "紧急程度", width = 15)
    @Dict(dicCode = "priority_level")
    @ApiModelProperty(value = "紧急程度")
    private java.lang.Integer priorityLevel;

    /**工单类型*/
    @Excel(name = "工单类型", width = 15)
    @ApiModelProperty(value = "工单类型")
    @Dict(dicCode = "id",dictTable="sys_category",dicText="name")
    private java.lang.String categoryId;

	/**标题/内容*/
	@Excel(name = "标题/内容", width = 15)
    @ApiModelProperty(value = "标题/内容")
    private java.lang.String content;

    /**状态*/
    @Excel(name = "状态", width = 15)
    @Dict(dicCode = "work_order_status")
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;

	/**附件地址*/
    @ApiModelProperty(value = "附件地址")
    private java.lang.String attachedPath;


	/**创建时间*/
    @Excel(name = "创建时间", width = 15)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**负责人*/
    @Excel(name = "负责人", width = 15)
    @ApiModelProperty(value = "负责人")
    @Dict(dicCode = "username",dictTable="sys_user",dicText="realname")
    private java.lang.String handleMaster;

    /**责任公司/部门*/
    @Excel(name = "责任公司/部门", width = 15)
    @ApiModelProperty(value = "责任公司/部门")
    @Dict(dicCode = "id",dictTable="sys_depart",dicText="depart_name")
    private java.lang.String handleDept;

    /**处理人*/
    @Excel(name = "处理人", width = 15)
    @ApiModelProperty(value = "处理人")
    @Dict(dicCode = "username",dictTable="sys_user",dicText="realname")
    private java.lang.String handleBy;

    /**监督人*/
    @Excel(name = "监督人", width = 15)
    @ApiModelProperty(value = "监督人")
    private java.lang.String superviseBy;

    /**截止时间*/
    @Excel(name = "截止时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "截止时间")
    private java.util.Date expiryDate;


	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;

	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;



}
