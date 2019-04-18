package com.social.articleservice.controller;

import com.social.articleservice.service.QuestionService;
import common.questionAnswer.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    //分页获取问题(分页数量10）
    @GetMapping("/api/question")
    public List<Question> getQuestion(@RequestParam(value = "page") int page, @RequestParam(value = "count") int count) {
        List<Question> questions = questionService.getQuestiobByPage(page, count);
        return questions;
    }

    //添加问题
    @PostMapping("/api/question/ask")
    public int askQuestion(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("tag") String tag, @RequestParam("userId") int userId) {
        return questionService.askQuestion(title, content, tag, userId);
    }

    //获取所有问题
    @GetMapping("/api/question/all")
    public List<Question> getAllQuestion() {
        List<Question> questions = questionService.getAllQuestion();
        return questions;
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
