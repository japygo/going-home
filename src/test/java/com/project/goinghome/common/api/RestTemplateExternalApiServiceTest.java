package com.project.goinghome.common.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class RestTemplateExternalApiServiceTest {

    @Autowired
    ExternalApiService externalApiService;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getJson() {
        // given
        String url = "http://localhost:8080/get";

        String response = "{ \"data\": \"ok\" }";

        mockServer.expect(ExpectedCount.once(), requestTo(url + "?data=test"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        TestRequest request = new TestRequest("test");

        // when
        TestResponse actual = externalApiService.get(url, TestResponse.class, request);

        // then
        assertThat(actual.getData()).isEqualTo("ok");
    }

    @Test
    void getString() {
        // given
        String url = "http://localhost:8080/get";

        String response = "ok";

        mockServer.expect(ExpectedCount.once(), requestTo(url + "?data=test"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.TEXT_PLAIN));

        TestRequest request = new TestRequest("test");

        // when
        String actual = externalApiService.get(url, request);

        // then
        assertThat(actual).isEqualTo("ok");
    }

    @Test
    void postJson() {
        // given
        String url = "http://localhost:8080/post";

        String response = "{ \"data\": \"ok\" }";

        mockServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        TestRequest request = new TestRequest("test");

        // when
        TestResponse actual = externalApiService.post(url, TestResponse.class, request);

        // then
        assertThat(actual.getData()).isEqualTo("ok");
    }

    @Test
    void postString() {
        // given
        String url = "http://localhost:8080/get";

        String response = "ok";

        mockServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(response, MediaType.TEXT_PLAIN));

        TestRequest request = new TestRequest("test");

        // when
        String actual = externalApiService.post(url, request);

        // then
        assertThat(actual).isEqualTo("ok");
    }

    @Getter
    @RequiredArgsConstructor
    private static class TestRequest {
        private final String data;
    }

    @Getter
    private static class TestResponse {
        private String data;
    }
}