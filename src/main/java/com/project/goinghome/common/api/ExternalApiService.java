package com.project.goinghome.common.api;

import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

public interface ExternalApiService {

    <T> T get(String apiUrl, Class<T> responseType, @Nullable Object request);
    String get(String apiUrl, @Nullable Object request);

    <T> T post(String apiUrl, Class<T> responseType, MultiValueMap<String, String> header, @Nullable Object request);
    <T> T post(String apiUrl, Class<T> responseType, @Nullable Object request);
    String post(String apiUrl, MultiValueMap<String, String> header, @Nullable Object request);
    String post(String apiUrl, @Nullable Object request);
}
