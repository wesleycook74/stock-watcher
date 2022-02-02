package com.wcook.service.stock.watcher.service

import spock.lang.Specification
import spock.lang.Subject

class YahooFinanceStockPriceServiceSpec extends Specification {

    @Subject
    YahooFinanceStockPriceService yahooFinanceStockPriceService

    def setup() {
        yahooFinanceStockPriceService = new YahooFinanceStockPriceService()
    }

    def "retrieveStockPriceInfo should return price info for stock symbols"() {
        given:
            def stockSymbols = List.of("AAPL", "MSFT")
        when:
            def result = yahooFinanceStockPriceService.retrieveStockPriceInfo(stockSymbols)
        then:
            result.get("AAPL") != null
            result.get("MSFT") != null
    }

}
