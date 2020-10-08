package modyo.config;

import modyo.pokeapi.util.PokeApiConstants;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public  HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(PokeApiConstants.HEADERNAME, PokeApiConstants.HEADERVALUE);
        return new HttpEntity<String>(PokeApiConstants.PARAMETERS, headers);
    }


}
