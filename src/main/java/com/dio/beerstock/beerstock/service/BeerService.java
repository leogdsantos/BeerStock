package com.dio.beerstock.beerstock.service;

import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.entity.Beer;
import com.dio.beerstock.beerstock.expection.BeerAlreadyRegisteredExpection;
import com.dio.beerstock.beerstock.expection.BeerNotFoundExpection;
import com.dio.beerstock.beerstock.expection.BeerStockExceededException;
import com.dio.beerstock.beerstock.mapper.BeerMapper;
import com.dio.beerstock.beerstock.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerDTO createBear(BeerDTO beerDTO) throws BeerAlreadyRegisteredExpection {

        verifyIfIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.ToModel(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDTO(savedBeer);
    }
    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredExpection {

        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
        if(optSavedBeer.isPresent()){
            throw new BeerAlreadyRegisteredExpection(name);
        }
    }

    public BeerDTO findByName(String name) throws BeerNotFoundExpection {
        Beer foundBeer;
        foundBeer = beerRepository.findByName(name)
                .orElseThrow(()-> new BeerNotFoundExpection(name));
        return beerMapper.toDTO(foundBeer);
    }

    public List<BeerDTO> listAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws BeerNotFoundExpection {
        verifyIfExists(id);
        beerRepository.deleteById(id);

    }
    private Beer verifyIfExists(Long id) throws BeerNotFoundExpection {
        return beerRepository.findById(id)
                .orElseThrow(()-> new BeerNotFoundExpection(id));
    }

    public BeerDTO increment(Long id, Integer quantity) throws BeerNotFoundExpection, BeerStockExceededException {
        Beer beerToIncrement = verifyIfExists(id);
        int quantityAfterIncrement = quantity + beerToIncrement.getQuantity();
        if(quantityAfterIncrement <= beerToIncrement.getMax()){
            beerToIncrement.setQuantity(beerToIncrement.getQuantity()+ quantity);
            Beer incrementedBeerStock = beerRepository.save(beerToIncrement);
            return beerMapper.toDTO(incrementedBeerStock);
        }
        throw new BeerStockExceededException(id, quantity);

    }
}
