package com.tksystem.ecommerceplatform.web.controller.products;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductListDto;
import com.tksystem.ecommerceplatform.domain.service.products.ProductService;
import com.tksystem.ecommerceplatform.web.common.constants.ViewName;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductListController {

    private final ProductService service;

    @GetMapping
    public String showList(Model model) {
        // TODO: 画像ファイルが表示されない。

        List<ProductListDto> list = service.getList();

        model.addAttribute("productList", list);

        return ViewName.PRODUCT_LIST.forward();

    }

}
