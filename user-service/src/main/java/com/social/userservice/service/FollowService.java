package com.social.userservice.service;

import com.social.userservice.async.Consumer;
import com.social.userservice.dao.FollowDao;
import com.social.userservice.dao.UserDao;
import common.feign.QuestionAnswerFeign;
import common.questionAnswer.Question;
import common.user.Follow;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionAnswerFeign questionAnswerFeign;
    @Autowired
    Consumer consumer;

    //查询用户关注的用户
    public List<User> getFollowUser(int userId) {
        List<Follow> follows = followDao.selectFollowedId(userId, "用户");
        List<User> items = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userDao.selectUserById(follow.getEntityId());
            items.add(user);
        }
        return items;
    }

    //查询用户关注的问题
    public List<Question> getFollowQuestion(int userId) {
        List<Follow> follows = followDao.selectFollowedId(userId, "问题");
        List<Question> items = new ArrayList<>();
        for (Follow follow : follows) {
            Question q = questionAnswerFeign.getQuestionById(follow.getEntityId());
            items.add(q);
        }
        return items;
    }

    //添加关注
    public boolean addFollow(int userId, int entityId, String type) {
        return followDao.insertGuanzhu(userId, entityId, type);
    }

    //取消关注
    public boolean cancleFollow(int userId, int entityId, String type) {
        return followDao.deleteGuanzhu(userId, entityId, type);
    }

    //判断是否存在关注
    public Follow isFollow(int userId, int entityId, String type) {
        return followDao.isFollow(userId, entityId, type);
    }

    //获取关注某个实体的记录
    public List<Follow> getFollowByEntityId(int entityId, String type) {
        return followDao.selectFollowsByEntityId(entityId, type);
    }

    //获取A用户和B用户的共同关注
    public List<User> getSameFollow(int uid1, int uid2) {
        List<User> list1 = getFollowUser(uid1);
        List<User> list2 = getFollowUser(uid2);
        Set<User> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return set.size() == 0 ? null : new ArrayList<User>(set);
    }
}
