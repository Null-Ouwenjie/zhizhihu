package com.lanjiaai.kzhihu.model.entity;

/**
 * 文章列表
 * Created by 文杰 on 2015/10/19.
 */
public class Post {

    String id;
    String date;    // 发表日期
    String name;    // 文章名称 (yesterday / recent / archive)
    String pic;     // 抬头图 url
    String publishtime;     // 发表时间戳
    int count;      // 文章包含答案数量
    String excerpt;         // 摘要文字

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", publishtime='" + publishtime + '\'' +
                ", count=" + count +
                ", excerpt='" + excerpt + '\'' +
                '}';
    }

}
