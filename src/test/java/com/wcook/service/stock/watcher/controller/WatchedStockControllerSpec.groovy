package com.wcook.service.stock.watcher.controller

import com.wcook.service.stock.watcher.dto.AddWatchedStockDTO
import com.wcook.service.stock.watcher.dto.WatchedStockDTO
import com.wcook.service.stock.watcher.service.WatchedStockService
import spock.lang.Specification
import spock.lang.Subject

class WatchedStockControllerSpec extends Specification {

    @Subject
    WatchedStockController watchedStockController

    WatchedStockService watchedStockService

    def setup() {
        watchedStockService = Mock()

        watchedStockController = new WatchedStockController(watchedStockService)
    }

    def "listWatchedStocks should return list of watched stocks from service"() {
        given:
            def watchedStock = WatchedStockDTO.builder().build()
            def expectedWatchedStocks = List.of(watchedStock)
        when:
            def result = watchedStockController.listWatchedStocks()
        then:
            1 * watchedStockService.findAllWatchedStocks() >> expectedWatchedStocks;

            result == expectedWatchedStocks
    }

    def "addWatchedStock should call addWatchedStock on service"() {
        given:
            def addWatchedStockDTO = new AddWatchedStockDTO("AAPL")
        when:
            watchedStockController.addWatchedStock(addWatchedStockDTO)
        then:
            1 * watchedStockService.addWatchedStock(addWatchedStockDTO)
    }

    def "removeWatchedStock should call removeWatchedStock on service"() {
        given:
            def stockSymbol = "AAPL"
        when:
            watchedStockController.removeWatchedStock(stockSymbol)
        then:
            1 * watchedStockService.removeWatchedStock(stockSymbol)
    }

}
