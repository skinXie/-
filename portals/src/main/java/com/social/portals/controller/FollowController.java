package com.social.portals.controller;

import common.feign.UserFeign;
import common.portals.Holder;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class FollowController {
    @Autowired
    Holder holder;
    @Autowired
    UserFeign userFeign;

    @RequestMapping("/follow")
    public String follow(Model model) {
        //获取用户的关注列表
        List<User> followeds = userFeign.getFollowUser(holder.getUser().getUserId());
        model.addAttribute("followeds", followeds);
        model.addAttribute("holder", holder.getUser());
        return "follow";
    }
}
