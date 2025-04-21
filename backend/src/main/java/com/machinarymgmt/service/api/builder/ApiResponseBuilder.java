package com.machinarymgmt.service.api.builder;

import com.machinarymgmt.service.api.config.dto.ApiMessage;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.config.dto.Metadata;
import com.machinarymgmt.service.api.config.dto.Status;
import com.machinarymgmt.service.api.utils.Constants;
import com.machinarymgmt.service.dto.MakeDto;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApiResponseBuilder {

    private final Tracer tracer;

    // For successful GET operations
    public BaseApiResponse buildSuccessApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.OK.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_SUCCESS));
        return response;
    }


    // For successful POST operations
    public BaseApiResponse buildCreatedApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.CREATED.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_CREATED));
        return response;
    }

    // For successful DELETE operations
    public BaseApiResponse buildNoContentApiResponse() {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.NO_CONTENT.value()).statusMessage("")
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_NO_CONTENT));
        return response;
    }

    // Common for POST/PUT - Input validation errors
    public BaseApiResponse buildValidationErrorApiResponse(String statusMessage, List<ApiMessage> validationMessages) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.BAD_REQUEST.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_VALIDATION_ERROR))
                .messages(validationMessages);
        return response;
    }

    // Common for PUT/DELETE - Resource not found
    public BaseApiResponse buildNotFoundApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.NOT_FOUND.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_NOT_FOUND));
        return response;
    }

    // Common for all operations - Authentication issues
    public BaseApiResponse buildUnauthorizedApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.UNAUTHORIZED.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_UNAUTHORIZED));
        return response;
    }

    // Common for all operations - Authorization issues
    public BaseApiResponse buildForbiddenApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.FORBIDDEN.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_FORBIDDEN));
        return response;
    }

    // Common for POST - Resource already exists
    // Common for PUT/DELETE - Resource state conflicts
    public BaseApiResponse buildConflictApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.CONFLICT.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_CONFLICT));
        return response;
    }

    // Common for POST/PUT - Business rule validation failures
    public BaseApiResponse buildUnprocessableEntityApiResponse(String statusMessage, List<ApiMessage> validationMessages) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_UNPROCESSABLE_ENTITY))
                .messages(validationMessages);
        return response;
    }

    // Common for all operations - Unexpected errors
    public BaseApiResponse buildInternalServerErrorApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_SERVER_ERROR));
        return response;
    }

    // For dependency failures (e.g., database down, external service unavailable)
    public BaseApiResponse buildServiceUnavailableApiResponse(String statusMessage) {
        BaseApiResponse response = new BaseApiResponse()
                .metadata(new Metadata().timestamp(Instant.now())
                        .traceId(null != tracer.currentSpan()
                                ? Objects.requireNonNull(tracer.currentSpan()).context().traceId()
                                : ""))
                .status(new Status().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value()).statusMessage(statusMessage)
                        .statusMessageKey(Constants.RESPONSE_MESSAGE_KEY_SERVICE_UNAVAILABLE));
        return response;
    }
}
