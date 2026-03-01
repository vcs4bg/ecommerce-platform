package com.tksystem.ecommerceplatform.web.controller.products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;
import com.tksystem.ecommerceplatform.domain.service.products.ProductService;
import com.tksystem.ecommerceplatform.web.common.constants.ViewName;
import com.tksystem.ecommerceplatform.web.controller.products.form.ProductRegistForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductEditController {

    private final ProductService service;

    @GetMapping("/edit")
    public String showCreateForm(Model model) {
        model.addAttribute("isCreate", true);
        return ViewName.PRODUCT_EDIT.forward();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable int id,
            Model model) {

        return ViewName.PRODUCT_EDIT.forward();

    }

    @PostMapping("/regist")
    public String registProduct(
            @Validated ProductRegistForm form,
            BindingResult result,
            Model model) {

        log.info("registProductを開始");

        if (result.hasErrors()) {
            model.addAttribute("isCreate", form.getProductId() == null);
            return ViewName.PRODUCT_EDIT.forward();
        }

        log.info("チェックエラーなし");

        ProductEntity product = service.saveProduct(form.toEntity(), form.getImageFile());

        log.info("商品登録完了");

        // TODO: 遷移先は編集画面ではなく商品詳細画面にする
        return ViewName.PRODUCT_EDIT.redirect(product.getProductId().toString());
    }

}
