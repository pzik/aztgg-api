package com.aztgg.api.global.filter;

import com.aztgg.api.global.logging.AppLogger;
import com.aztgg.api.global.logging.TraceLogDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Order(-1)
@Component
@RequiredArgsConstructor
public class TraceLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest contentCachingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
            HttpServletResponse contentCachingResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

            chain.doFilter(contentCachingRequest, contentCachingResponse);

            AppLogger.traceLog(TraceLogDto.of(contentCachingRequest, contentCachingResponse));

            ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(contentCachingResponse, ContentCachingResponseWrapper.class);
            if (wrapper != null) {
                wrapper.copyBodyToResponse();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
