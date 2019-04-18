package com.social.portals.controller;

import common.feign.QuestionAnswerFeign;
import common.feign.UserFeign;
import common.portals.Holder;
import common.portals.ViewObject;
import common.questionAnswer.Question;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    Holder holder;
    @Autowired
    QuestionAnswerFeign questionAnswerFeign;
    @Autowired
    UserFeign userFeign;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<ViewObject> viewObjects = new ArrayList<>();
        //获取最大分页数
        int maxPage = questionAnswerFeign.getAllQuestion().size();
        maxPage = maxPage % 5 == 0 ? maxPage / 5 : maxPage / 5 + 1;
        //分页获取所有问题
        List<Question> questionList = questionAnswerFeign.getQuestion(1, 5);
        //获取模板数据
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            //获取问题的提问者
            User user = userFeign.getUser(question.getUserId());
            viewObject.put("question", question);
            viewObject.put("user", user);
            viewObjects.add(viewObject);
        }
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("maxPage", maxPage);
        return "index";
    }
}
