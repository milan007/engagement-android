package com.example.android.engagementandroid.data;

/**
 * Created by Milan on 3/8/2015.
 */
public class ReplyDto {

    private Integer questionId;
    private Integer answerId;
    private Long created;

    @Override
    public String toString() {
        return "ReplyDto{" +
                "questionId=" + questionId +
                ", answerId=" + answerId +
                ", created=" + created +
                '}';
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
