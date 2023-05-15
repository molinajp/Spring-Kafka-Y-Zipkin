package common.commonlogger.interceptor;

import java.util.Map;

import common.commonlogger.service.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import common.commonlogger.dto.LogDTO;
import common.commonlogger.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingGetInterceptor implements HandlerInterceptor {

    private static final String URL_MANAGE_HEALTH = "actuator/health";
    private static final String LOG_TYPE = "Request";

    private LoggingService loggingService;
    protected HttpServletRequest httpServletRequest;

    public LoggingGetInterceptor(LoggingService loggingService, HttpServletRequest request) {
        this.loggingService = loggingService;
        this.httpServletRequest = request;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            var method = request.getMethod();
            if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                    && method.equals(HttpMethod.GET.name()) && !request.getRequestURI().contains(URL_MANAGE_HEALTH)) {
                Map<String, Object> headers = LogUtils.getHeaders(request);
                loggingService.logData(LogDTO.builder().headers(headers).eventType(LOG_TYPE).method(method).build()); 
            }
        } catch (Exception e) {
            log.info("Exception: ", e);
        }
        return true;
    }
}
