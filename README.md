# Stock Watcher Service
Retrieves real time stock prices for watched stocks using the Yahoo Finance API 
and an H2 database to store the watched stocks in memory.

## Pre-requisites
* Java 17
* Maven

##  Running

Start service with: `mvn spring-boot:run`

The service runs on port 8080.

## Endpoints

| Request                                   | Description                                                                             |
|-------------------------------------------|-----------------------------------------------------------------------------------------|
| `GET /api/watched-stock`                  | Returns the list of currently watched stocks with pricing info                          |
| `POST /api/watched-stock`                 | Add a new stock to be watched by the symbol. Ex. request body: `{"stockSymbol":"AAPL"}` |
| `DELETE /api/watched-stock/{stockSymbol}` | Removes a stock from the list of currently watched stocks                               |

