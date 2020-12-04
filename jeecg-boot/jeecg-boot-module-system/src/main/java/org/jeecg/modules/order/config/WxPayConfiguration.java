package org.jeecg.modules.order.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.user.entity.ThirdConfig;
import org.jeecg.modules.user.service.IThirdConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WxPayConfiguration implements InitializingBean {

    private static Map<String, WxPayService> wxPayServiceMap = Maps.newHashMap();

    @Resource
    private IThirdConfigService thirdConfigService;

    public void init() {
        QueryWrapper<ThirdConfig> queryWrapper = new QueryWrapper<ThirdConfig>().eq("type", 1).eq("state", 1);
        int total = thirdConfigService.count();
        if (total > 5000) {
            throw new JeecgBootException("微信多账号配置数量不能超过5000个");
        }
        List<ThirdConfig> thirdConfigs = thirdConfigService.list(queryWrapper);
        thirdConfigs.stream()
                .map(x -> {
                    WxPayConfig payConfig = new WxPayConfig();
                    payConfig.setAppId(x.getAppid());
                    payConfig.setMchId(x.getMchId());
                    payConfig.setMchKey(x.getMchKey());
                    payConfig.setKeyPath(x.getKeypath());
                    payConfig.setPrivateCertPath(x.getPrivateKertPath());
                    payConfig.setPrivateKeyPath(x.getPrivateKeyPath());
                    // 可以指定是否使用沙箱环境
                    payConfig.setUseSandboxEnv(false);
                    WxPayService wxPayService = new WxPayServiceImpl();
                    wxPayService.setConfig(payConfig);
                    wxPayServiceMap.put(x.getTanentaId(), wxPayService);
                    return wxPayService;
                }).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() {
        init();
    }
}
