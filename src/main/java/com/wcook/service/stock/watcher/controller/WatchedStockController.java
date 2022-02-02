package com.wcook.service.stock.watcher.controller;

import com.wcook.service.stock.watcher.dto.AddWatchedStockDTO;
import com.wcook.service.stock.watcher.dto.WatchedStockDTO;
import com.wcook.service.stock.watcher.service.WatchedStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/watched-stock")
@RestController
public class WatchedStockController {

    private final WatchedStockService watchedStockService;

    @GetMapping
    public List<WatchedStockDTO> listWatchedStocks() {
        return watchedStockService.findAllWatchedStocks();
    }

    @PostMapping
    public void addWatchedStock(@RequestBody AddWatchedStockDTO addWatchedStockDTO) {
        watchedStockService.addWatchedStock(addWatchedStockDTO);
    }

    @DeleteMapping("/{stockSymbol}")
    public void removeWatchedStock(@PathVariable String stockSymbol) {
        watchedStockService.removeWatchedStock(stockSymbol);
    }

}
