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
        //声明vo视图变量
        ViewObject vo = new ViewObject();
        //获取用户
        User user = userFeign.getUser(uid);
        //获取用户的回答和回答的问题
        List<Answer> answers = questionAnswerFeign.getAnswerByUid(uid, 0);
        //获取回答数
        int answerCount = answers.size();
        List<Question> questions = new ArrayList<>();
        //获取点赞数
        int zanCount = 0;
        for (Answer var : answers) {
            Question q = questionAnswerFeign.getQuestionById(var.getEntityId());
            questions.add(q);
            zanCount += questionAnswerFeign.getZan(var.getAnswerId());
        }

        //获取发表问题数
        List<Question> questions1 = questionAnswerFeign.getQuestionByUserId(uid);
        int questionCount = questions1.size();
        //获取问题总浏览数
        int visitCount = 0;
        for (Question var : questions1) {
            visitCount += var.getVisitTime();
        }
        //获取用户是否关注该用户
        Follow follow = userFeign.isFollow(holder.getUser().getUserId(), uid, "用户");
        //添加进视图
        model.addAttribute("user", user);
        model.addAttribute("holder", holder);
        model.addAttribute("answers", answers);
        model.addAttribute("questionCount", questionCount);
        model.addAttribute("answerCount", answerCount);
        model.addAttribute("visitCount", visitCount);
        model.addAttribute("zanCount", zanCount);
        return "profile";
    }
}
