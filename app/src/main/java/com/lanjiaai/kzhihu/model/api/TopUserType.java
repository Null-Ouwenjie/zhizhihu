package com.lanjiaai.kzhihu.model.api;

/**
 * Created by Jack on 2016/2/3.
 */
public enum TopUserType {

    ask,             // 提问数
    answer,          // 回答数
    post,            // 专栏文章数
    agree,           // 赞同数
    ratio,           // 平均赞同（总赞同数/(回答+专栏)）
    followee,        // 关注数
    follower,        // 被关注数（粉丝）

    agreei,          // 1日赞同数增加
    agreeiratio,     // 1日赞同数增幅
    agreeiw,         // 7日赞同数增加
    agreeiratiow,    // 7日赞同数增幅

    followeri,       // 1日被关注数增加
    followiratio,    // 1日被关注数增幅
    followeriw,      // 7日被关注数增加
    followiratiow,   // 7日被关注数增幅

    thanks,          // 感谢数
    tratio,          // 感谢/赞同比
    fav,             // 收藏数
    fratio,          // 收藏/赞同比
    logs,            // 公共编辑数

    mostvote,            // 最高赞同
    mostvotepercent,     // 最高赞同占比
    mostvote5,           // 前5赞同
    mostvote5percent,    // 前5赞同占比
    mostvote10,          // 前10赞同
    mostvote10percent,   // 前10赞同占比

    count10000,          // 赞同≥10000答案数
    count5000,           // 赞同≥5000答案数
    count2000,           // 赞同≥2000答案数
    count1000,           // 赞同≥1000答案数
    count500,            // 赞同≥500答案数
    count200,            // 赞同≥200答案数
    count100,            // 赞同≥100答案数

}
