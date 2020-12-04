package org.jeecg.modules.cas.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.user.entity.ThirdConfig;
import org.jeecg.modules.user.service.IThirdConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
public class WxMaConfiguration implements InitializingBean {

    private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMaService> maServices = Maps.newHashMap();

    @Resource
    private IThirdConfigService thirdConfigService;

    public static WxMaService getMaService(String tanentId) throws Exception {

        WxMaService wxService = maServices.get(tanentId);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", tanentId));
        }
        return wxService;
    }

    public static WxMaMessageRouter getRouter(String tanentId) {
        return routers.get(tanentId);
    }

    public void init() {
        QueryWrapper<ThirdConfig> queryWrapper = new QueryWrapper<ThirdConfig>().eq("type", 1).eq("state", 1);
        int total = thirdConfigService.count();
        if (total > 5000 || total == 0) {
            throw new JeecgBootException("微信多账号配置数量不能超过5000个或者没配置");
        }
        List<ThirdConfig> thirdConfigs = thirdConfigService.list(queryWrapper);
        thirdConfigs.stream()
            .map(x -> {
                WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
                config.setAppid(x.getAppid());
                config.setSecret(x.getSecret());
//                config.setToken(a.getToken());
//                config.setAesKey(a.getAesKey());
//                config.setMsgDataFormat(a.getMsgDataFormat());
                WxMaService service = new WxMaServiceImpl();
                service.setWxMaConfig(config);
                maServices.put(x.getTanentaId(), service);
                routers.put(x.getTanentaId(), this.newRouter(service));
                return service;
            }).collect(Collectors.toList());
    }

    private WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
            .rule().handler(logHandler).next()
            .rule().async(false).content("模板").handler(templateMsgHandler).end()
            .rule().async(false).content("文本").handler(textHandler).end()
            .rule().async(false).content("图片").handler(picHandler).end()
            .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

    private final WxMaMessageHandler templateMsgHandler = (wxMessage, context, service, sessionManager) -> {
//        service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
//            .templateId("此处更换为自己的模板id")
//            .formId("自己替换可用的formid")
//            .data(Lists.newArrayList(
//                new WxMaTemplateData("keyword1", "339208499", "#173177")))
//            .toUser(wxMessage.getFromUser())
//            .build());
        return null;
    };

    private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
        System.out.println("收到消息：" + wxMessage.toString());
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
            .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) -> {
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
            .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = service.getMediaService()
                .uploadMedia("image", "png",
                    ClassLoader.getSystemResourceAsStream("tmp.png"));
            service.getMsgService().sendKefuMsg(
                WxMaKefuMessage
                    .newImageBuilder()
                    .mediaId(uploadResult.getMediaId())
                    .toUser(wxMessage.getFromUser())
                    .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            final File file = service.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(
                WxMaKefuMessage
                    .newImageBuilder()
                    .mediaId(uploadResult.getMediaId())
                    .toUser(wxMessage.getFromUser())
                    .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
