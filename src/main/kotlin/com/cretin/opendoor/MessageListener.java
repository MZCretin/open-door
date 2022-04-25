package com.cretin.opendoor;

import com.cretin.opendoor.log.LOGGER;
import com.cretin.opendoor.request.HttpRequest;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public class MessageListener implements IMqttMessageListener {

    /**
     * 处理消息
     *
     * @param topic       主题
     * @param mqttMessage 消息
     */
    @Override
    public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage mqttMessage) throws Exception {
        if (topic.equals("mqtt001")) {
            String order = new String(mqttMessage.getPayload());
            if (order.equals("on")) {
                HttpRequest.openDoor();
            }
        }

        LOGGER.info(String.format("MQTT: 订阅主题[%s]发来消息[%s]", topic, new String(mqttMessage.getPayload())));
    }

}