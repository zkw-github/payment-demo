package com.atguigu.paymentdemo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;;
import lombok.Data;

/**
 * 
 * @TableName t_payment_info
 */
@TableName(value ="t_payment_info")
@Data
public class PaymentInfo extends BaseEntity implements Serializable {

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 支付系统交易编号
     */
    private String transactionId;

    /**
     * 支付类型
     */
    private String paymentType;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 支付金额(分)
     */
    private Integer payerTotal;

    /**
     * 通知参数
     */
    private String content;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}