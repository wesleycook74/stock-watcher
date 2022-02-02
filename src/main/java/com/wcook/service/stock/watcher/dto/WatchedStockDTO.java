package com.wcook.service.stock.watcher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchedStockDTO {
    private String stockSymbol;
    private StockPriceInfoDTO stockPriceInfo;
}
