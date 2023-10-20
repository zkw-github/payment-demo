package com.atguigu.paymentdemo.controller;

import com.atguigu.paymentdemo.domain.Product;
import com.atguigu.paymentdemo.service.ProductService;
import com.atguigu.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin // 解决跨域问题
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Api(tags = "商品管理")
public class ProductController {
    private final ProductService productService;
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(){
        List<Product> list = this.productService.list();
        return R.ok().data("productList",list);
    }

}
