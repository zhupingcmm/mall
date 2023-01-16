package com.mf.payment.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "tb_payment")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Integer count;

    @Column(name = "unit_price")
    private Double unitPrice;

    private Double amount;

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}
