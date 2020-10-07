package modyo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
public class PokemonDetails {
    @Getter
    @Setter
    private Pokemon pokemon;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private List<List<String>> evolutions;
}
