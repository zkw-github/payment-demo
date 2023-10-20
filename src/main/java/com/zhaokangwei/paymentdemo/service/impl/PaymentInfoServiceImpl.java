package com.atguigu.paymentdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.paymentdemo.domain.PaymentInfo;
import com.atguigu.paymentdemo.service.PaymentInfoService;
import com.atguigu.paymentdemo.mapper.PaymentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author zkw-computer
* @description 针对表【t_payment_info】的数据库操作Service实现
* @createDate 2023-10-17 19:34:38
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService{

}




