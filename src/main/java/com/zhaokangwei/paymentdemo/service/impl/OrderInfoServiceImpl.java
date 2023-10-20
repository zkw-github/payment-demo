package com.atguigu.paymentdemo.service.impl;

import com.atguigu.paymentdemo.domain.BaseEntity;
import com.atguigu.paymentdemo.domain.Product;
import com.atguigu.paymentdemo.enums.OrderStatus;
import com.atguigu.paymentdemo.service.ProductService;
import com.atguigu.paymentdemo.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.paymentdemo.domain.OrderInfo;
import com.atguigu.paymentdemo.service.OrderInfoService;
import com.atguigu.paymentdemo.mapper.OrderInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
* @author zkw-computer
* @description 针对表【t_order_info】的数据库操作Service实现
* @createDate 2023-10-17 19:34:38
*/
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
    implements OrderInfoService{
    private final ProductService productService;
    @Override
    public OrderInfo createOrderInfo(Long productId) {
        // 查询已存在未支付订单
        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId);
        if (orderInfo != null){
            return orderInfo;
        }
        // 查询商品信息
        Product product = this.productService.lambdaQuery().eq(BaseEntity::getId, productId).one();
        // 生成订单
        orderInfo = OrderInfo.builder()
                .title(product.getTitle())
                .orderNo(OrderNoUtils.getOrderNo())
                .productId(productId)
                .totalFee(product.getPrice())
                .orderStatus(OrderStatus.NOTPAY.getType())
                .build();
        // 将当前订单存入数据库
        this.save(orderInfo);

        // 返回订单信息
        return orderInfo;
    }

    public OrderInfo getNoPayOrderByProductId(Long productId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        wrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = OrderInfo.builder()
                .codeUrl(codeUrl)
                .build();
        this.update(orderInfo, wrapper);
    }
}




