package com.social.userservice.service;

import com.social.userservice.dao.LetterDao;
import com.social.userservice.dao.UserDao;
import common.user.Letter;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterService {
    @Autowired
    LetterDao letterDao;
    @Autowired
    UserDao userDao;

    //查找未读的站内信
    public List<Letter> getLetterByUserId(int userId) {
        return letterDao.selectUnreadLetterByUserId(userId);
    }

    //发送站内信
    public boolean sendLetter(int senderId, String userName, String content) {
        User user = userDao.selectUserByUserName(userName);
        if (user == null)
            return false;
        Letter letter = new Letter();
        letter.setLetterContent(content);
        letter.setLetterStatus(0);
        letter.setReceiverId(user.getUserId());
        letter.setSenderId(senderId);
        return letterDao.insertLetter(letter);
    }

    //删除站内信
    public boolean deleteLetter(int letterId) {
        return letterDao.deleteLetterByLetterId(letterId);
    }
}
