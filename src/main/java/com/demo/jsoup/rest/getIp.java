package com.demo.jsoup.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.jsoup.common.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 提取可用ip
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月16日  17:07 by zyw9527
 */

public class getIp {


    private static Logger  logger = LoggerFactory.getLogger(getIp.class);

    public static void jjj(String[] args) {

        for (int i = 0; i < 100; i++) {
            String url="http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=e653a575189c4d9f83cacf3c684abbd6&count=1&expiryDate=0&format=1&newLine=2";
            String s = HttpUtils.postInterface(url, null);
            JSONObject object = JSON.parseObject(s);
            String msg = object.get("msg").toString();
            String value = JSON.parseArray(msg).get(0).toString();
            JSONObject jsonObject = JSON.parseObject(value);
            String ip = jsonObject.get("ip").toString();
            String port = jsonObject.get("port").toString();
            if (visit(ip,Integer.valueOf(port))){
                writeFile("F:/ip.text", ip+":"+port);
            }
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                logger.error("线程异常", e);
            }

        }

    }


    private static boolean  visit(String ip,int port){
        //目标地址
        String url = "https://weixin.sogou.com/weixin";

        String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/56.0.2924.87 Safari/537.36";
        try {
            Document document = Jsoup.connect(url).proxy(ip, port).
                    ignoreContentType(true).
                    userAgent(userAgent).
                    ignoreHttpErrors(true).
                    timeout(3000).get();
        }catch (IOException e){
            logger.error("访问网站IO异常", e);
            return false;
        }catch (Exception e){
            logger.error("访问网站数据异常", e);
            return false;
        }
        return true;
    }


    public static void writeFile(String fileFullPath,String content) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(fileFullPath, true);
            //写入
            fos.write(content.getBytes());
            // 写入一个换行
            fos.write("\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


}}
