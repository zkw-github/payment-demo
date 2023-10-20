package com.atguigu.paymentdemo.controller;

import com.atguigu.paymentdemo.config.WxPayConfig;
import com.atguigu.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试控制器")
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final WxPayConfig wxPayConfig;

    @GetMapping
    public R getWxPayConfig(){
        String mchId = wxPayConfig.getMchId();
        return R.ok().data("mchId", mchId);
    }

}
