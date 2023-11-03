package ru.liga.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.liga.exception.ResourceNotFoundException;

public class ResourceNotFoundErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ResourceNotFoundException();
        }

        return errorDecoder.decode(methodKey, response);
    }
}
