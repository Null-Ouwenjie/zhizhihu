package com.ouwenjie.zhizhihu.model.entity;

import java.util.List;

/**
 * Created by Jack on 2016/2/3.
 */
public class UserInfo extends BaseModel {

    String name;        // 用户名
    String avatar;      // 用户头像 URL
    String signature;   // 用户签名
    String description; // 自我描述

    Detail detail;      // 详细数据
    Star star;          // 七星阵排名
    List<Trend> trend;  // 近日动态
    List<TopAnswers> topnswers; //高票答案

    public class Detail {

        String ask;             // 提问数
        String answer;          // 回答数
        String post;            // 专栏文章数
        String agree;           //  赞同数
        String ratio;           // 平均赞同（总赞同数/(回答+专栏)）
        String followee;        // 关注数
        String follower;        // 被关注数（粉丝）

        String agreei;          // 1日赞同数增加
        String agreeiratio;     // 1日赞同数增幅
        String agreeiw;         // 7日赞同数增加
        String agreeiratiow;    // 7日赞同数增幅

        String followeri;       // 1日被关注数增加
        String followiratio;    // 1日被关注数增幅
        String followeriw;      // 7日被关注数增加
        String followiratiow;   // 7日被关注数增幅

        String thanks;          // 感谢数
        String tratio;          // 感谢/赞同比
        String fav;             // 收藏数
        String fratio;          // 收藏/赞同比
        String logs;            // 公共编辑数

        String mostvote;            // 最高赞同
        String mostvotepercent;     // 最高赞同占比
        String mostvote5;           // 前5赞同
        String mostvote5percent;    // 前5赞同占比
        String mostvote10;          // 前10赞同
        String mostvote10percent;   // 前10赞同占比

        String count10000;          // 赞同≥10000答案数
        String count5000;           // 赞同≥5000答案数
        String count2000;           // 赞同≥2000答案数
        String count1000;           // 赞同≥1000答案数
        String count500;            // 赞同≥500答案数
        String count200;            // 赞同≥200答案数
        String count100;            // 赞同≥100答案数

        public void setAsk(String ask) {
            this.ask = ask;
        }

