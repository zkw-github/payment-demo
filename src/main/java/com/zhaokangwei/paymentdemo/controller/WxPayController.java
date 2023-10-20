package com.atguigu.paymentdemo.controller;

import com.atguigu.paymentdemo.service.WxPayService;
import com.atguigu.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wx-pay")
@Api(tags = "网站微信支付API")
@Slf4j
public class WxPayController {

    private final WxPayService wxPayService;

    @ApiOperation("调用统一下单API，生成支付二维码")
    @PostMapping("/native/{productId}")
    public R nativePay(@PathVariable Long productId) throws IOException {
        log.info("发起微信支付请求");

        // 返回支付二维码连接和订单号
        Map<String, Object> map = wxPayService.nativePay(productId);
        return R.ok().setData(map);
    }

}
