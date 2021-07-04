package com.dio.beerstock.beerstock.expection;

public class BeerNotFoundExpection extends Exception {
    public BeerNotFoundExpection(String beerName) {
        super(String.format("Beer with name %s not found in the system.", beerName));
    }
    public BeerNotFoundExpection(Long beerId) {
        super(String.format("Beer with id %s not found in the system.", beerId));
    }
}
