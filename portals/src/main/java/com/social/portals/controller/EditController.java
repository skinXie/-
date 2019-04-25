package com.social.portals.controller;

import common.portals.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditController {

    @Autowired
    Holder holder;

    @RequestMapping("/user/edit")
    public String edit(Model model) {
        model.addAttribute("holder", holder.getUser());
        return "edit";
    }
}
