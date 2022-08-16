package com.pofol.shop.config;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component("ThymeleafCustomUtilMethods")
public class ThymeleafCustomUtilMethods {

    public UriComponentsBuilder removeSortQueryStrings(String currentUri) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(currentUri);

        Map<String, String> queryParams = uri
                .buildAndExpand().getQueryParams().toSingleValueMap();

        UriComponentsBuilder updatedUri = null;

        for (String name : queryParams.keySet()) {
            if(name.equals("highestSalesRate")) {
                updatedUri = uri.replaceQueryParam("highestSalesRate");

            } else if(name.equals("lowestPrice")) {
                updatedUri= uri.replaceQueryParam("lowestPrice");

            } else if(name.equals("highestPrice")) {
                updatedUri = uri.replaceQueryParam("highestPrice");
            }
        }

        if(updatedUri == null) {
            return uri;
        }

        return updatedUri;

    }

}