        public String getAsk() {
            return this.ask;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswer() {
            return this.answer;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getPost() {
            return this.post;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getAgree() {
            return this.agree;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getRatio() {
            return this.ratio;
        }

        public void setFollowee(String followee) {
            this.followee = followee;
        }

        public String getFollowee() {
            return this.followee;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }

        public String getFollower() {
            return this.follower;
        }

        public void setAgreei(String agreei) {
            this.agreei = agreei;
        }

        public String getAgreei() {
            return this.agreei;
        }

        public void setAgreeiratio(String agreeiratio) {
            this.agreeiratio = agreeiratio;
        }

        public String getAgreeiratio() {
            return this.agreeiratio;
        }

        public void setAgreeiw(String agreeiw) {
            this.agreeiw = agreeiw;
        }

        public String getAgreeiw() {
            return this.agreeiw;
        }

        public void setAgreeiratiow(String agreeiratiow) {
            this.agreeiratiow = agreeiratiow;
        }

        public String getAgreeiratiow() {
            return this.agreeiratiow;
        }

        public void setFolloweri(String followeri) {
            this.followeri = followeri;
        }

        public String getFolloweri() {
            return this.followeri;
        }

        public void setFollowiratio(String followiratio) {
            this.followiratio = followiratio;
        }

        public String getFollowiratio() {
            return this.followiratio;
        }

        public void setFolloweriw(String followeriw) {
            this.followeriw = followeriw;
        }

        public String getFolloweriw() {
            return this.followeriw;
        }

        public void setFollowiratiow(String followiratiow) {
            this.followiratiow = followiratiow;
        }

        public String getFollowiratiow() {
            return this.followiratiow;
        }

        public void setThanks(String thanks) {
            this.thanks = thanks;
        }

        public String getThanks() {
            return this.thanks;
        }

        public void setTratio(String tratio) {
            this.tratio = tratio;
        }

        public String getTratio() {
            return this.tratio;
        }

        public void setFav(String fav) {
            this.fav = fav;
        }

        public String getFav() {
            return this.fav;
        }

        public void setFratio(String fratio) {
            this.fratio = fratio;
        }

        public String getFratio() {
            return this.fratio;
        }

        public void setLogs(String logs) {
            this.logs = logs;
        }

        public String getLogs() {
            return this.logs;
        }

        public void setMostvote(String mostvote) {
            this.mostvote = mostvote;
        }

        public String getMostvote() {
            return this.mostvote;
        }

        public void setMostvotepercent(String mostvotepercent) {
            this.mostvotepercent = mostvotepercent;
        }

        public String getMostvotepercent() {
            return this.mostvotepercent;
        }

        public void setMostvote5(String mostvote5) {
            this.mostvote5 = mostvote5;
        }

        public String getMostvote5() {
            return this.mostvote5;
        }

        public void setMostvote5percent(String mostvote5percent) {
            this.mostvote5percent = mostvote5percent;
        }

        public String getMostvote5percent() {
            return this.mostvote5percent;
        }

        public void setMostvote10(String mostvote10) {
            this.mostvote10 = mostvote10;
        }

        public String getMostvote10() {
            return this.mostvote10;
        }

        public void setMostvote10percent(String mostvote10percent) {
            this.mostvote10percent = mostvote10percent;
        }

        public String getMostvote10percent() {
            return this.mostvote10percent;
        }

        public void setCount10000(String count10000) {
            this.count10000 = count10000;
        }

        public String getCount10000() {
            return this.count10000;
        }

        public void setCount5000(String count5000) {
            this.count5000 = count5000;
        }

        public String getCount5000() {
            return this.count5000;
        }

        public void setCount2000(String count2000) {
            this.count2000 = count2000;
        }

        public String getCount2000() {
            return this.count2000;
        }

        public void setCount1000(String count1000) {
            this.count1000 = count1000;
        }

        public String getCount1000() {
            return this.count1000;
        }

        public void setCount500(String count500) {
            this.count500 = count500;
        }

        public String getCount500() {
            return this.count500;
        }

        public void setCount200(String count200) {
            this.count200 = count200;
        }

        public String getCount200() {
            return this.count200;
        }

        public void setCount100(String count100) {
            this.count100 = count100;
        }

        public String getCount100() {
            return this.count100;
        }

    }

    public class Star {

        String answerrank;      // 回答数+专栏文章数排名
        String agreerank;       // 赞同数排名
        String ratiorank;       // 平均赞同排名
        String followerrank;    // 被关注数排名
        String favrank;         // 收藏数排名
        String count1000rank;   // 赞同超1000的回答数排名
        String count100rank;    // 赞同超100的回答数排名

        public void setAnswerrank(String answerrank) {
            this.answerrank = answerrank;
        }

        public String getAnswerrank() {
            return this.answerrank;
        }

        public void setAgreerank(String agreerank) {
            this.agreerank = agreerank;
        }

        public String getAgreerank() {
            return this.agreerank;
        }

        public void setRatiorank(String ratiorank) {
            this.ratiorank = ratiorank;
        }

        public String getRatiorank() {
            return this.ratiorank;
        }

        public void setFollowerrank(String followerrank) {
            this.followerrank = followerrank;
        }

        public String getFollowerrank() {
            return this.followerrank;
        }

        public void setFavrank(String favrank) {
            this.favrank = favrank;
        }

        public String getFavrank() {
            return this.favrank;
        }

        public void setCount1000rank(String count1000rank) {
            this.count1000rank = count1000rank;
        }

        public String getCount1000rank() {
            return this.count1000rank;
        }

        public void setCount100rank(String count100rank) {
            this.count100rank = count100rank;
        }

        public String getCount100rank() {
            return this.count100rank;
        }

    }

    public class Trend {

        String answer;      // 回答数+专栏文章数
        String agree;       // 赞同数
        String follower;    // 被关注数
        String date;        // 日期，格式为2014-09-25

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswer() {
            return this.answer;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getAgree() {
            return this.agree;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }

        public String getFollower() {
            return this.follower;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

    }

    public class TopAnswers {

        String title;           // 标题
        String link;            // 链接地址（不含域名）
        String agree;           // 赞同数
        String date;            // 日期，格式为2014-09-25
        String ispost;          // 是否专栏文章，用于判断链接域名是zhihu.com还是zhuanlan.zhihu.com

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setAgree(String agree) {
            this.agree = agree;
        }

        public String getAgree() {
            return this.agree;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setIspost(String ispost) {
            this.ispost = ispost;
        }

        public String getIspost() {
            return this.ispost;
        }

    }

}
