package common.user;

import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    private int ticketId = -1;
    private int userId;
    private Date validTime;

    public int getUserId() {
        return userId;
    }
}
