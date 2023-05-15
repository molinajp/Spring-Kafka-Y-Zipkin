package common.commonlogger.config;

import common.commonlogger.interceptor.LoggingFeignInterceptor;
import common.commonlogger.interceptor.LoggingGetInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

    private LoggingGetInterceptor customLoggingInterceptor;
    private LoggingFeignInterceptor loggingFeignInterceptor;


    public InterceptorAppConfig(LoggingGetInterceptor customLoggingInterceptor,
            LoggingFeignInterceptor loggingFeignInterceptor) 
    {
        this.customLoggingInterceptor = customLoggingInterceptor;
        this.loggingFeignInterceptor = loggingFeignInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customLoggingInterceptor);
        registry.addInterceptor(loggingFeignInterceptor);
    }
}
