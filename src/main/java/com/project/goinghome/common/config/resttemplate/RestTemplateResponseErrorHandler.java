package com.project.goinghome.common.config.resttemplate;

import com.project.goinghome.common.error.exception.BadRequestException;
import com.project.goinghome.common.error.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        String errorMessage = new String(getResponseBody(response));
        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode.is4xxClientError()) {
            throw new BadRequestException(errorMessage);
        }

        if (statusCode.is5xxServerError()) {
            throw new InternalServerErrorException(errorMessage);
        }
    }
}
