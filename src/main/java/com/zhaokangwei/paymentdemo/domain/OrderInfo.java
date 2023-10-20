package com.atguigu.paymentdemo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName t_order_info
 */
@TableName(value ="t_order_info")
@Data
@Builder
public class OrderInfo extends BaseEntity implements Serializable {

    /**
     * 订单标题
     */
    private String title;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 支付产品id
     */
    private Long productId;

    /**
     * 订单金额(分)
     */
    private Integer totalFee;

    /**
     * 订单二维码连接
     */
    private String codeUrl;

    /**
     * 订单状态
     */
    private String orderStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}