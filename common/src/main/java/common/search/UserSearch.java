package common.search;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class UserSearch {
    private int userId;
    private String userName;
    private String headUrl;
    private String category;
    public String toString() {
        return JSON.toJSONString(this);
    }
}
