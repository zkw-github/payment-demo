package com.atguigu.paymentdemo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_product
 */
@TableName(value ="t_product")
@Data
public class Product extends BaseEntity implements Serializable {

    /**
     * 商品名称
     */
    private String title;

    /**
     * 价格(分)
     */
    private Integer price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}