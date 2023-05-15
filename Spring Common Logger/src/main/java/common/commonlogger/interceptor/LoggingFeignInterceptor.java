package common.commonlogger.interceptor;

import common.commonlogger.dto.LogDTO;
import common.commonlogger.service.LoggingService;
import common.commonlogger.utils.LogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingFeignInterceptor implements RequestInterceptor, Decoder, HandlerInterceptor {
    private LoggingService loggingService;
    protected static final String GENERIC_REQUEST = "payloadRequest";
    protected static final String EMPTY_STRING = "";
    protected String bodyValue;
    protected static final String FEING_BODY = "payloadRequest";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private HttpServletRequest request;

    public LoggingFeignInterceptor(LoggingService loggingService, HttpServletRequest request) {
        this.loggingService = loggingService;
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        bodyValue = FEING_BODY;
        final Map<String, Object> headersLog = getTemplateHeader(requestTemplate.headers());
        final Object bodyLog = createBody(requestTemplate.toString());
        final LogDTO logBody = LogUtils.format(headersLog, bodyLog, getTemplateQuery(requestTemplate.queries()));
        logBody.setEventType("Feign Request");
        logBody.setUri(requestTemplate.url());
        logBody.setMethod(requestTemplate.method());
        try {
            setFeignHeaders(requestTemplate);
        } catch (IllegalStateException ignored) {
            log(Response.builder().status(500).headers(requestTemplate.headers()).body(requestTemplate.body()).build(),
                    ignored.getMessage());
        }
        loggingService.logData(logBody);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        var body = "";
        try {
            body = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8.toString());
            log(response, body);
            return JSON_MAPPER.readValue(body, (Class<?>) type);
        } catch (Exception e) {
            log(response, e.getMessage());
        }
        return body;
    }

    @PostConstruct
    public void config() {
        bodyValue = GENERIC_REQUEST;
    }

    protected Map<String, Object> getTemplateHeader(final Map<String, Collection<String>> mapHeader) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Collection<String>> entry : mapHeader.entrySet()) {
            map.put(entry.getKey(), entry.getValue().stream().map(String::valueOf).collect(Collectors.joining()));
        }
        return map;
    }

    protected Object createBody(Object body) {
        if (Objects.nonNull(body)) {
            if (body instanceof String) {
                return createMap(bodyValue, body);
            } else {
                return body;
            }
        } else {
            return createMap(GENERIC_REQUEST, EMPTY_STRING);
        }
    }

    private Map<String, Object> createMap(String param, Object value) {
        final Map<String, Object> m = new HashMap<>();
        m.put(param, value);
        return m;
    }

    protected Map<String, String[]> getTemplateQuery(final Map<String, Collection<String>> mapHeader) {
        Map<String, String[]> map = new HashMap<>();
        for (Map.Entry<String, Collection<String>> entry : mapHeader.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
        }
        return map;
    }

    protected void log(Response response, String body) {
        final Map<String, Object> headersLog = getTemplateHeader(response.headers());
        final Object bodyLog = createBody(body);
        final Map<String, String[]> paramLog = getTemplateQuery(response.headers());
        final LogDTO logBody = LogUtils.format(headersLog, bodyLog, paramLog);
        logBody.setEventType("Feign Response");
        logBody.setUri(response.request().url());
        logBody.setMethod(response.request().httpMethod().toString());
        loggingService.logData(logBody);
    }

    private void setFeignHeaders(RequestTemplate requestTemplate) {
        try {
            Enumeration<String> headers = request.getHeaderNames();
            while (headers.hasMoreElements()) {
                String header = headers.nextElement();
                if (header.startsWith("x-") || header.equals("conversationid")) {
                    String value = request.getHeader(header);
                    requestTemplate.header(header, value);
                }
            }

        } catch (Exception e) {
            log.info("Exception: ", e);
        }
    }

}
