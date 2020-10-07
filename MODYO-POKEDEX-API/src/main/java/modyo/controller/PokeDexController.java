package modyo.controller;

import modyo.model.Response;
import modyo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PokeDexController {

    @Autowired
    IService pokemonService;

    @GetMapping("/pokemons/{offSet}")
    public Response pokemons(@PathVariable("offSet") String offSet, HttpServletResponse response){
        final Response pokemons = pokemonService.pokemons(offSet);
            response.setStatus(pokemons.getStatusCode());
            return pokemons;
    }

    @GetMapping("/pokemonDetails/{id}")
    public Response pokemonDetails(@PathVariable("id") String id,HttpServletResponse response){

        final Response pokemonDetails =  pokemonService.pokemonDetails(id);
        response.setStatus(pokemonDetails.getStatusCode());
        return pokemonDetails;
    }



}
