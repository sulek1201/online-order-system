package com.sulek.order.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "daily_profit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DailyProfit {

    @Id
    @SequenceGenerator(name = "dailyprofit_seq", sequenceName = "S_DAILYPROFIT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailyprofit_seq")
    @Column(name = "id", columnDefinition = "numeric(19)")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "total_profit")
    private BigDecimal totalProfit;


}
