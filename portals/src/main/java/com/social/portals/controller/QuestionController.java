package com.social.portals.controller;

import common.feign.QuestionAnswerFeign;
import common.feign.UserFeign;
import common.portals.Holder;
import common.portals.ViewObject;
import common.questionAnswer.Answer;
import common.questionAnswer.Question;
import common.questionAnswer.Zan;
import common.user.Follow;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    Holder holder;
    @Autowired
    UserFeign userFeign;
    @Autowired
    QuestionAnswerFeign questionAnswerFeign;

    @RequestMapping(value = "/question/{qid}")
    public String question(Model model, @PathVariable(value = "qid") int qid) {

        //根据问题id获取问题
        Question question = questionAnswerFeign.getQuestionById(qid);
        //获取问题作者
        User user = userFeign.getUser(question.getUserId());
        //添加视图类
        List<ViewObject> viewObjects = new ArrayList<>();
        //判断当前用户是否关注问题发布者
        Follow follow = null;
        if (holder.getUser() != null) {
            follow = userFeign.isFollow(holder.getUser().getUserId(), user.getUserId(), "用户");
        }
        //分页获取对问题的回答,数量
        List<Answer> answerToQuestion = questionAnswerFeign.getAnswer(0, qid, 1, 5);
        int page = questionAnswerFeign.getAnswer(0, qid, 0, 0).size();
        page = page % 5 == 0 ? page / 5 : page / 5 + 1;
        //数据放进视图类
        if (answerToQuestion != null) {
            for (Answer answer : answerToQuestion) {
                //根据用户id判断是否点过赞
                Zan zan = null;
                if (holder.getUser() != null) {
                    zan = questionAnswerFeign.getZanByAnswerIdAndUserId(answer.getAnswerId(), holder.getUser().getUserId());
                }
                //添加视图类
                List<ViewObject> viewObjects1 = new ArrayList<>();
                //获取对答案的回答
                List<Answer> answers = questionAnswerFeign.getAnswer(1, answer.getAnswerId(), 0, 0);
                //获取赞的数量
                int zanCount = questionAnswerFeign.getZan(answer.getAnswerId());
                //获取回答问题的用户
                User user1 = userFeign.getUser(answer.getUserId());
                if (answers != null) {
                    //获取回答答案的用户和回复
                    for (Answer var : answers) {
                        ViewObject viewObject = new ViewObject();
                        User user2 = userFeign.getUser(var.getUserId());
                        viewObject.put("user", user2);
                        viewObject.put("answer", var);
                        viewObjects1.add(viewObject);
                    }
                }
                ViewObject viewObject = new ViewObject();
                viewObject.put("zanNote", zan);
                viewObject.put("answer", answer);
                viewObject.put("user", user1);
                viewObject.put("zan", zanCount);
                viewObject.put("viewObjects1", viewObjects1);
                viewObjects.add(viewObject);
            }
        }

        //浏览数+1
        questionAnswerFeign.addVisitTime(qid);
        model.addAttribute("question", question);
        model.addAttribute("follow", follow);
        model.addAttribute("user", user);
        model.addAttribute("answerToQuestion", answerToQuestion);
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("page", page);
        return "question";
    }
}
