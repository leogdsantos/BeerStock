package com.dio.beerstock.beerstock.mapper;

import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer ToModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);

}
