package org.dizena.modules.guest.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GuestIndex implements ErrorController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "OK";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error() {
        return "Error";
    }

}
