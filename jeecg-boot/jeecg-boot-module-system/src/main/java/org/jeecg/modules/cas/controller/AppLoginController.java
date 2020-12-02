package org.jeecg.modules.cas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.JwtUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.cas.entity.MobileLoginVo;
import org.jeecg.modules.cas.entity.UserInfoDto;
import org.jeecg.modules.cas.entity.UserLoginVo;
import org.jeecg.modules.cas.service.LoginService;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Api("API登录接口")
public class AppLoginController {
    @Resource
    private IUserAccountService accountService;
    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private LoginService loginService;

    /**
     * 登录
     */
    @PostMapping("userLogin")
    @ApiOperation("账号登录")
    public Result<?> login(@RequestBody UserLoginVo userLoginVo){
        UserAccount userAccount = loginService.loginByUserName(userLoginVo);
        if (userAccount != null) {
            if (userAccount.getStatus() != 1) {
                return Result.error(500, "用户停用");
            }
            UserInfoDto userInfo = getUserInfo(userAccount);
            return Result.OK(userInfo);
        }
        return Result.error(500, "用户名或密码失败");
    }

    /**
     * 登录
     */
    @PostMapping("mobileLogin")
    @ApiOperation("手机登录")
    public Result<?> login(@RequestBody MobileLoginVo mobileLoginVo){
        UserAccount userAccount = accountService.getUserAccountByMobile(mobileLoginVo.getMobile());
        Object temp = redisUtil.get(mobileLoginVo.getMobile());
        if (temp != null && StringUtils.isNotBlank(temp.toString())) {
            //获取手机验证码code
            String code = temp.toString().split(":")[1];
            if (mobileLoginVo.getCode().equals(code)) {
                if (userAccount.getStatus() != 1) {
                    return Result.error(500, "用户停用");
                }
                UserInfoDto userInfo = getUserInfo(userAccount);
                return Result.OK(userInfo);
            }
        }

        return Result.error(500, "验证码有误");

    }

    private UserInfoDto getUserInfo(UserAccount userAccount) {
        String token = jwtUtils.generateToken(userAccount.getId());
        UserInfoDto userInfo = new UserInfoDto();
        BeanUtils.copyProperties(userAccount, userInfo);
        userInfo.setToken(token);
        userInfo.setExpireTime(jwtUtils.getExpire());
        return userInfo;
    }



}