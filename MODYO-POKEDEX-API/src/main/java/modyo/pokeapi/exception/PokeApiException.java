package modyo.pokeapi.exception;

import lombok.Getter;

public class PokeApiException extends RuntimeException {
    @Getter
    private  Integer statusCode;
    public PokeApiException(String message, Integer statusCode){
        super(message);
        this.statusCode=statusCode;
    }

}
