package modyo.service;

import modyo.model.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceImpl {

    @Autowired
    IService service;

    @Test
    public void validatePokemonsService(){
        Response pokemons = service.pokemons(BigDecimal.ONE.toString());
        Assert.assertTrue(pokemons.getStatusCode().intValue() == HttpStatus.OK.value());
    }

    @Test
    public void validatePokemonDetailService(){
        Response pokemonDetail = service.pokemonDetails(BigDecimal.ONE.toString());
        Assert.assertTrue(pokemonDetail.getStatusCode().intValue() == HttpStatus.OK.value());
    }

}
