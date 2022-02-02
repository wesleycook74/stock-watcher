package com.wcook.service.stock.watcher.service;

import com.wcook.service.stock.watcher.dto.AddWatchedStockDTO;
import com.wcook.service.stock.watcher.dto.WatchedStockDTO;
import com.wcook.service.stock.watcher.entity.WatchedStock;
import com.wcook.service.stock.watcher.exception.StockAlreadyWatchedException;
import com.wcook.service.stock.watcher.exception.WatchedStockNotFoundException;
import com.wcook.service.stock.watcher.repository.WatchedStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchedStockService {

    private final WatchedStockRepository watchedStockRepository;
    private final StockPriceService stockPriceService;

    public List<WatchedStockDTO> findAllWatchedStocks() {
        log.info("Returning list of all watched stocks");

        var watchedStocks = watchedStockRepository.findAll();

        var pricingInfo = stockPriceService.retrieveStockPriceInfo(watchedStocks.stream()
                .map(WatchedStock::getStockSymbol)
                .collect(Collectors.toList()));

        return watchedStocks.stream()
                .map(watchedStock -> WatchedStockDTO.builder()
                        .stockSymbol(watchedStock.getStockSymbol())
                        .stockPriceInfo(pricingInfo.get(watchedStock.getStockSymbol()))
                        .build())
                .collect(Collectors.toList());
    }

    public void addWatchedStock(AddWatchedStockDTO addWatchedStockDTO) {
        String stockSymbol = addWatchedStockDTO.stockSymbol();
        log.info("Adding stock to watch for symbol={}", stockSymbol);

        if (watchedStockRepository.existsById(stockSymbol)) {
            throw new StockAlreadyWatchedException(stockSymbol);
        }

        var stockToWatch = WatchedStock.builder()
                .stockSymbol(stockSymbol)
                .build();

        var watchedStock = watchedStockRepository.save(stockToWatch);

        log.debug("Started watching stock: {}", watchedStock);
    }

    public void removeWatchedStock(String stockSymbol) {
        log.info("Removing watched stock for symbol={}", stockSymbol);

        if (!watchedStockRepository.existsById(stockSymbol)) {
            throw new WatchedStockNotFoundException(stockSymbol);
        }

        watchedStockRepository.deleteById(stockSymbol);
    }

}
