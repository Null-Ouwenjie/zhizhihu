package com.ouwenjie.zhizhihu.model.entity;

import io.realm.RealmObject;

/**
 * Created by 文杰 on 2015/10/20.
 */
public class Answer extends RealmObject {

    String title;
    String time;
    String summary;
    String questionid;
    String answerid;
    String authorname;
    String authorhash;
    String avatar;
    String vote;

    public Answer() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getAnswerid() {
        return answerid;
    }

    public void setAnswerid(String answerid) {
        this.answerid = answerid;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAuthorhash() {
        return authorhash;
    }

    public void setAuthorhash(String authorhash) {
        this.authorhash = authorhash;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (title != null ? !title.equals(answer.title) : answer.title != null) return false;
        if (time != null ? !time.equals(answer.time) : answer.time != null) return false;
        if (summary != null ? !summary.equals(answer.summary) : answer.summary != null)
            return false;
        if (questionid != null ? !questionid.equals(answer.questionid) : answer.questionid != null)
            return false;
        if (answerid != null ? !answerid.equals(answer.answerid) : answer.answerid != null)
            return false;
        if (authorname != null ? !authorname.equals(answer.authorname) : answer.authorname != null)
            return false;
        if (authorhash != null ? !authorhash.equals(answer.authorhash) : answer.authorhash != null)
            return false;
        if (avatar != null ? !avatar.equals(answer.avatar) : answer.avatar != null) return false;
        return vote != null ? vote.equals(answer.vote) : answer.vote == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (questionid != null ? questionid.hashCode() : 0);
        result = 31 * result + (answerid != null ? answerid.hashCode() : 0);
        result = 31 * result + (authorname != null ? authorname.hashCode() : 0);
        result = 31 * result + (authorhash != null ? authorhash.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (vote != null ? vote.hashCode() : 0);
        return result;
    }

}
