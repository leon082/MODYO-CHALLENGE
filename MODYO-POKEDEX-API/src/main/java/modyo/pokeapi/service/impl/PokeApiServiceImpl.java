package modyo.pokeapi.service.impl;

import modyo.dto.Pokemon;
import modyo.dto.PokemonDetails;
import modyo.pokeapi.exception.PokeApiException;


import modyo.pokeapi.repository.IRepository;
import modyo.pokeapi.service.IPokeApiService;
import modyo.pokeapi.util.PokeApiConstants;
import modyo.pokeapi.util.PokeApiUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PokeApiServiceImpl implements IPokeApiService {

    @Autowired
    IRepository repository;


    @Override
    public List<Pokemon> pokemon(String offSet) throws Exception {
        List<Pokemon> pokemonList = new ArrayList<>();

        try {

            final ResponseEntity<String> response = repository.executeGET(PokeApiConstants.URL_POKEDEX + PokeApiConstants.POKEMON_PATH + offSet);

            JSONObject js = PokeApiUtil.getBodyFromResponse(response);
            if (js == null) {
                throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, response.getStatusCode().value());
            }

            JSONArray array = js.getJSONArray(PokeApiConstants.RESULTS);
            for (Object object : array) {

                JSONObject item = (JSONObject) object;
                final ResponseEntity<String> details = repository.executeGET(item.getString(PokeApiConstants.URL));

                js = PokeApiUtil.getBodyFromResponse(details);
                if (js == null) {

                    throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, response.getStatusCode().value());
                }
                Pokemon pokemon = PokeApiUtil.pokemonFromPokeApi(js);
                pokemon.setId(PokeApiUtil.getIdFromUrl(item.getString(PokeApiConstants.URL)));
                pokemonList.add(pokemon);

            }

            return pokemonList;

        } catch (HttpClientErrorException e) {

            throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, e.getStatusCode().value());
        } catch (Exception e) {

            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e);

        }

    }

    @Override
    public PokemonDetails pokemonDetails(String id) throws Exception {

        try {
            final ResponseEntity<String> details = repository.executeGET(PokeApiConstants.POKEMON_DETAIL_PATH + id);

            JSONObject js = PokeApiUtil.getBodyFromResponse(details);
            if (js == null) {

                throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, details.getStatusCode().value());
            }
            Pokemon pokemon = PokeApiUtil.pokemonFromPokeApi(js);
            pokemon.setId(PokeApiUtil.getIdFromUrl(id));

            final ResponseEntity<String> species = repository.executeGET(js.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.URL));
            js = PokeApiUtil.getBodyFromResponse(species);
            if (js == null) {

                throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, species.getStatusCode().value());
            }
            PokemonDetails pokemonDetails = PokemonDetails.builder().pokemon(pokemon).
                    description(js.getJSONArray(PokeApiConstants.FLAVOR_TEXT_ENTRIES).getJSONObject(0).getString(PokeApiConstants.FLAVOR_TEXT)).build();

            final ResponseEntity<String> evolutions = repository.executeGET(js.getJSONObject(PokeApiConstants.EVOLUTION_CHAIN).getString(PokeApiConstants.URL));
            js = PokeApiUtil.getBodyFromResponse(evolutions);
            if (js == null) {

                throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, evolutions.getStatusCode().value());
            }

            Map<String, String> evolves = PokeApiUtil.getEvolves(js);
            List<Pokemon> pokemonEvolves = new ArrayList<>();
            evolves.forEach((k,v) -> {
                final ResponseEntity<String> item = repository.executeGET(PokeApiConstants.POKEMON_DETAIL_PATH + v);
                JSONObject jsItem = PokeApiUtil.getBodyFromResponse(item);
                Pokemon pokemonItem = PokeApiUtil.pokemonFromPokeApi(jsItem);
                pokemonItem.setId(PokeApiUtil.getIdFromUrl(v));
                pokemonEvolves.add(pokemonItem);

            });

            pokemonDetails.setEvolutions(pokemonEvolves);
            return pokemonDetails;
        } catch (HttpClientErrorException e) {

            throw new PokeApiException(PokeApiConstants.ERROR_SERVICE, e.getStatusCode().value());
        } catch (Exception e) {

            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e);
        }

    }





}
