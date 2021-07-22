package com.ccbhousing.ccbhousing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CcbController {

    @RequestMapping("importExcel")
    public String importExcel() {
        return "redirect:/export.jsp";
    }



}
