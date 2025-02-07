package com.ld.infrastructure.adapter;

import com.ld.infrastructure.apigouv.ApiGouvCaller;
import com.ld.infrastructure.apigouv.ApiGouvResponse;
import ld.domain.feature.putaddress.VerifyAddressPort;
import ld.domain.user.information.adresse.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VerifyAdresseAdapter implements VerifyAddressPort {
    public static final double SMALL_VALUE_ACCEPTABLE = 0.7;
    public static final double BEST_VALUE = 1;
    @Autowired
    private ApiGouvCaller apiGouvCaller;
    @Override
    public boolean isValidAddress(Adresse adresse) {
        ApiGouvResponse apiGouvResponse = this.apiGouvCaller.callApiToVerifyAddress(adresse);
        Optional<ApiGouvResponse.Feature> optionalFeature = apiGouvResponse.getFeatures().stream().filter(feature ->
                feature.getProperties().getPostcode().equals(adresse.codePostal().value().toString()))
                .filter(feature -> {
                    ApiGouvResponse.Feature.Properties properties = feature.getProperties();
                    return properties.getScore() >= SMALL_VALUE_ACCEPTABLE && properties.getScore() <= BEST_VALUE;
                }).max(Comparator.comparingDouble(feature -> feature.getProperties().getScore()));
        return optionalFeature.isPresent();
    }
}
