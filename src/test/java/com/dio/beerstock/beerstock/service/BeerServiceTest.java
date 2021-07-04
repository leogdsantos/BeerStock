package com.dio.beerstock.beerstock.service;

import com.dio.beerstock.beerstock.builder.BeerDTOBuilder;
import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.entity.Beer;
import com.dio.beerstock.beerstock.expection.BeerAlreadyRegisteredExpection;
import com.dio.beerstock.beerstock.expection.BeerNotFoundExpection;
import com.dio.beerstock.beerstock.expection.BeerStockExceededException;
import com.dio.beerstock.beerstock.mapper.BeerMapper;
import com.dio.beerstock.beerstock.repository.BeerRepository;
import net.bytebuddy.matcher.ElementMatcher;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredExpection{

        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedSavedBeer = beerMapper.ToModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        BeerDTO createdBeerDTO = beerService.createBear(expectedBeerDTO);

    }

    @Test
    void  whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown(){

        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer duplicatedBeer = beerMapper.ToModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

    }
    @Test
    void  whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundExpection {

        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.ToModel(expectedFoundBeerDTO);

        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

    }
    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException(){
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers(){

        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.ToModel(expectedFoundBeerDTO);

        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        List<BeerDTO> foundListBeersDTO = beerService.listAll();

    }
    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers(){

        when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<BeerDTO> foundListBeersDTO = beerService.listAll();

        assertThrows(foundListBeersDTO, is(empty()));


    }

    private void assertThrows(List<BeerDTO> foundListBeersDTO, ElementMatcher.Junction<Object> objectJunction) {
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundExpection{

        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedDeleteBeer = beerMapper.ToModel(expectedDeletedBeerDTO);

        when(beerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.of(expectedDeleteBeer));
        doNothing().when(beerRepository).deleteById(expectedDeletedBeerDTO.getId());

        beerService.deleteById(expectedDeletedBeerDTO.getId());

        verify(beerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());
        verify(beerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
    }
    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundExpection, BeerStockExceededException{

        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.ToModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

        BeerDTO incrementBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

    }
    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException(){

        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.ToModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 80;

    }
    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException(){

        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.ToModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 45;

    }
    @Test
    void  whenIncrementIsCalledWithInvalidIdThenThrowException(){

        int quantityToIncrement = 10;

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

    }

}
