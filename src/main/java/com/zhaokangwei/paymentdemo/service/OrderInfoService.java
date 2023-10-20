package com.atguigu.paymentdemo.service;

import com.atguigu.paymentdemo.domain.OrderInfo;
import com.atguigu.paymentdemo.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zkw-computer
* @description 针对表【t_order_info】的数据库操作Service
* @createDate 2023-10-17 19:34:38
*/
public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrderInfo(Long productId);

    void saveCodeUrl(String orderNo, String codeUrl);
}
