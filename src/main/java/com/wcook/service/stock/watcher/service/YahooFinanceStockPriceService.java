package com.wcook.service.stock.watcher.service;

import com.wcook.service.stock.watcher.dto.StockPriceInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class YahooFinanceStockPriceService implements StockPriceService {


    @Override
    public Map<String, StockPriceInfoDTO> retrieveStockPriceInfo(List<String> stockSymbols) {
        if (stockSymbols == null || stockSymbols.isEmpty()) {
            return Map.of();
        }

        try {
            log.info("Retrieving stock price info from Yahoo Finance for stocks symbols {}", stockSymbols);
            var yahooPriceQuotes = YahooFinance.get(stockSymbols.toArray(new String[stockSymbols.size()]));

            return yahooPriceQuotes.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> convertToStockPriceInfoDTO(entry.getValue())));
        } catch (IOException e) {
            log.error("Error retrieving stock prices from Yahoo: ", e);
        }

        return Map.of();
    }

    private static StockPriceInfoDTO convertToStockPriceInfoDTO(Stock stock) {
        var quote = stock.getQuote();
        return StockPriceInfoDTO.builder()
                .currencySymbol(stock.getCurrency())
                .currentPrice(quote.getPrice())
                .fiftyDayAverage(quote.getPriceAvg50())
                .twoHundredDayAverage(quote.getPriceAvg200())
                .build();
    }
}
