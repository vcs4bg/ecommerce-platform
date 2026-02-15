package com.tksystem.ecommerceplatform.controller.common.root;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tksystem.ecommerceplatform.controller.common.constants.ViewName;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String index() {
        return ViewName.INDEX.forward();
    }

}
