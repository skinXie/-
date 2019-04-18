package common.user;

import lombok.Data;

@Data
public class Letter {
    //站内信的id
    private int letterId;
    //发送者id
    private int senderId;
    //接收者id
    private int receiverId;
    //站内信内容
    private String letterContent;
    //站内信状态(0未读，1已读)
    private int letterStatus;


}
