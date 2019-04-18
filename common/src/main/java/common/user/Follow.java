package common.user;

import lombok.Data;

/*
定义关注实体类
 */
@Data
public class Follow {
    //id
    private int followId;
    //关注者的id
    private int userId;
    //被关注实体的id
    private int entityId;
    //关注的类型
    private String type;
}
