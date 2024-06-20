package org.beatengine.onlineshop.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import java.math.BigInteger;
import java.util.Set;

@Entity
public class Article extends SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(columnDefinition = "BIGINT")
    private Long articleId;
    @Column
    private String name;
    @Column
    private String manufacturer;
    @Column
    private long stockAmount;
    @Column
    private float price;
    @Column
    @ColumnDefault("1.0")
    private float discountFactor;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "ArticleOrder",
            joinColumns = { @JoinColumn(name = "articleId") },
            inverseJoinColumns = { @JoinColumn(name = "orderId") })
    public Set<Order> orders;

    @OneToMany
    @JoinColumn(name = "articleId")
    public Set<Rating> ratings;

    @OneToMany
    @JoinColumn(name = "articleId")
    public Set<Picture> pictures;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public long getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(long stockAmount) {
        this.stockAmount = stockAmount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(float discountFactor) {
        this.discountFactor = discountFactor;
    }
}
