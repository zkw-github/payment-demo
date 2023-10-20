package com.atguigu.paymentdemo.service.impl;

import com.atguigu.paymentdemo.config.WxPayConfig;
import com.atguigu.paymentdemo.domain.OrderInfo;
import com.atguigu.paymentdemo.enums.wxpay.WxApiType;
import com.atguigu.paymentdemo.enums.wxpay.WxNotifyType;
import com.atguigu.paymentdemo.service.OrderInfoService;
import com.atguigu.paymentdemo.service.WxPayService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    private final WxPayConfig wxPayConfig;
    private final CloseableHttpClient httpClient;

    private final OrderInfoService orderInfoService;
    /**
     * 创建订单，调用Native支付接口
     * @param productId 商品号
     * @return 二维码和订单号的集合
     * @throws IOException
     */
    @Override
    public Map<String, Object> nativePay(Long productId) throws IOException {

        log.info("订单生成中...");
        // 生成订单
        OrderInfo orderInfo = orderInfoService.createOrderInfo(productId);
        String codeUrl = orderInfo.getCodeUrl();
        if (orderInfo != null && !StringUtils.isEmpty(codeUrl)){
            // 订单存在，二维码存在
            Map<String, Object> map = new HashMap<>();
            map.put("codeUrl", codeUrl);
            map.put("orderNo", orderInfo.getOrderNo());
            return map;
        }
        // 调用统一下单API
        //请求URL
        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat(WxApiType.NATIVE_PAY.getType()));

        // 请求body参数
        Gson gson = new Gson();
        Map paramsMap = new HashMap<>();
        paramsMap.put("appid", wxPayConfig.getAppid());
        paramsMap.put("mchid", wxPayConfig.getMchId());
        paramsMap.put("description", orderInfo.getTitle());
        paramsMap.put("out_trade_no", orderInfo.getOrderNo());
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));

        Map amountMap = new HashMap<>();
        amountMap.put("total", orderInfo.getTotalFee());
        amountMap.put("currency", "CNY");
        paramsMap.put("amount", amountMap);

        // 将请求参数转换成JSON字符串
        String jsonParams = gson.toJson(paramsMap);
        log.info("请求参数：" + jsonParams);

        StringEntity entity = new StringEntity(jsonParams, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            String bodyAsString = EntityUtils.toString(response.getEntity());// 响应体
            int statusCode = response.getStatusLine().getStatusCode();// 响应状态码
            if (statusCode == 200) {
                log.info("成功，返回结果 = " + bodyAsString);
            } else if (statusCode == 204) {
                System.out.println("成功");
            } else {
                System.out.println("Native下单失败,响应码 = " + statusCode + ",返回结果 = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
            // 响应结果
            Map<String, String> resultMap = gson.fromJson(bodyAsString, HashMap.class);
            // 获取二维码
            codeUrl = resultMap.get("code_url");
            // 订单号
            String orderNo = orderInfo.getOrderNo();
            // 保存二维码
            this.orderInfoService.saveCodeUrl(orderNo,codeUrl);
            // 返回二维码
            Map<String, Object> map = new HashMap<>();
            map.put("codeUrl", codeUrl);
            map.put("orderNo", orderInfo.getOrderNo());

            return map;
        } finally {
            response.close();
        }
    }
}
