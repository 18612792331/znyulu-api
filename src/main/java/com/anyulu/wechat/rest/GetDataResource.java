package com.anyulu.wechat.rest;

import com.anyulu.wechat.domain.DataYulu;
import com.anyulu.wechat.utils.HttpsUtil;
import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("api")
public class GetDataResource {

    private final Logger log = LoggerFactory.getLogger(GetDataResource.class);

    @Autowired
    private MongoTemplate mongo;


    @PostMapping("/data")
    public String getLove() {
        HttpsUtil.trustEveryone();
        String url = "https://api.lovelive.tools/api/SweetNothings/1500/Serialization/Json";
        HttpRequest request = HttpRequest.get(url);
        String body = request.body();
        if (StringUtils.isNoneBlank("body")) {
            JSONObject jsonObject = JSONObject.fromObject(body);
            int code = jsonObject.getInt("code");
            try {
                if (code==200) {
                    JSONArray array = jsonObject.getJSONArray("returnObj");
                    Iterator iterator = array.iterator();
                    int i = 0;
                    while (iterator.hasNext()) {
                        i++;
                        Object data = iterator.next();
                        DataYulu yulu = new DataYulu();
                        log.info("输出: {}", data.toString());
                        yulu.setText(data.toString());
                        yulu.setCopyCount(0);
                        yulu.setCaiCount(0);
                        yulu.setZanCount(0);
                        yulu.setNo(i);
                        mongo.save(yulu);
                    }
                    /*array.forEach(data -> {
                        if (StringUtils.isNoneBlank(data.toString())) {
                            DataYulu yulu = new DataYulu();
                            log.info("输出: {}", data.toString());
                            yulu.setText(data.toString());
                            yulu.setCopyCount(0);
                            yulu.setCaiCount(0);
                            yulu.setZanCount(0);
                            mongo.save(yulu);
                        }
                    });*/
                    System.out.println(array.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return "success";
    }
}
