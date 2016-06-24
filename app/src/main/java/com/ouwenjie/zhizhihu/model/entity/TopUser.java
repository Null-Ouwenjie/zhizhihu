package com.ouwenjie.zhizhihu.model.entity;

/**
 * Created by 文杰 on 2015/10/23.
 */
public class TopUser {

    // 基本信息
    String id;
    String name;
    String hash;
    String avatar;
    String signature;

    // 根据传入的参数，这下面只会有一个出现
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
