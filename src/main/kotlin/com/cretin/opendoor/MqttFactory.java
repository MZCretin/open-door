package com.cretin.opendoor;

import com.cretin.opendoor.log.LOGGER;
import org.eclipse.paho.client.mqttv3.*;

public class MqttFactory {

    private static MqttClient client;

    /**
     *   获取客户端实例
     *   单例模式, 存在则返回, 不存在则初始化
     */
    public static MqttClient getInstance() {
        if (client == null) {
            init();
        }
        return client;
    }

    /**
     *   初始化客户端
     */
    public static void init() {
        try {
            client = new MqttClient("tcp://bemfa.com:9501", "xxx");
            // MQTT配置对象
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置自动重连, 其它具体参数可以查看MqttConnectOptions
            options.setAutomaticReconnect(true);
            if (!client.isConnected()) {
                client.connect(options);
            }
        } catch (MqttException e) {
            LOGGER.error(String.format("MQTT: 连接消息服务器[%s]失败", "bemfa.com:9501"));
        }
    }

}
