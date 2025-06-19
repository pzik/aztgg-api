package com.aztgg.api.global.logging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

@Builder
public record TraceLogDto(String ip,
                          String uri,
                          int status,
                          String method,
                          Object requestHeader,
                          Object requestParameter,
                          Object responseHeader,
                          Object requestBody,
                          Object responseBody) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TraceLogDto of(HttpServletRequest request, HttpServletResponse response) {
        return TraceLogDto.builder()
                .ip(Optional.ofNullable(request.getHeader("X-Forwarded-For")).orElse(request.getRemoteAddr()))
                .uri(request.getRequestURI())
                .status(response.getStatus())
                .method(request.getMethod())
                .requestHeader(getRequestHeaders(request))
                .requestParameter(request.getParameterMap())
                .responseHeader(getResponseHeaders(response))
                .requestBody(readBodyAsMap(request))
                .responseBody(readBodyAsMap(response))
                .build();
    }

    private static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    private static Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }

    private static Map<String, Object> readBodyAsMap(Object requestOrResponse) {
        byte[] contentBytes = null;

        if (requestOrResponse instanceof ContentCachingRequestWrapper req) {
            contentBytes = req.getContentAsByteArray();
        } else if (requestOrResponse instanceof ContentCachingResponseWrapper res) {
            contentBytes = res.getContentAsByteArray();
        }

        if (contentBytes == null || contentBytes.length == 0) {
            return Collections.emptyMap();
        }

        if (contentBytes.length > 10240) {
            return Map.of("rawBody", "Maximum length exceeded");
        }

        if (contentBytes[0] == '{' || contentBytes[0] == '[') {
            try {
                return objectMapper.readValue(contentBytes, new TypeReference<>() {});
            } catch (IOException e) {
                AppLogger.errorLog(e.getMessage(), e);
                return Map.of("rawBody", new String(contentBytes));
            }
        } else {
            return Map.of("rawBody", new String(contentBytes));
        }
    }
}
