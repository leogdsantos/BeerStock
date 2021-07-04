package com.dio.beerstock.beerstock.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyRegisteredExpection extends Exception {
    public BeerAlreadyRegisteredExpection(String BeerName) {
        super(String.format("Beer with name %s already registered in the system.", BeerName));
    }
}
