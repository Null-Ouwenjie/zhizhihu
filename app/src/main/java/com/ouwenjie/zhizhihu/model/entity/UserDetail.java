package com.ouwenjie.zhizhihu.model.entity;

import java.util.List;

/**
 * Created by Jack on 2016/3/29.
 */
public class UserDetail {

    String error;
    String name;
    String avatar;
    String signature;
    String description;
    Detail detail;
    Star star;
    List<Trend> trend;
    List<TopAnswer> topanswers;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public List<Trend> getTrend() {
        return trend;
    }

    public void setTrend(List<Trend> trend) {
        this.trend = trend;
    }

    public List<TopAnswer> getTopanswers() {
        return topanswers;
    }

    public void setTopanswers(List<TopAnswer> topanswers) {
        this.topanswers = topanswers;
    }

    public class Detail {
        String ask;
        String answer;
        String post;
        String agree;
        String ratio;
        String followee;
        String follower;
        String agreei;
        String agreeiratio;
        String agreeiw;
        String agreeiratiow;
        String followeri;
        String followiratio;
        String followeriw;
        String followiratiow;
        String thanks;
        String tratio;
        String fav;
        String fratio;
        String logs;
        String mostvote;
        String mostvotepercent;
        String mostvote5;
        String mostvote5percent;
        String mostvote10;
        String mostvote10percent;
        String count10000;
        String count5000;
        String count2000;
        String count1000;
        String count500;
        String count200;
        String count100;

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getAgree() {
            return agree;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getFollowee() {
            return followee;
        }

        public void setFollowee(String followee) {
            this.followee = followee;
        }

        public String getFollower() {
            return follower;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }

        public String getAgreei() {
            return agreei;
        }

        public void setAgreei(String agreei) {
            this.agreei = agreei;
        }

        public String getAgreeiratio() {
            return agreeiratio;
        }

        public void setAgreeiratio(String agreeiratio) {
            this.agreeiratio = agreeiratio;
        }

        public String getAgreeiw() {
            return agreeiw;
        }

        public void setAgreeiw(String agreeiw) {
            this.agreeiw = agreeiw;
        }

        public String getAgreeiratiow() {
            return agreeiratiow;
        }

        public void setAgreeiratiow(String agreeiratiow) {
            this.agreeiratiow = agreeiratiow;
        }

        public String getFolloweri() {
            return followeri;
        }

        public void setFolloweri(String followeri) {
            this.followeri = followeri;
        }

        public String getFollowiratio() {
            return followiratio;
        }

        public void setFollowiratio(String followiratio) {
            this.followiratio = followiratio;
        }

        public String getFolloweriw() {
            return followeriw;
        }

        public void setFolloweriw(String followeriw) {
            this.followeriw = followeriw;
        }

        public String getFollowiratiow() {
            return followiratiow;
        }

        public void setFollowiratiow(String followiratiow) {
            this.followiratiow = followiratiow;
        }

        public String getThanks() {
            return thanks;
        }

        public void setThanks(String thanks) {
            this.thanks = thanks;
        }

        public String getTratio() {
            return tratio;
        }

        public void setTratio(String tratio) {
            this.tratio = tratio;
        }

        public String getFav() {
            return fav;
        }

        public void setFav(String fav) {
            this.fav = fav;
        }

        public String getFratio() {
            return fratio;
        }

        public void setFratio(String fratio) {
            this.fratio = fratio;
        }

        public String getLogs() {
            return logs;
        }

        public void setLogs(String logs) {
            this.logs = logs;
        }

        public String getMostvote() {
            return mostvote;
        }

        public void setMostvote(String mostvote) {
            this.mostvote = mostvote;
        }

        public String getMostvotepercent() {
            return mostvotepercent;
        }

        public void setMostvotepercent(String mostvotepercent) {
            this.mostvotepercent = mostvotepercent;
        }

        public String getMostvote5() {
            return mostvote5;
        }

        public void setMostvote5(String mostvote5) {
            this.mostvote5 = mostvote5;
        }

        public String getMostvote5percent() {
            return mostvote5percent;
        }

        public void setMostvote5percent(String mostvote5percent) {
            this.mostvote5percent = mostvote5percent;
        }

        public String getMostvote10() {
            return mostvote10;
        }

        public void setMostvote10(String mostvote10) {
            this.mostvote10 = mostvote10;
        }

        public String getMostvote10percent() {
            return mostvote10percent;
        }

        public void setMostvote10percent(String mostvote10percent) {
            this.mostvote10percent = mostvote10percent;
        }

        public String getCount10000() {
            return count10000;
        }

        public void setCount10000(String count10000) {
            this.count10000 = count10000;
        }

        public String getCount5000() {
            return count5000;
        }

        public void setCount5000(String count5000) {
            this.count5000 = count5000;
        }

        public String getCount2000() {
            return count2000;
        }

        public void setCount2000(String count2000) {
            this.count2000 = count2000;
        }

        public String getCount1000() {
            return count1000;
        }

        public void setCount1000(String count1000) {
            this.count1000 = count1000;
        }

        public String getCount500() {
            return count500;
        }

        public void setCount500(String count500) {
            this.count500 = count500;
        }

        public String getCount200() {
            return count200;
        }

        public void setCount200(String count200) {
            this.count200 = count200;
        }

        public String getCount100() {
            return count100;
        }

        public void setCount100(String count100) {
            this.count100 = count100;
        }
    }

    public class Star {
        String answerrank;
        String agreerank;
        String ratiorank;
        String followerrank;
        String favrank;
        String count1000rank;
        String count100rank;

        public String getAnswerrank() {
            return answerrank;
        }

        public void setAnswerrank(String answerrank) {
            this.answerrank = answerrank;
        }

        public String getAgreerank() {
            return agreerank;
        }

        public void setAgreerank(String agreerank) {
            this.agreerank = agreerank;
        }

        public String getRatiorank() {
            return ratiorank;
        }

        public void setRatiorank(String ratiorank) {
            this.ratiorank = ratiorank;
        }

        public String getFollowerrank() {
            return followerrank;
        }

        public void setFollowerrank(String followerrank) {
            this.followerrank = followerrank;
        }

        public String getFavrank() {
            return favrank;
        }

        public void setFavrank(String favrank) {
            this.favrank = favrank;
        }

        public String getCount1000rank() {
            return count1000rank;
        }

        public void setCount1000rank(String count1000rank) {
            this.count1000rank = count1000rank;
        }

        public String getCount100rank() {
            return count100rank;
        }

        public void setCount100rank(String count100rank) {
            this.count100rank = count100rank;
        }
    }

    public class Trend {
        String answer;
        String agree;
        String follower;
        String date;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAgree() {
            return agree;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getFollower() {
            return follower;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public class TopAnswer {
        String title;
        String link;
        String agree;
        String date;
        String ispost;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAgree() {
            return agree;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIspost() {
            return ispost;
        }

        public void setIspost(String ispost) {
            this.ispost = ispost;
        }
    }
}
