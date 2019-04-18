package common.user;

import lombok.Data;

/*
定义用户实体类
 */
@Data
public class User {
    private int userId;
    private String account;
    private String password;
    private String userName;
    private String salt;
    private String headUrl;
    private String activeCode;
    private String mailbox;
}
