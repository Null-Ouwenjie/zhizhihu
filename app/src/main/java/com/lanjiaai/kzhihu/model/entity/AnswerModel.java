package com.lanjiaai.kzhihu.model.entity;

import java.util.List;

/**
 * Created by 文杰 on 2015/10/20.
 */
public class AnswerModel extends BaseModel {

    int count;
    List<Answer> answers;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
