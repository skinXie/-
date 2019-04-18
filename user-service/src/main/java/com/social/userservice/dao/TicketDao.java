package com.social.userservice.dao;

import common.user.Ticket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TicketDao {
    //查询用户的ticket
    @Select("select * from ticket where user_id=#{userId} and valid_time>now()")
    Ticket selectTicketByUserId(int userId);

    //更新ticket
    @Update("update ticket set valid_time=#{validTime} where user_id=#{userId}")
    boolean updateTicket(Ticket ticket);

    //删除ticket
    @Delete("Delete from ticket where user_id=#{userId}")
    boolean deleteTicket(Ticket ticket);

    //添加ticket
    @Insert("insert into ticket (user_id,valid_time) values (#{userId},#{validTime})")
    @Options(keyProperty = "ticketId", useGeneratedKeys = true)
    boolean insertTicket(Ticket ticket);

    //查询ticket
    @Select("select * from ticket where ticket_id=#{ticketId} and valid_time>now()")
    Ticket selectTicketByTicketId(int ticketId);
}
