package com.lanjiaai.kzhihu.model.entity;

import java.util.List;

/**
 * Created by Jack on 2016/3/15.
 */
public class TabData {

    String tabTxt;
    List<Topic> topics;

    public String getTabTxt() {
        return tabTxt;
    }

    public void setTabTxt(String tabTxt) {
        this.tabTxt = tabTxt;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "TabData{" +
                "tabTxt='" + tabTxt + '\'' +
                ", topics=" + topics +
                '}';
    }
}
