package com.project.goinghome.common.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.goinghome.common.api.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestTemplateExternalApiService implements ExternalApiService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <T> T get(String apiUrl, Class<T> responseType, @Nullable Object request) {
        Objects.requireNonNull(apiUrl);
        Objects.requireNonNull(responseType);

        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

            if (request != null) {
                Map<String, Object> queryParams = objectMapper.convertValue(request, new TypeReference<>() {});

                for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof List<?> listValue) {
                        String commaSeparatedValues = listValue.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(","));
                        builder.queryParam(entry.getKey(), commaSeparatedValues);
                    } else {
                        builder.queryParam(entry.getKey(), value);
                    }
                }
            }

            URI uri = builder.build().encode(StandardCharsets.UTF_8).toUri();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            if (responseType.isAssignableFrom(String.class)) {
                return responseType.cast(responseEntity.getBody());
            }

            return objectMapper.readValue(responseEntity.getBody(), responseType);
        } catch (Exception e) {
            throw new ExternalApiException(e.getMessage());
        }
    }

    @Override
    public String get(String apiUrl, Object request) {
        return get(apiUrl, String.class, request);
    }

    @Override
    public <T> T post(String apiUrl, Class<T> responseType, MultiValueMap<String, String> header,
                      @Nullable Object request) {
        Objects.requireNonNull(apiUrl);
        Objects.requireNonNull(header);
        Objects.requireNonNull(responseType);

        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

            URI uri = builder.build().encode(StandardCharsets.UTF_8).toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.addAll(header);

            HttpEntity<String> entity;
            if (request != null) {
                String json = objectMapper.writeValueAsString(request);
                entity = new HttpEntity<>(json, headers);
            } else {
                entity = new HttpEntity<>(headers);
            }

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);
            String body = responseEntity.getBody();

            if (responseType.isAssignableFrom(String.class)) {
                return responseType.cast(body);
            }

            return objectMapper.readValue(body, responseType);
        } catch (Exception e) {
            throw new ExternalApiException(e.getMessage());
        }
    }

    @Override
    public <T> T post(String apiUrl, Class<T> responseType, @Nullable Object request) {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return post(apiUrl, responseType, header, request);
    }

    @Override
    public String post(String apiUrl, MultiValueMap<String, String> header, @Nullable Object request) {
        return post(apiUrl, String.class, header, request);
    }

    @Override
    public String post(String apiUrl, @Nullable Object request) {
        return post(apiUrl, String.class, request);
    }
}
