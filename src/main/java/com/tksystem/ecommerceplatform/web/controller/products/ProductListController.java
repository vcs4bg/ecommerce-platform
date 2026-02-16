package com.tksystem.ecommerceplatform.web.controller.products;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.web.common.constants.ViewName;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/products")
public class ProductListController {

    @GetMapping
    public String showList() {
        return ViewName.PRODUCT_LIST.forward();
    }

}
