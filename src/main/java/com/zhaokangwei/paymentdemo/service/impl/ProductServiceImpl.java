package com.atguigu.paymentdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.paymentdemo.domain.Product;
import com.atguigu.paymentdemo.service.ProductService;
import com.atguigu.paymentdemo.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
* @author zkw-computer
* @description 针对表【t_product】的数据库操作Service实现
* @createDate 2023-10-17 19:34:38
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

}




