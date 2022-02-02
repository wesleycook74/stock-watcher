package com.wcook.service.stock.watcher.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "watched_stock")
public class WatchedStock {

    @Id
    @Column(name = "stock_symbol")

    private String stockSymbol;

    @CreationTimestamp
    @Column(name = "created_date")
    private OffsetDateTime createdDate;

}
