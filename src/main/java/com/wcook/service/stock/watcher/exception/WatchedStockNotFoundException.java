package com.wcook.service.stock.watcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WatchedStockNotFoundException extends RuntimeException {

    private static final String message = "Watched Stock not found for symbol=%s";

    public WatchedStockNotFoundException(String stockSymbol) {
        super(String.format(message, stockSymbol));
    }

}
