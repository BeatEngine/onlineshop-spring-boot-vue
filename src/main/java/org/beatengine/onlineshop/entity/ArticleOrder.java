package org.beatengine.onlineshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.math.BigInteger;


public class ArticleOrder extends SuperEntity{
    @Id
    //@Column(columnDefinition = "BIGINT")
    private Long articleId;
    @Id
    //@Column(columnDefinition = "BIGINT")
    private Long orderId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
