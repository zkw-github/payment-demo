package com.atguigu.paymentdemo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_refund_info
 */
@TableName(value ="t_refund_info")
@Data
public class RefundInfo extends BaseEntity implements Serializable {

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 商户退款单编号
     */
    private String refundNo;

    /**
     * 支付系统退款单号
     */
    private String refundId;

    /**
     * 原订单金额(分)
     */
    private Integer totalFee;

    /**
     * 退款金额(分)
     */
    private Integer refund;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 申请退款返回参数
     */
    private String contentReturn;

    /**
     * 退款结果通知参数
     */
    private String contentNotify;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}