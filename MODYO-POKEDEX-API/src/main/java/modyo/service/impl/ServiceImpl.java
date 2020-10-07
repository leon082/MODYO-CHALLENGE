package modyo.service.impl;

import modyo.dto.Pokemon;
import modyo.model.Response;
import modyo.pokeapi.exception.PokeApiException;
import modyo.pokeapi.service.IPokeApiService;
import modyo.service.IService;
import modyo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements IService {

    @Autowired
    IPokeApiService pokeapiServices;

    @Override
    public Response pokemons(String offSet) {

        try {
            return Response.builder().data(pokeapiServices.pokemon(offSet)).statusCode(HttpStatus.OK.value()).build();
        }catch (PokeApiException e) {
           return Util.buildErrorResponse(e.getStatusCode(),e.getMessage());
        } catch (Exception e) {
            return Util.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }

    @Override
    public Response pokemonDetails(String id) {
        try {
            return Response.builder().data(pokeapiServices.pokemonDetails(id)).statusCode(HttpStatus.OK.value()).build();
        }catch (PokeApiException e) {
            return Util.buildErrorResponse(e.getStatusCode(),e.getMessage());
        } catch (Exception e) {
            return Util.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }
}
