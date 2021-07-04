package com.dio.beerstock.beerstock.builder;

import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.enums.BeerType;
import lombok.Builder;

@Builder

public class BeerDTOBuilder {

    @Builder.Default
    private Long id = 1l;

    @Builder.Default
    private String name = "Budweiser";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private int max = 35;

    @Builder.Default
    private int quantity = 5;

    @Builder.Default
    private BeerType type = BeerType.LAGER;

    public BeerDTO toBeerDTO (){
        return new BeerDTO(id, name, brand, max, quantity, type);
    }
}
