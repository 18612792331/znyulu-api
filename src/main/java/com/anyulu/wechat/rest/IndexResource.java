package com.anyulu.wechat.rest;

import com.anyulu.wechat.domain.DataYulu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class IndexResource {

    private final Logger log = LoggerFactory.getLogger(IndexResource.class);

    @Autowired
    private MongoTemplate mongo;

    @GetMapping("/success")
    public String success() {
        return "success";
    }

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

    @GetMapping("/rank")
    public Page<DataYulu> getRank(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        log.info("获取语录排行榜");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Query query = new Query();
        query.with(Sort.by(
                Sort.Order.desc("zanCount")
        ));
        long total = mongo.count(query, DataYulu.class);
        List<DataYulu> list = mongo.find(query.with(pageRequest), DataYulu.class);
        Page<DataYulu> page = new PageImpl(list, pageRequest, total);

        return page;
    }

    @GetMapping("/search/{keyword}")
    public List<DataYulu> search(@PathVariable String keyword) {
        log.info("根据关键词搜索 :{}",keyword);
        Query query = new Query();
        Pattern pattern=Pattern.compile("^.*"+keyword+".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("text").regex(pattern));
        List<DataYulu> dataYulus = mongo.find(query, DataYulu.class);
        return dataYulus;
    }




}
