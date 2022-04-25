
package com.cretin.opendoor;

import com.cretin.opendoor.log.LOGGER;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

public class MqttUtil {

    /**
    *   发送消息
    *   @param topic 主题
    *   @param data 消息内容
    */
    public static void send(String topic, Object data) {    
        // 获取客户端实例
        MqttClient client = MqttFactory.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 转换消息为json字符串
            String json = mapper.writeValueAsString(data);      
            client.publish(topic, new MqttMessage(json.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format("MQTT: 主题[%s]发送消息转换json失败", topic));
        } catch (MqttException e) {
            LOGGER.error(String.format("MQTT: 主题[%s]发送消息失败", topic));    
        }  
    }
    
    /** 
    * 订阅主题 
    * @param topic 主题 
    * @param listener 消息监听处理器 
    */
    public static void subscribe(String topic, IMqttMessageListener listener) {
        MqttClient client = MqttFactory.getInstance();
        try {    
            client.subscribe(topic, listener);  
        } catch (MqttException e) {    
            LOGGER.error(String.format("MQTT: 订阅主题[%s]失败", topic));  
        }
    }
}