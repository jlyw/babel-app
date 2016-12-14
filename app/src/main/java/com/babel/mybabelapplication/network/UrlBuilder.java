package com.babel.mybabelapplication.network;

public class UrlBuilder {

    private static final String BASE_URL = "http://api.verbix.com/conjugator/json/";

    private static final String API_KEY = "eba16c29-e22e-11e5-be88-00089be4dcbc/";

    private static final String LANGUAGE = "eng/";

    public static String getVerbUrl(String name) {
        return BASE_URL+API_KEY+LANGUAGE+name;
    }
}
