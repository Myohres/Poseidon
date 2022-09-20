package com.nnk.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    /**
     * Get home page.
     * @param model model
     * @return home page html
     */
    @RequestMapping("/")
    public String home(final Model model) {
        return "home";
    }

    /**
     * Get admin home page.
     * @param model model
     * @return adminHome page html
     */
    @RequestMapping("/admin/home")
    public String adminHome(final Model model) {
        return "redirect:/bidList/list";
    }


}
