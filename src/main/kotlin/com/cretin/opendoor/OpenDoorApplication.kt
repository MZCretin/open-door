package com.cretin.opendoor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenDoorApplication

fun main(args: Array<String>) {
    //订阅主题mqtt001, 使用MessageListener来处理它的消息
    MqttUtil.subscribe("mqtt001", MessageListener())
    runApplication<OpenDoorApplication>(*args)
}
