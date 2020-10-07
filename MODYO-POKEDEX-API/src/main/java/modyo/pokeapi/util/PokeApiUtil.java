package modyo.pokeapi.util;

import modyo.dto.Pokemon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;


public class PokeApiUtil {

    public static List<List<String>> getEvolves(JSONObject jsonObject) {

        List<List<String>> evolutions = new ArrayList<>();

        jsonObject = jsonObject.getJSONObject(PokeApiConstants.CHAIN);

        JSONArray evolves_to = jsonObject.getJSONArray(PokeApiConstants.EVOLVES_TO);
        while (!evolves_to.isEmpty()) {
            List<String> evolution = new ArrayList<>();
            for (Object object : evolves_to) {
                JSONObject item = (JSONObject) object;
                evolution.add(item.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.NAME));

            }
            evolutions.add(evolution);
            evolves_to = evolves_to.getJSONObject(0).getJSONArray(PokeApiConstants.EVOLVES_TO);
        }
        return evolutions;

    }

    public static String getIdFromUrl(String url) {
        String[] split = url.split("\\/");
        return split[split.length - 1];
    }

    public static Pokemon pokemonFromPokeApi(JSONObject js) {
        List<String> abilities = new ArrayList<>();
        List<String> types = new ArrayList<>();

        JSONArray abilitiesJson = js.getJSONArray(PokeApiConstants.ABILITIES);
        for (Object ability : abilitiesJson) {
            JSONObject jsonObject = (JSONObject) ability;
            abilities.add(jsonObject.getJSONObject(PokeApiConstants.ABILITY).getString(PokeApiConstants.NAME));

        }

        abilitiesJson = js.getJSONArray(PokeApiConstants.TYPES);
        for (Object ability : abilitiesJson) {
            JSONObject jsonObject = (JSONObject) ability;
            types.add(jsonObject.getJSONObject(PokeApiConstants.TYPE).getString(PokeApiConstants.NAME));

        }

        return Pokemon.builder().name(js.getString(PokeApiConstants.NAME))
                .weight(js.getInt(PokeApiConstants.WEIGHT))
                .abilities(abilities)
                .image(js.getJSONObject(PokeApiConstants.SPRITES).isNull(PokeApiConstants.FRONT_DEFAULT) ? "" : js.getJSONObject(PokeApiConstants.SPRITES).getString(PokeApiConstants.FRONT_DEFAULT))
                .type(types).build();
    }

    public static JSONObject getBodyFromResponse(ResponseEntity<String> response) {
        if (response.getStatusCodeValue() != HttpStatus.OK.value()) {
            return null;
        }
        return new JSONObject(response.getBody());

    }


}
