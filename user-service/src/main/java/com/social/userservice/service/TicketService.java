package com.social.userservice.service;

import com.social.userservice.dao.TicketDao;
import common.user.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    //根据用户Id查询ticket
    public Ticket getTicketById(int userId) {
        return ticketDao.selectTicketByUserId(userId);
    }

    //根据ticketId查询ticket
    public Ticket getTicketByTicketId(int ticketId) {
        return ticketDao.selectTicketByTicketId(ticketId);
    }
}
