package com.wcook.service.stock.watcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StockAlreadyWatchedException extends RuntimeException {

    private static final String message = "Stock Already Watched with symbol=%s";

    public StockAlreadyWatchedException(String stockSymbol) {
        super(String.format(message, stockSymbol));
    }

}
