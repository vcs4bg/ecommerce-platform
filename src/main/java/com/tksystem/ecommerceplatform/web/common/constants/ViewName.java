package com.tksystem.ecommerceplatform.web.common.constants;

public enum ViewName {

    INDEX("root/index"),
    PRODUCT_LIST("products/list"),
    PRODUCT_EDIT("products/edit");

    private final String viewName;

    ViewName(String viewName) {
        this.viewName = viewName;
    }

    public String forward() {
        return forward(null);
    }

    public String forward(String param) {
        if (param == null) {
            return viewName;
        } else {
            return viewName + "/" + param;
        }
    }

    public String redirect() {
        return redirect(null);
    }
    public String redirect(String param) {
        if (param == null) {
            return "redirect:/" + viewName;
        } else {
            return "redirect:/" + viewName + "/" + param;
        }
    }

}
