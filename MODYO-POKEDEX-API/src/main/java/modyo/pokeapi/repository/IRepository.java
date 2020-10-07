package modyo.pokeapi.repository;

import org.springframework.http.ResponseEntity;

public interface IRepository {
    public ResponseEntity<String> executeGET(final String url);
}
