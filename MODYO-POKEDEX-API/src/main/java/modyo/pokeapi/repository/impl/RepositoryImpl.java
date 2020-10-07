package modyo.pokeapi.repository.impl;

import modyo.pokeapi.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class RepositoryImpl implements IRepository {

    @Autowired
    private HttpEntity<String> httpEntity;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> executeGET(final String url){

        ResponseEntity<String> result= restTemplate.exchange(url, HttpMethod.GET,httpEntity, String.class);
        return result;
    }
}
