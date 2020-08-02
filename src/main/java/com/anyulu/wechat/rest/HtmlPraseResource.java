package com.anyulu.wechat.rest;


import com.anyulu.wechat.utils.HttpsUtil;
import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("api")
public class HtmlPraseResource {

    private final Logger log = LoggerFactory.getLogger(HtmlPraseResource.class);

    public static void main(String[] args) {
        String url = "https://api.lovelive.tools/api/SweetNothings/2/Serialization/Json";
        HttpRequest request = HttpRequest.get(url);
        String body = request.body();
        System.out.println(body);
    }

    @PostMapping("/start")
    public String parseHtml() {
        log.info("开始爬取数据");
        HttpsUtil.trustEveryone();
        Document html =null;
        String url = "https://www.1juzi.com/new/32060.html";
        try {
            html = Jsoup.parse(new URL(url), 10000);
            Elements content = html.getElementsByClass("content");
            Elements ps = content.select("p");
            System.out.println(ps.size());


            ps.forEach(element -> {
                log.info("输出: {}", element.text().replaceFirst("\\d+、", "").trim());

            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "success";
    }
}
