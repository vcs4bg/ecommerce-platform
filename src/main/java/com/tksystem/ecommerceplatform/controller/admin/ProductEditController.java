package com.tksystem.ecommerceplatform.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.controller.admin.form.ProductRegistForm;
import com.tksystem.ecommerceplatform.controller.common.constants.ViewName;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class ProductEditController {

    @GetMapping("/product-edit")
    public String showCreateForm(Model model) {
        model.addAttribute("isCreate", true);
        return ViewName.PRODUCT_EDIT.forward();
    }

    @GetMapping("/product-edit/{id}")
    public String getMethodName(
            @PathVariable int id,
            Model model) {

        return ViewName.PRODUCT_EDIT.forward();

    }

    @PostMapping("/product-regist")
    public String postMethodName(
            @Validated ProductRegistForm form,
            BindingResult result,
            Model model) {

        if (form.getProductId() == null) {
            return ViewName.PRODUCT_LIST.redirect();
        } else {
            return ViewName.PRODUCT_EDIT.redirect(form.getProductId().toString());
        }
    }

}
