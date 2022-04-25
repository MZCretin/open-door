package com.cretin.opendoor.request;

import com.cretin.opendoor.MqttUtil;
import com.cretin.opendoor.log.DingdingService;
import com.cretin.opendoor.model.DoorResponse;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;

public class HttpRequest {

    private static Gson gson = new Gson();

    /**
     * 开蒙
     */
    public static void openDoor() {
        try {
            String params = "{\"houseHostId\":\"xxx\",\"peopleId\":\"xxx\",\"roleType\":\"0\"}";
            OkHttpUtil.getInstance().postJsonAsyn("https://pabaspmj.szxhdz.com:18000/xhapp/service/iacs/info/house/host/commandByHouseHostId?token=xxx", params, new OkHttpUtil.NetCall() {
                @Override
                public void success(Call call, Response response) throws IOException {
                    String result = response.body().string();

                    DoorResponse resp = gson.fromJson(result, DoorResponse.class);
                    if (resp.getStatus().equals("1")) {
                        //开门成功
                        System.out.println("开门请求成功：" + result);
                        DingdingService.sendTextMsg("3巷5号一楼开门成功");
                    } else {
                        System.out.println("开门请求成功：" + result);
                        DingdingService.sendTextMsg("3巷5号一楼开门失败，错误信息：" + resp.getMessage());
                    }
                    //这里强行设置成off 不然小爱同学那边会以为设备是开的 就会说设备已经开啦 啥的
                    MqttUtil.send("mqtt001", "off");
                }

                @Override
                public void failed(Call call, IOException e) {
                    DingdingService.sendTextMsg("3巷5号一楼开门失败，错误信息：" + e.getMessage());
                }
            });
        } catch (Exception e) {
            DingdingService.sendTextMsg("3巷5号一楼开门失败，错误信息：" + e.getMessage());
        }
    }
}
