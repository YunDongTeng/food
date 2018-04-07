package com.cloud.food.util;

import com.cloud.food.weixin.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "ubscribe";
    public static final String MESSAGE_CLICK = "click";
    public static final String MESSAGE_VIEW = "view";


    public static Map<String, Object> xmlToMap(HttpServletRequest request) throws IOException, Exception {

        Map<String, Object> result = new HashMap<String, Object>();

        SAXReader saxReader = new SAXReader();

        InputStream inputstream = request.getInputStream();

        Document document = saxReader.read(inputstream);

        Element element = document.getRootElement();

        List<Element> list = element.elements();


        for (Element e : list) {
            result.put(e.getName(), e.getText());
        }

        inputstream.close();

        return result;
    }


    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    public static TextMessage initMenu(String FromUserName, String ToUserName, String Content) {

        TextMessage textMessage = new TextMessage();
        textMessage.setContent(Content);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setFromUserName(FromUserName);
        textMessage.setToUserName(ToUserName);
        return textMessage;
    }

    public static String menuText() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("欢迎观众爱学网公众号,请按照一下操作进行：\n\n");
        buffer.append("1-查看所有课程\n");
        buffer.append("2-查看所有收费课程\n");
        buffer.append("3-查看所有公开课\n");

        buffer.append("回复 ? 调出主菜单");
        return buffer.toString();

    }

}
