/**
 * Copyright (c) 2016-2019 学嘉科技 All rights reserved.
 *
 * https://www.xuejiashopping.com
 *
 * 版权所有，侵权必究！
 */

package org.jeecg.common.util;

import org.jeecg.common.exception.JeecgBootException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws JeecgBootException  校验不通过，则报JeecgBootException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws JeecgBootException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new JeecgBootException(msg.toString());
        }
    }
}
