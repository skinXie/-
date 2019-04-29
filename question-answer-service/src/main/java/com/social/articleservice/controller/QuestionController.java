package com.social.articleservice.controller;

import com.alibaba.fastjson.JSON;
import com.social.articleservice.service.QuestionService;
import common.questionAnswer.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    //获取问题(可分页）
    @GetMapping("/api/question")
    public List<Question> getQuestion(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(value = "count", defaultValue = "0", required = false) int count) {

        List<Question> questions = questionService.getQuestiobByPage(page, count);
        return questions;
    }

    //添加问题
    @PostMapping("/api/question/ask")
    public int askQuestion(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("tags[]") List<String> tags,
                           @RequestParam("userId") int userId) {

        return questionService.askQuestion(title, content, tags, userId);
    }


    //获取该用户的提问
    @GetMapping("/api/question/user/{userId}")
    public List<Question> getQuestionByUserId(@PathVariable("userId") int userId) {
        List<Question> questions = questionService.getQuestionsByUserId(userId);
        return questions;
    }

    //根据问题id获取问题
    @GetMapping("/api/question/{id}")
    public Question getQuestionByQuestionId(@PathVariable("id") int id) {
        return questionService.getQuestionByQuestionId(id);
    }

    //浏览数+1
    @PostMapping("/api/question/visit")
    public int addVisitTime(@RequestParam("qid") int qid) {
        Question question = questionService.getQuestionByQuestionId(qid);
        int time = question.getVisitTime() + 1;
        return questionService.addVisitTime(time, qid);
    }


}
