package modyo.pokeapi.util;

import modyo.dto.Pokemon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;


public class PokeApiUtil {

    public static Map<String,String> getEvolves(JSONObject jsonObject) {

       Map<String,String> evolutions = new HashMap<>();

        jsonObject = jsonObject.getJSONObject(PokeApiConstants.CHAIN);
        evolutions.put(jsonObject.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.NAME) , getIdFromUrl(jsonObject.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.URL)));

        JSONArray evolves_to = jsonObject.getJSONArray(PokeApiConstants.EVOLVES_TO);
        while (!evolves_to.isEmpty()) {

            for (Object object : evolves_to) {
                JSONObject item = (JSONObject) object;
                evolutions.put(item.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.NAME) , getIdFromUrl(item.getJSONObject(PokeApiConstants.SPECIES).getString(PokeApiConstants.URL)));

            }

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
                .imageArtWork(js.getJSONObject(PokeApiConstants.SPRITES).getJSONObject(PokeApiConstants.OTHER).getJSONObject(PokeApiConstants.OFFICIAL_ART_WORK).isNull(PokeApiConstants.FRONT_DEFAULT) ? "" : js.getJSONObject(PokeApiConstants.SPRITES).getJSONObject(PokeApiConstants.OTHER).getJSONObject(PokeApiConstants.OFFICIAL_ART_WORK).getString(PokeApiConstants.FRONT_DEFAULT))
                .type(types).build();
    }

    public static JSONObject getBodyFromResponse(ResponseEntity<String> response) {
        if (response.getStatusCodeValue() != HttpStatus.OK.value()) {
            return null;
        }
        return new JSONObject(response.getBody());

    }


}
