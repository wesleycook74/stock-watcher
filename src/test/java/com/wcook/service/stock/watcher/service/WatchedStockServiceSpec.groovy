package com.wcook.service.stock.watcher.service

import com.wcook.service.stock.watcher.dto.AddWatchedStockDTO
import com.wcook.service.stock.watcher.dto.StockPriceInfoDTO
import com.wcook.service.stock.watcher.entity.WatchedStock
import com.wcook.service.stock.watcher.exception.StockAlreadyWatchedException
import com.wcook.service.stock.watcher.exception.WatchedStockNotFoundException
import com.wcook.service.stock.watcher.repository.WatchedStockRepository
import spock.lang.Specification
import spock.lang.Subject

class WatchedStockServiceSpec extends Specification {

    @Subject
    WatchedStockService watchedStockService

    WatchedStockRepository watchedStockRepository
    StockPriceService stockPriceService

    def setup() {
        watchedStockRepository = Mock()
        stockPriceService = Mock()

        watchedStockService = new WatchedStockService(watchedStockRepository, stockPriceService)
    }

    def "findAllWatchedStocks should return list of watched stocks with pricing info from stock pricing service"() {
        given:
            def stockSymbol = "AAPL"
            def watchedStock = WatchedStock.builder().stockSymbol(stockSymbol).build()

            def pricingInfo = StockPriceInfoDTO.builder()
                    .build()
        when:
            def result = watchedStockService.findAllWatchedStocks()
        then:
            1 * watchedStockRepository.findAll() >> List.of(watchedStock)
            1 * stockPriceService.retrieveStockPriceInfo(List.of(stockSymbol)) >> Map.of(stockSymbol, pricingInfo)

            result.size() == 1
            result.get(0).getStockSymbol() == stockSymbol
            result.get(0).getStockPriceInfo() == pricingInfo
    }

    def "addWatchedStock should save a new watched stock if it is not already being watched"() {
        given:
            def stockSymbol = "AAPL"
            def addWatchedStockDTO = new AddWatchedStockDTO(stockSymbol)
        when:
            watchedStockService.addWatchedStock(addWatchedStockDTO)
        then:
            1 * watchedStockRepository.existsById(stockSymbol) >> false
            1 * watchedStockRepository.save(_ as WatchedStock) >> {
                def saved = (WatchedStock) it[0]
                assert saved.getStockSymbol() == stockSymbol
            }
    }

    def "addWatchedStock should throw StockAlreadyWatchedException if it is already being watched"() {
        given:
            def stockSymbol = "AAPL"
            def addWatchedStockDTO = new AddWatchedStockDTO(stockSymbol)
        when:
            watchedStockService.addWatchedStock(addWatchedStockDTO)
        then:
            1 * watchedStockRepository.existsById(stockSymbol) >> true
            0 * watchedStockRepository.save(_)

            thrown(StockAlreadyWatchedException)
    }

    def "removeWatchedStock should delete watched stock if it is being watched"() {
        given:
            def stockSymbol = "AAPL"
        when:
            watchedStockService.removeWatchedStock(stockSymbol)
        then:
            1 * watchedStockRepository.existsById(stockSymbol) >> true
            1 * watchedStockRepository.deleteById(stockSymbol)
    }

    def "removeWatchedStock should throw WatchedStockNotFoundException if stock is not being watched"() {
        given:
            def stockSymbol = "AAPL"
        when:
            watchedStockService.removeWatchedStock(stockSymbol)
        then:
            1 * watchedStockRepository.existsById(stockSymbol) >> false
            0 * watchedStockRepository.deleteById(stockSymbol)

            thrown(WatchedStockNotFoundException)
    }

}
