package common.questionAnswer;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/*
定义赞的实体类
 */
@Data
public class Zan {
    private int zanId;
    private int zanUserId;
    private int zanAnswerId;
    public String toString() {
        return JSON.toJSONString(this);
    }
}
