package common.questionAnswer;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.ibatis.annotations.Result;

import java.util.Date;
import java.util.List;

@Data
public class Question {
    private int questionId;
    private int userId;
    private String questionTitle;
    private String questionContent;
    private Date questionDate;
    private List<String> tag;
    private volatile int visitTime;
    public String toString() {
        return JSON.toJSONString(this);
    }
}
