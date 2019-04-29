package common.search;


import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.sql.Date;

@Data
public class QuestionSearch {
    private int questionId;
    private String questionTitle;
    private int visitTime;
    private int answerTime;
    private Date questionDate;
    private int userId;
    private String userName;
    private String headUrl;
    private String category = "问题";
    public String toString() {
        return JSON.toJSONString(this);
    }

}
