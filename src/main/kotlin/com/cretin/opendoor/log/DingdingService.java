/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DingdingService
 * Author:   a112233
 * Date:     2020/7/3 6:24 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cretin.opendoor.log;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.internal.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 发送钉钉消息
 *
 * @author a112233
 * @create 2020/7/3
 * @since 1.0.0
 */
public class DingdingService {

    public static void sendMarkdownMsg(String title, String markdownMsg, String[] userPhones, String accessToken, String secret) {
        sendMarkdownMsg(title, markdownMsg, userPhones, accessToken, secret, true);
    }

    public static void sendTextMsg(String msg) {
        sendTextMsg(msg, null, "f39ab3a7cd4e6268fb69bb6ed62a650321192bd70956dc3e898b0a41ddce210f", "SECfeb033c067e2bdb928f13197f8771403cda705d39184143630b260aeedceb2c7", false);
    }

    public static void sendTextMsg(String msg, String[] userPhones, String accessToken, String secret) {
        sendTextMsg(msg, userPhones, accessToken, secret, true);
    }

    /**
     * 发送钉钉消息
     *
     * @param title
     * @param markdownMsg
     */
    public static void sendMarkdownMsg(String title, String markdownMsg, String[] userPhones, String accessToken, String secret, Boolean isAtAll) {
        String serverAddress = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;
        try {
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeToChar(signData, true)), "UTF-8");
            serverAddress = serverAddress + "&sign=" + sign + "&timestamp=" + timestamp;

            com.dingtalk.api.DingTalkClient client = new DefaultDingTalkClient(serverAddress);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            if (userPhones != null && userPhones.length > 0) {
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtMobiles(Arrays.asList(userPhones));
                request.setAt(at);

                markdownMsg = markdownMsg + "\n";
                for (String userPhone : userPhones) {
                    markdownMsg = markdownMsg + "@" + userPhone + " ";
                }
            } else if (isAtAll) {
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setIsAtAll(true);
                request.setAt(at);
            }
            markdown.setTitle(title);
            markdown.setText(markdownMsg);
            request.setMarkdown(markdown);
            client.execute(request);
        } catch (Exception e) {

        }
    }

    /**
     * 发送钉钉消息
     */
    public static void sendTextMsg(String msg, String[] userPhones, String accessToken, String secret, Boolean isAtAll) {
        String serverAddress = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;
        try {

            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeToChar(signData, true)), "UTF-8");
            serverAddress = serverAddress + "&sign=" + sign + "&timestamp=" + timestamp;

            com.dingtalk.api.DingTalkClient client = new DefaultDingTalkClient(serverAddress);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            request.setText(text);
            if (userPhones != null && userPhones.length > 0) {
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtMobiles(Arrays.asList(userPhones));
                request.setAt(at);

                msg = msg + "\n";
                for (String userPhone : userPhones) {
                    msg = msg + "@" + userPhone + " ";
                }
            } else if (isAtAll) {
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setIsAtAll(true);
                request.setAt(at);
            }
            text.setContent(msg);
            request.setText(text);
            client.execute(request);
        } catch (Exception e) {

        }
    }

    /**
     * 发送钉钉消息
     *
     * @param title
     * @param markdownMsg
     */
    public static void sendMarkdownMsg(String title, String markdownMsg, String accessToken, String secret) {
        sendMarkdownMsg(title, markdownMsg, null, accessToken, secret);
    }

//    public static void main(String[] args) {
//        DingdingService.sendMarkdownMsg("测试", "测试", null, "f39ab3a7cd4e6268fb69bb6ed62a650321192bd70956dc3e898b0a41ddce210f", "SECfeb033c067e2bdb928f13197f8771403cda705d39184143630b260aeedceb2c7");
//    }
}