package common.user;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    private int ticketId = -1;
    private int userId;
    private Date validTime;

    public String toString() {
        return JSON.toJSONString(this);
    }
}
