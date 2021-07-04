package com.dio.beerstock.beerstock.controller;

import com.dio.beerstock.beerstock.builder.BeerDTOBuilder;
import com.dio.beerstock.beerstock.dto.BeerDTO;
import com.dio.beerstock.beerstock.dto.QuantityDTO;
import com.dio.beerstock.beerstock.expection.BeerNotFoundExpection;
import com.dio.beerstock.beerstock.service.BeerService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerTestController {

    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final long VALID_BEER_ID = 1L;
    private static final long INVALID_BEER_ID = 2l;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(((s, locale) -> new MappingJackson2JsonView()))
                .build();
    }
    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.createBear(beerDTO)).thenReturn(beerDTO);

    }
    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        beerDTO.setBrand(null);

    }
    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.findByName(beerDTO.getName()).thenReturn(beerDTO));

    }
    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned()throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.findByName(beerDTO.getName()).thenReturn(beerDTO));
    }
    @Test
    void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

    }
    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception{

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        doNothing().when(beerService).deleteById(beerDTO.getId());
    }
    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception{

        doThrow(BeerNotFoundExpection.class).when(beerService).deleteById(INVALID_BEER_ID);

    }
    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception{

        QuantityDTO quantityDTO = QuantityDTO.builder()
                .quantity(10)
                .build();
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());
    }



}
