package common.questionAnswer;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
    private int questionId;
    private int userId;
    private String questionTitle;
    private String questionContent;
    private Date questionDate;
    private String tag;
    private volatile int visitTime;


}
