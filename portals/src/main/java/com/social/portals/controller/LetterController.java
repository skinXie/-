package com.social.portals.controller;

import common.feign.UserFeign;
import common.portals.Holder;
import common.portals.ViewObject;
import common.user.Letter;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LetterController {
    @Autowired
    Holder holder;
    @Autowired
    UserFeign userFeign;

    @RequestMapping(value = "/letter")
    public String letter(Model model) {
        //获取用户未读的站内信
        List<Letter> letters = userFeign.getLetter(holder.getUser().getUserId());
        List<ViewObject> viewObjects = new ArrayList<>();
        for (Letter letter : letters) {
            ViewObject viewObject = new ViewObject();
            //获取站内信的发送者
            User user = userFeign.getUser(letter.getSenderId());
            viewObject.put("user", user);
            viewObject.put("letter", letter);
            viewObjects.add(viewObject);
        }
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        return "letter";
    }
}
