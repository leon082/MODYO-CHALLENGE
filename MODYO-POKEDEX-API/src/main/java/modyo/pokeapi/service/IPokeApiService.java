package modyo.pokeapi.service;

import modyo.dto.Pokemon;
import modyo.dto.PokemonDetails;
import modyo.model.Response;
import modyo.pokeapi.exception.PokeApiException;

import java.util.List;

public interface IPokeApiService {
    public List<Pokemon> pokemon(String offSet) throws PokeApiException, Exception;

    public PokemonDetails pokemonDetails(String id) throws PokeApiException,Exception ;

}
