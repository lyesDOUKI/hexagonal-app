package com.ld.infrastructure.apigouv;

import ld.domain.user.information.adresse.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ApiGouvCaller {

    @Value("${api.gouv}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;
    public ApiGouvResponse callApiToVerifyAddress(Adresse adresse){
        String query = adresse.nomAdresse().value().replaceAll(" ", "+");
        String finalUrl = UriComponentsBuilder.fromUri(URI.create(url))
                .queryParam("q", query)
                .queryParam("limit", 5)
                .queryParam("postcode", adresse.codePostal().value())
                .toUriString();

        try {
            return restTemplate.getForObject(finalUrl, ApiGouvResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }

    }
}
