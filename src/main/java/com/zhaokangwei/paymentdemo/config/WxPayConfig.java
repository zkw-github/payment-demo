package com.atguigu.paymentdemo.config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

@Configuration
@PropertySource("classpath:wxpay.properties")
@ConfigurationProperties(prefix = "wxpay")
@Data
@Slf4j
public class WxPayConfig {

    // 商户号
    private String mchId;

    // 商户API证书序列号
    private String mchSerialNo;

    // 商户私钥文件
    private String privateKeyPath;

    // APIv3秘钥
    private String apiV3Key;

    // APPID
    private String appid;

    // 微信服务器地址
    private String domain;

    // 接收结果通知地址
    private String notifyDomain;

    /**
     * 获取商户的私钥文件
     * @return
     */
    private PrivateKey getPrivateKey(String filename) {
        //私钥存储在文件
        try {
            return PemUtil.loadPrivateKey(
                    new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("私钥文件不存在",e);
        }
    }

    /**
     * 获取证书管理器
     * @return 证书管理器
     */
    @Bean
    public CertificatesManager getCertificatesManager() throws GeneralSecurityException, IOException, HttpCodeException {
        log.info("获取证书管理器...");
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        // 获取私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, privateKey);
        // 获取身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(mchId, wechatPay2Credentials, apiV3Key.getBytes(StandardCharsets.UTF_8));
        // 返回证书管理器
        return certificatesManager;
    }

    /**
     * 获取签名验证器
     * @param certificatesManager 证书管理器
     * @return 签名验证器
     * @throws NotFoundException
     */
    @Bean
    public Verifier getVerifier(CertificatesManager certificatesManager) throws NotFoundException {
        log.info("获取签名验证器...");
        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(mchId);
        // 返回签名验证器
        return verifier;
    }

    /**
     * 获取http请求对象
     * @return http请求对象
     * @throws NotFoundException
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws HttpCodeException
     */
    @Bean
    public CloseableHttpClient getWxPayClient(Verifier verifier) throws NotFoundException, GeneralSecurityException, IOException, HttpCodeException {
        log.info("获取http请求对象...");
        // 获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        // 获取签名验证对象
        WechatPay2Validator wechatPay2Validator = new WechatPay2Validator(verifier);

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(wechatPay2Validator);
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();
        // 返回http请求对象
        return httpClient;
    }
}
