package org.beatengine.onlineshop.entity;

import jakarta.persistence.*;


import java.math.BigInteger;
import java.sql.Blob;

@Entity
public class Picture extends SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(columnDefinition = "BIGINT")
    private Long pictureId;

    //@Column(columnDefinition = "BIGINT")
    private Long articleId;

    @Lob
    private Blob jpegData;

    @ManyToOne
    @JoinColumn(name = "articleId", insertable = false, updatable = false)
    public Article article;

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Blob getJpegData() {
        return jpegData;
    }

    public void setJpegData(Blob jpegData) {
        this.jpegData = jpegData;
    }
}
