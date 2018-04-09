package com.cloud.food.web;


import com.cloud.food.util.CheckUtil;
import com.cloud.food.util.LogUtils;
import com.cloud.food.util.MessageUtil;
import com.cloud.food.weixin.TextMessage;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/weixin")
public class WeixinController {


    Logger logger = LogUtils.getLogger(WeixinController.class);

    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");

        logger.info("code:{}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb4c7e9de3db7fe7a&secret=7b19e866f0c2b228e8a21ce2077472e4&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();

        String jsonResult = restTemplate.getForObject(url, String.class);
        logger.info("response:{}", jsonResult);

    }

    @RequestMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();


        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    @PostMapping(value = "/auth")
    public void auth2(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();

            Map<String, Object> map = MessageUtil.xmlToMap(request);
            String FromUserName = (String) map.get("ToUserName");
            String ToUserName = (String) map.get("FromUserName");

            String MsgType = (String) map.get("MsgType");
            String Content = (String) map.get("Content");
            String MsgId = (String) map.get("MsgId");

            String message = null;

            if (MsgType.equals(MessageUtil.MESSAGE_TEXT)) {

                TextMessage textMessage = new TextMessage();

                if ("1".equals(Content)) {
                    textMessage.setContent("全部课程---");
                } else if ("2".equals(Content)) {
                    textMessage.setContent("收费课程---");
                } else if ("3".equals(Content)) {
                    textMessage.setContent("公开课---");
                } else if ("?".equals(Content)) {
                    textMessage.setContent(MessageUtil.menuText());
                } else {
                    textMessage.setContent("你发送的消息是：" + Content);
                }
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setFromUserName(FromUserName);
                textMessage.setToUserName(ToUserName);
                textMessage.setMsgId(MsgId);
                textMessage.setMsgType("text");
                message = MessageUtil.textMessageToXml(textMessage);
                System.out.println(message);
            } else if (MessageUtil.MESSAGE_EVENT.equals(MsgType)) {
                String event = (String) map.get("Event");
                if (event.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {
                    TextMessage textMessage = MessageUtil.initMenu(FromUserName, ToUserName, MessageUtil.menuText());
                    message = MessageUtil.textMessageToXml(textMessage);
                }
            }

            out.print(message);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            out.close();
        }


    }

}
