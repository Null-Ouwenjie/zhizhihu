package com.lanjiaai.kzhihu.model.entity;

/**
 * Created by Jack on 2016/3/15.
 */
public class Topic {

    String topicTxt;
    String topicImg;
    String targetUrl;

    public String getTopicTxt() {
        return topicTxt;
    }

    public void setTopicTxt(String topicTxt) {
        this.topicTxt = topicTxt;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicTxt='" + topicTxt + '\'' +
                ", topicImg='" + topicImg + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                '}';
    }
}
