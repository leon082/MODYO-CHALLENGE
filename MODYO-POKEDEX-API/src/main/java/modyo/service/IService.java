package modyo.service;

import modyo.model.Response;

public interface IService {
    public Response pokemons(String offSet);
    public Response pokemonDetails(String id) ;
}
