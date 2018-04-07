package com.cloud.food.web;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceBaseImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
public class WechatController {

    private Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        String url = "http://53ab2t.natappfree.cc/sell/wechat//userinfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        logger.info("【微信网页授权】信息：" + redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/userinfo")
    public String userinfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (Exception e) {
            logger.error("【微信网页授权】" + e.getMessage());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();

        logger.info("openId:{}"+openId);

        return "redirect:" + returnUrl + "?openid=" + openId;
    }


}
