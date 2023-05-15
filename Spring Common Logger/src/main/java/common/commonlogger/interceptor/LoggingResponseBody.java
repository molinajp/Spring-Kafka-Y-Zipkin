package common.commonlogger.interceptor;

import common.commonlogger.dto.LogDTO;
import common.commonlogger.service.LoggingService;
import common.commonlogger.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class LoggingResponseBody implements ResponseBodyAdvice<Object> {

    private static final String MANAGE_HEALTH = "actuator/health";
    private static final String LOG_TYPE = "Response";
    private LoggingService loggingService;

    public LoggingResponseBody(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        try {
            if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
                HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
                if (!req.getRequestURI().contains(MANAGE_HEALTH)) {
                    loggingService.logData(LogDTO.builder().method(request.getMethod().name()).eventType(LOG_TYPE)
                            .payload(body).headers(LogUtils.getHeaders(req)).build());
                }
            }
            
            return body;
        } catch (Exception e) {
            return body;
        }
    }

}
