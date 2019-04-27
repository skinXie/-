package com.social.portals.controller;

import common.portals.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditController {

    @Autowired
    Holder holder;

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Model model) {
        model.addAttribute("holder", holder.getUser());
        return "edit";
    }
}
