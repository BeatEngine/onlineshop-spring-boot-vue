package org.beatengine.onlineshop.entity;

import jakarta.persistence.*;


import java.math.BigInteger;

@Entity
public class Rating extends SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(columnDefinition = "BIGINT")
    private Long ratingId;
    //@Column(columnDefinition = "BIGINT")
    private Long articleId;
    //@Column(columnDefinition = "BIGINT")
    private Long userId;
    @Column(columnDefinition = "text")
    private String ratingText;
    // 1 to 5 stars
    private int ratingStars;


    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    public User rater;

    @ManyToOne
    @JoinColumn(name = "articleId", insertable = false, updatable = false)
    public Article article;


    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public int getRatingStars() {
        return ratingStars;
    }

    public void setRatingStars(int ratingStars) {
        this.ratingStars = ratingStars;
    }
}
