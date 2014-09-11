package br.com.example.springoauthauthserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class DefaultController {

    @RequestMapping("/")
    @ResponseBody
    public String teste() {
        return "OK";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
