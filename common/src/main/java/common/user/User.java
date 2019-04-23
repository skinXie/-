package common.user;

import lombok.Data;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(userId, user.userId)
                .append(account, user.account)
                .append(userName, user.userName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(account)
                .append(userName)
                .toHashCode();
    }
}
