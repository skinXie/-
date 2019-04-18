/**
 * @param answerId 回答的id
 * @param userId 回答者的id
 * @param entityId 被回答的实体id
 * @param entityType 被回答的实体类型（0是问题，1是答案）
 * @param answerContent 回答的内容
 */

package common.questionAnswer;

import lombok.Data;

@Data
public class Answer {
    private int answerId;
    private int userId;
    private int entityId;
    private int entityType;
    private String answerContent;


}
