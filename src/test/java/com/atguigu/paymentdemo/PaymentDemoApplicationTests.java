package com.atguigu.paymentdemo;

import com.atguigu.paymentdemo.config.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.security.PrivateKey;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
class PaymentDemoApplicationTests {
    @Resource
    private  WxPayConfig wxPayConfig;
    @Test
    void getPrivateKey() {
        String privateKeyPath = wxPayConfig.getPrivateKeyPath();
//        PrivateKey privateKey = wxPayConfig.getPrivateKey(privateKeyPath);
//        log.info("私钥文件：" + privateKey);
    }

}
