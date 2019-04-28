package com.social.portals.controller;

import common.feign.QuestionAnswerFeign;
import common.feign.UserFeign;
import common.portals.Holder;
import common.portals.ViewObject;
import common.questionAnswer.Answer;
import common.questionAnswer.Question;
import common.user.Follow;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    Holder holder;
    @Autowired
    QuestionAnswerFeign questionAnswerFeign;
    @Autowired
    UserFeign userFeign;

    @GetMapping(value = "/profile/{uid}")
    public String Profile(Model model, @PathVariable("uid") int uid) {
        //获取用户
        User user = userFeign.getUser(uid);
        //获取用户的回答和回答的问题
        List<Answer> answers = questionAnswerFeign.getAnswerByUid(user.getUserId(), 0);
        //获取回答数
        int answerCount = answers.size();
        List<ViewObject> vo = new ArrayList<>();
        //获取点赞数
        int zanCount = 0;
        for (Answer var : answers) {
            ViewObject var1 = new ViewObject();
            Question q = questionAnswerFeign.getQuestionById(var.getEntityId());
            zanCount += questionAnswerFeign.getZan(var.getAnswerId());
            var1.put("question", q);
            var1.put("answer", var);
            vo.add(var1);
        }

        //获取发表问题数
        List<Question> questions1 = questionAnswerFeign.getQuestionByUserId(user.getUserId());
        int questionCount = questions1.size();
        //获取问题总浏览数
        int visitCount = 0;
        for (Question var : questions1) {
            visitCount += var.getVisitTime();
        }
        //获取用户是否关注该用户
        Follow follow = userFeign.isFollow(holder.getUser().getUserId(), user.getUserId(), "用户");
        //添加进视图
        model.addAttribute("user", user);
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("answers", answers);
        model.addAttribute("questionCount", questionCount);
        model.addAttribute("answerCount", answerCount);
        model.addAttribute("visitCount", visitCount);
        model.addAttribute("zanCount", zanCount);
        model.addAttribute("follow", follow);
        model.addAttribute("vo", vo);
        return "profile";
    }
}
