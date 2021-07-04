package com.dio.beerstock.beerstock.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededException extends Exception {
    public BeerStockExceededException(Long id, Integer quantity) {
        super(String.format("Beers with %s ID to increment informed exceeds the max Stock capacity:%s.", id, quantity));
    }
}
