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

    @RequestMapping("queryWeiXinNews")
    private Result queryWeiXinNews(String key ,int limit){


        try {
           // List<News> list = getResult(key, limit);
            List<News> result = getResult(key, limit);
            return new Result(result);
        } catch (Exception e) {
            logger.info("查询异常", e);
            return new Result(Constant.FAIL,"查询异常");
        }

    }

    private List<News> getResult(String key, int limit) throws IOException {
        List<News> list = new ArrayList<>();
        int count=1;
        while (true){
            queryWeiXinNewsPage(key, limit, list,count);
            if (list.size()<limit){
                count++;
            }else{
                break;
            }
        }
        return list;
    }


    private void queryNewsPage(String key, int limit, List<News> list,int page) throws IOException {
        //目标地址
        String url = "http://news.sogou.com/news";
        Document document = Jsoup.connect(url).data("query", key).data("page", page+"").get();

        Elements select = document.select("div.news151102");
        Elements children = select.get(0).children();


        for (int i = 2; i < select.size()-1; i++) {
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


    private void queryWeiXinNewsPage(String key, int limit, List<News> list,int page) throws IOException {


      /*  System.setProperty("https.proxySet", "true");
        System.getProperties().setProperty("https.proxyHost", "59.61.38.116");
        System.getProperties().setProperty("https.proxyPort","25369");*/


        //目标地址
        String url = "https://weixin.sogou.com/weixin";

        String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/56.0.2924.87 Safari/537.36";


        Document document = Jsoup.connect(url).proxy("117.93.19.137", 44030).
                ignoreContentType(true).
        userAgent(userAgent).
                ignoreHttpErrors(true).
                data("query", key).data("type", "2").data("page", page+"").timeout(3000).get();

        Elements result = document.select(".news-list");
        Elements children = result.get(0).children();

        for (int i = 0; i < children.size()-1; i++) {
            if (list.size()>=limit){
                break;
            }

            News news = new News();
            Element element = children.get(i);
            String clsassName = element.attr("class");
            //sogou_vr_11002601_box_0
            if ("js-li".equals(clsassName)){
                //三图的
                List<String> urls = new ArrayList<>();
                Elements imgD = element.select(".img-d").get(0).select("[src]");
                for (int j = 0; j <imgD.size() ; j++) {
                    urls.add(imgD.get(j).attr("src"));
                }
              /*  //标题
                Elements textBox = element.select(".text-box");
                String title = textBox.select("h3").select("a").html();
                String titleUrl = textBox.select("h3").select("a").attr("href");
                //来源
                String source = element.select(".img-d").select("a").text();*/
                news.setPicUrlList(urls);
            }else{

                Elements img = element.select("[src]");
                String src = img.attr("src");
                news.setPicUrl(src);
            }

            //标题
            Elements textBox = element.select(".txt-box");
            String title = textBox.select("h3").select("a").html();
            String titleUrl = textBox.select("h3").select("a").attr("href");


            //内容
            String content = textBox.select("p").html();
            //来源
            String time = textBox.select(".s-p").attr("t");
            String source = textBox.select(".s-p").select("a").text();


            news.setTitle(title);
            news.setContent(content);
            news.setSource(source);
            news.setTime(time);
            news.setUrl(titleUrl);

            list.add(news);

            /*Elements title= element.getElementsByClass("vrTitle");
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
            }*/
            /*System.out.println("标题="+name.text());
            System.out.println("标题连接="+name.attr("href"));
            System.out.println("内容="+span.text());
            System.out.println("图片链接="+pic.attr("src"));
            System.out.println("来源="+from.text());*/
        }
    }





}
