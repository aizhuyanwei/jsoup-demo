package com.demo.jsoup.pojo;

/**
 * 新闻信息
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月15日  14:06 by zyw9527
 */

public class News {


    /**
     * 新闻标题
     */
    private String title;
    /**
     * url
     */
    private String url;
    /**
     * 内容
     */
    private String content;
    /**
     * 来源
     */
    private String source;
    /**
     * 图片地址
     */
    private String picUrl;

    public String getTitle() {
        return this.title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;

    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return this.source;

    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPicUrl() {
        return this.picUrl;

    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
