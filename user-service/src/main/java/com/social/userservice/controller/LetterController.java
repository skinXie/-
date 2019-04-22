package com.social.userservice.controller;

import com.social.userservice.service.LetterService;
import common.user.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LetterController {
    @Autowired
    LetterService letterService;

    //查找未读的站内信
    @GetMapping(value = "/api/letter/{uid}")
    public List<Letter> getLetter(@PathVariable("uid") int userId) {
        return letterService.getLetterByUserId(userId);
    }

    //发送站内信
    @PostMapping(value = "/api/letter/send")
    public String sendLetter(@RequestParam("sender")int senderId, @RequestParam("receiver") String userName, @RequestParam("content") String content) {
        if (letterService.sendLetter(senderId, userName, content))
            return "发送成功";
        return "发送失败";
    }

    //删除站内信
    @PostMapping(value = "/api/letter/delete")
    public String deleteLetter(@RequestParam("letterId") int letterId) {
        if (letterService.deleteLetter(letterId))
            return "删除成功";
        return "删除失败";
    }

}
