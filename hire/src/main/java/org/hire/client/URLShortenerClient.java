package org.hire.client;

import org.hire.domain.entity.URLShortenerEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class URLShortenerClient {
    private static final String BASE_URL = "http://localhost:8080";

    private final RestTemplate restTemplate;

    public URLShortenerClient() {
        this.restTemplate = new RestTemplate();
    }

    public String shortenUrl(String alias, String url) {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/create?alias={alias}&url={url}", HttpMethod.PUT, null, String.class, alias, url);
        return response.getBody();
    }
    public String redirect(String alias) {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/{alias}", String.class, alias);
        return response.getBody();
    }

    public List<URLShortenerEntity> getTop10MostAccessedUrls() {
        ResponseEntity<List<URLShortenerEntity>> response = restTemplate.exchange(
                BASE_URL + "/top-urls",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<URLShortenerEntity>>() {}
        );
        return response.getBody();
    }
}
