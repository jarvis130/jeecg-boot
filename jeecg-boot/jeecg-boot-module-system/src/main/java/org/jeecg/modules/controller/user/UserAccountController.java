package org.jeecg.modules.controller.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.service.IUserAccountService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
@Api(tags="会员管理-账号信息")
@RestController
@RequestMapping("/user/userAccount")
@Slf4j
public class UserAccountController extends JeecgController<UserAccount, IUserAccountService> {
	@Autowired
	private IUserAccountService userAccountService;
	
	/**
	 * 分页列表查询
	 *
	 * @param userAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分页列表查询")
	@ApiOperation(value="分页列表查询", notes="分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<UserAccount>> queryPageList(UserAccount userAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Result<IPage<UserAccount>> result = new Result<IPage<UserAccount>>();
		QueryWrapper<UserAccount> queryWrapper = QueryGenerator.initQueryWrapper(userAccount, req.getParameterMap());
		Page<UserAccount> page = new Page<UserAccount>(pageNo, pageSize);
		IPage<UserAccount> pageList = userAccountService.page(page, queryWrapper);

		result.setResult(pageList);
		return result;
	}
	
	/**
	 *   添加
	 *
	 * @param userAccount
	 * @return
	 */
	@AutoLog(value = "添加")
	@ApiOperation(value="添加", notes="添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserAccount userAccount) {
		//手机号校验
		UserAccount account = userAccountService.getUserAccountByMobile(userAccount.getMobile());
		if(account != null){
			log.info("手机号已经存在！");
			return Result.error("手机号已经存在！");
		}
		userAccountService.createUserAccount(userAccount);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userAccount
	 * @return
	 */
	@AutoLog(value = "编辑")
	@ApiOperation(value="编辑", notes="编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserAccount userAccount) {
		//手机号校验
		UserAccount account = userAccountService.getUserAccountByMobile(userAccount.getMobile());
		if(account != null){
			log.info("手机号已经存在！");
			return Result.error("手机号已经存在！");
		}
		userAccountService.updateById(userAccount);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "通过id删除")
	@ApiOperation(value="通过id删除", notes="通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userAccountService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "批量删除")
	@ApiOperation(value="批量删除", notes="批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "通过id查询")
	@ApiOperation(value="通过id查询", notes="通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		UserAccount userAccount = userAccountService.getById(id);
		if(userAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(userAccount);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userAccount
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserAccount userAccount) {
        return super.exportXls(request, userAccount, UserAccount.class, "user_account");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserAccount.class);
    }

    private void validate(){

	}

}
