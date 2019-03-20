package com.demo.jsoup.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 接口请求
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月15日  14:21 by zyw9527
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post调用第三方接口
     *
     * @param url
     * @param param
     * @return
     */
    public static String postInterface(String url, String param) {
        HttpPost post;
        String rst;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            post = new HttpPost(url);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");
            String charSet = "UTF-8";
            if (StringUtils.isNotBlank(param)){
                StringEntity entity = new StringEntity(param, charSet);
                post.setEntity(entity);
            }

           // StringEntity entity = new StringEntity(JSON.toJSONString(map), charSet);
            HttpResponse response = httpClient.execute(post);
            // 检验返回码
            rst = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
           // JSONObject dataJson = JSON.parseObject(rst);
            return rst;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
