package com.social.articleservice.service;

import com.social.articleservice.dao.ZanDao;
import common.questionAnswer.Zan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZanService {
    @Autowired
    ZanDao zanDao;

    //查询赞的数量
    public int getZanById(int answerId) {
        return zanDao.selectZanByAnswerId(answerId);
    }

    //点赞
    public boolean dianZan(int userId, int answerId) {
        if (zanDao.selectZanByAnswerIdAndUserId(answerId, userId) == null)
            return zanDao.insertZan(userId, answerId);
        return false;
    }

    //查询点赞记录
    public Zan getZanByAnswerIdAndUserId(int answerId, int userId) {
        return zanDao.selectZanByAnswerIdAndUserId(answerId, userId);
    }
}
