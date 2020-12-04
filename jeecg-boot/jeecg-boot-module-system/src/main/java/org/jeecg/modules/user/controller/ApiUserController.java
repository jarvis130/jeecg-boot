package org.jeecg.modules.user.controller;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.Login;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.user.service.IUserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags="api_user")
@RestController
@RequestMapping("/user/userAccount")
@Slf4j
public class ApiUserController {

    @Resource
    private IUserService userService;

    /**
     *  更新微信用户信息到user_account表
     *
     * @param wxMaUserInfo
     * @return
     */
    @Login
    @AutoLog(value = "更新微信用户信息到user_account表")
    @ApiOperation(value="更新微信用户信息到user_account表", notes="更新微信用户信息到user_account表")
    @PutMapping(value = "/updateUser")
    public Result<?> update(HttpServletRequest request, @RequestBody WxMaUserInfo wxMaUserInfo) {
        String userId = request.getParameter(CommonConstant.USER_KEY);
        boolean result = userService.updateUser(wxMaUserInfo, userId);
        if(result){
            return Result.OK("更新账户成功");
        }
        return Result.error("更新账户失败!");
    }

    /**
     *  更新手机信息到user_account表
     *
     * @param mobile
     * @return
     */
    @Login
    @AutoLog(value = "更新手机信息到user_account表")
    @ApiOperation(value="更新手机信息到user_account表", notes="更新手机信息到user_account表")
    @PutMapping(value = "/updateUser")
    public Result<?> update(HttpServletRequest request, String mobile) {
        String userId = request.getParameter(CommonConstant.USER_KEY);
        boolean result = userService.updateMobileToUser(mobile, userId);
        if(result){
            return Result.OK("更新手机号成功");
        }
        return Result.error("更新手机号失败!");
    }
}