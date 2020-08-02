package com.anyulu.wechat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "data_yulu")
public class DataYulu {

    @Id
    private String id;

    private String text;

    @Field("copy_count")
    private Integer copyCount;

    @Field("zan_count")
    private Integer zanCount;

    @Field("cai_count")
    private Integer caiCount;

    private Integer no;

    public DataYulu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(Integer copyCount) {
        this.copyCount = copyCount;
    }

    public Integer getZanCount() {
        return zanCount;
    }

    public void setZanCount(Integer zanCount) {
        this.zanCount = zanCount;
    }

    public Integer getCaiCount() {
        return caiCount;
    }

    public void setCaiCount(Integer caiCount) {
        this.caiCount = caiCount;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "DataYulu{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", copyCount=" + copyCount +
                ", zanCount=" + zanCount +
                ", caiCount=" + caiCount +
                '}';
    }
}
