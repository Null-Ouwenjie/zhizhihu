package com.lanjiaai.kzhihu.ui.viewInterface;

import com.lanjiaai.kzhihu.model.entity.Answer;

import java.util.List;

/**
 * Created by 文杰 on 2015/10/27.
 */
public interface PostAnswerViewInterface extends MVPView {

    void initList(List<Answer> answers);

}
