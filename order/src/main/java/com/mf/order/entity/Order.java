package com.mf.order.entity;

import com.mf.common.base.BaseBean;
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
@Table(name = "tb_order")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Order extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Integer count;

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;


}
