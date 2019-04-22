package com.social.userservice.controller;

import com.social.userservice.service.FollowService;

import common.questionAnswer.*;
import common.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {
    @Autowired
    FollowService followService;

    //获取用户关注的用户
    @GetMapping("/api/user/following-user/{id}")
    public List<User> getFollowUser(@PathVariable("id") int userId) {
        return followService.getFollowUser(userId);
    }

    //获取用户关注的问题
    @GetMapping("/api/user/following-question/{id}")
    public List<Question> getFollowQuestion(@PathVariable("id") int userId) {
        return followService.getFollowQuestion(userId);
    }

    //用户关注
    @PostMapping(value = "/api/following-user")
    public String FollowUser(@RequestParam("followerId") int userId, @RequestParam("followedId") int entityId,@RequestParam("type")String type) {
        if (followService.isFollow(userId, entityId, type) == null) {
            followService.addFollow(userId, entityId, type);
            return "关注成功";
        }
        followService.cancleFollow(userId, entityId, type);
        return "关注取消";
    }

    //问题关注
    @RequestMapping(value = "/api/following-question/{id}", method = RequestMethod.POST)
    public String FollowQuestion(int userId, @PathVariable("id") int entityId) {
        if (followService.isFollow(userId, entityId, "问题") == null) {
            followService.addFollow(userId, entityId, "问题");
            return "关注成功";
        }
        followService.cancleFollow(userId, entityId, "问题");
        return "关注取消";
    }

    //判断A是否关注B
    @GetMapping(value = "/api/follow")
    public Follow isFollow(@RequestParam("userId") int userId, @RequestParam("entityId") int entityId, @RequestParam("type") String type) {
        return followService.isFollow(userId, entityId, type);
    }
    //获取关注某个问题的记录
    @GetMapping(value="/api/follow/question/{qid}")
    public List<Follow> getQuestionFollow(@PathVariable("qid")int qid){


    }


}
