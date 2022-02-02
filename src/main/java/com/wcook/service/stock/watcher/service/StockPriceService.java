package com.wcook.service.stock.watcher.service;

import com.wcook.service.stock.watcher.dto.StockPriceInfoDTO;

import java.util.List;
import java.util.Map;

public interface StockPriceService {

    Map<String, StockPriceInfoDTO> retrieveStockPriceInfo(List<String> stockSymbols);

}
