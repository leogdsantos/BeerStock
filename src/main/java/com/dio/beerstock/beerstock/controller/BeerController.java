package com.dio.beerstock.beerstock.controller;

import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.dto.QuantityDTO;
import com.dio.beerstock.beerstock.expection.BeerAlreadyRegisteredExpection;
import com.dio.beerstock.beerstock.expection.BeerNotFoundExpection;
import com.dio.beerstock.beerstock.expection.BeerStockExceededException;
import com.dio.beerstock.beerstock.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class BeerController {

    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@PathVariable @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredExpection {
        return beerService.createBear(beerDTO);
    }

    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundExpection {
        return beerService.findByName(name);
    }

   @GetMapping
    public List<BeerDTO> listBeers(){
        return beerService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(Long id) throws BeerNotFoundExpection {
        beerService.deleteById(id);
    }


    public BeerDTO increment(Long id, QuantityDTO quantityDTO) throws BeerNotFoundExpection, BeerStockExceededException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

}
