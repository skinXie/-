package com.social.articleservice.controller;

import com.social.articleservice.service.AnswerService;
import common.questionAnswer.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;

    //添加回答
    @PostMapping("/api/answer/adding")
    public int addAnswerToQuestion(@RequestParam("entityType") int entityType, @RequestParam("userId") int userId,
                                   @RequestParam("entityId") int entityId, @RequestParam("content") String content) {
        return answerService.addAnswer(entityType, userId, entityId, content);
    }


    //根据实体Id分页获取回答(分页数量10)
    @GetMapping("/api/answer")
    public List<Answer> getAnswer(@RequestParam("entityType") int entityType, @RequestParam("entityId") int entityId,
                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "count", defaultValue = "0", required = false) int count) {
        List<Answer> answers;
        //获取所有
        if (page == 0 || count == 0)
            answers = answerService.getAnswer(entityType, entityId);
            //分页获取
        else
            answers = answerService.getAnswer(entityType, entityId, page, count);
        return answers;
    }

    //获取用户的回答
    @GetMapping("/api/answer/user/{uid}")
    public List<Answer> getAnswerByUid(@PathVariable("uid") int uid, @RequestParam("entityType") int entityType) {
        return answerService.getAnswerByUid(uid, entityType);
    }

}
