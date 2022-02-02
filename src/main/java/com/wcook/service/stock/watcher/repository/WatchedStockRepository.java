package com.wcook.service.stock.watcher.repository;

import com.wcook.service.stock.watcher.entity.WatchedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedStockRepository extends JpaRepository<WatchedStock, String> {
}
