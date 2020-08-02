package com.anyulu.wechat.rest;

import com.anyulu.wechat.domain.DataYulu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class IndexResource {

    private final Logger log = LoggerFactory.getLogger(IndexResource.class);

    @Autowired
    private MongoTemplate mongo;

    @GetMapping("/SweetNothings")
    public DataYulu getRandowData() {
        log.info("随机获取一条数据");
        long count = mongo.count(new Query(), DataYulu.class);
        DataYulu result = mongo.findOne(new Query(Criteria.where("no").is(new Random().nextInt((int) count))), DataYulu.class);
        return result;

    }

    @PostMapping("/zan/{id}")
    public Integer zan(@PathVariable String id) {
        log.info("赞了一条语录 id: {}", id);
        DataYulu yulu = mongo.findById(id, DataYulu.class);
        yulu.setZanCount(yulu.getZanCount()+1);
        mongo.save(yulu);
        return yulu.getZanCount();
    }
    @PostMapping("/cai/{id}")
    public Integer cai(@PathVariable String id) {
        log.info("踩了一条语录 id: {}", id);
        DataYulu yulu = mongo.findById(id, DataYulu.class);
        yulu.setCaiCount(yulu.getCaiCount()+1);
        mongo.save(yulu);
        return yulu.getCaiCount();
    }




}
