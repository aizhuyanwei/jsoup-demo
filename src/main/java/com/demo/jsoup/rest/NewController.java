package com.demo.jsoup.rest;

import com.demo.jsoup.common.Constant;
import com.demo.jsoup.common.Result;
import com.demo.jsoup.pojo.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取新闻信息
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月15日  14:10 by zyw9527
 */
@RestController
public class NewController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("queryNews")
    private Result queryNews(String key ,int limit){

        List<News> list = new ArrayList<>();
        try {
            int count=0;
            while (true){
                queryNewsPage(key, limit, list,count);
                if (list.size()<limit){
                    count++;
                }else{
                    break;
                }
            }
            return new Result(list);
        } catch (Exception e) {
            logger.info("查询异常", e);
            return new Result(Constant.FAIL,"查询异常");
        }

    }

    private void queryNewsPage(String key, int limit, List<News> list,int page) throws IOException {
        //目标地址
        String url = "http://news.sogou.com/news";
        Document document = Jsoup.connect(url).data("query", key).data("page", page+"").get();

        Elements select = document.select("div.news151102");


        for (int i = 0; i < select.size()-1; i++) {
            News news = new News();
            Element element = select.get(i);
            Elements title= element.getElementsByClass("vrTitle");
            Elements detail = element.getElementsByClass("news-detail");

            Elements name = title.select("[href]");
            Elements span = detail.select("span");
           // String html = span.html();   带加红的标签
           // String text = span.text();  单独的纯文本
            Elements from = detail.select("[class=news-from]");
            Elements pic = detail.select("[src]");

            news.setTitle(name.text());
            news.setUrl(name.attr("href"));
            news.setContent(span.html());
            news.setPicUrl(pic.attr("src"));
            news.setSource(from.text());

            if (list.size()<limit){
                list.add(news);
            }else {
                break;
            }
            /*System.out.println("标题="+name.text());
            System.out.println("标题连接="+name.attr("href"));
            System.out.println("内容="+span.text());
            System.out.println("图片链接="+pic.attr("src"));
            System.out.println("来源="+from.text());*/
        }
    }

}
