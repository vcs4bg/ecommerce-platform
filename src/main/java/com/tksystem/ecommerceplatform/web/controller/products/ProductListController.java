package com.tksystem.ecommerceplatform.web.controller.products;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.web.common.constants.ViewName;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/products")
@Slf4j
public class ProductListController {

    @GetMapping
    public String showList() {
        log.info("デバッグ：ProductListController.showList");
        return ViewName.PRODUCT_LIST.forward();
    }

}
