package com.social.articleservice.controller;

import com.social.articleservice.service.ZanService;
import common.questionAnswer.Zan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ZanController {
    @Autowired
    ZanService zanService;

    //根据回答Id获取赞的数量
    @GetMapping("/api/zan/count")
    public int getZan(@RequestParam("answerId") int answerId) {
        int zanCount = zanService.getZanById(answerId);
        return zanCount;
    }

    //点赞
    @PostMapping("/api/zan/add")
    public boolean dianZan(@RequestParam("userId") int userId, @RequestParam("answerId") int answerId) {
        return zanService.dianZan(userId, answerId);
    }

    //查询点赞记录
    @GetMapping("/api/zan")
    public Zan getZanByAnswerIdAndUserId(@RequestParam("answerId") int answerId, @RequestParam("userId") int userId) {
        return zanService.getZanByAnswerIdAndUserId(answerId, userId);
    }
}
