package org.beatengine.onlineshop.entity;

import jakarta.persistence.*;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Order extends SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(columnDefinition = "BIGINT")
    private Long orderId;
    //@Column(columnDefinition = "BIGINT")
    private Long userId; //who orders
    @Column
    private Timestamp dateTimeOfOrder;

    @Column
    private float calculatedSum; // will be set on order to include all temporary discounts and texes.

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "ArticleOrder",
            joinColumns = { @JoinColumn(name = "orderId") },
            inverseJoinColumns = { @JoinColumn(name = "articleId") })
    public Set<Article> articles;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    public User orderer;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getDateTimeOfOrder() {
        return dateTimeOfOrder;
    }

    public void setDateTimeOfOrder(Timestamp dateTimeOfOrder) {
        this.dateTimeOfOrder = dateTimeOfOrder;
    }

    public float getCalculatedSum() {
        return calculatedSum;
    }

    public void setCalculatedSum(float calculatedSum) {
        this.calculatedSum = calculatedSum;
    }
}
