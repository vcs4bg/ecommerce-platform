package com.tksystem.ecommerceplatform.web.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tksystem.ecommerceplatform.web.common.constants.ViewName;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        // TODO: エラー画面を実装する
        // TODO: エラー画面名をViewNameの列挙子に加える。
        log.info(exception.getMessage());

        return ViewName.ERROR.forward();
    }
}
